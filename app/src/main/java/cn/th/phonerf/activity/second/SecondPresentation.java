package cn.th.phonerf.activity.second;


import android.app.Presentation;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.login.LoginActivity;
import cn.th.phonerf.activity.login.other.MyLoader;
import cn.th.phonerf.activity.pos.other.AdapterSaleFlowList;
import cn.th.phonerf.dal.SyncServiceDal;
import cn.th.phonerf.model.TRmPayflow;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.utils.DateUtil;

public class SecondPresentation extends Presentation {
    ListView gvSaleFlow;
    AdapterSaleFlowList _adapterSaleFlowList;
    List<TRmSaleflow> _saleFlowList = new ArrayList<>();

    TextView lbQty;
    TextView lbAmt;

    private VideoView videoView;

    private Context mContext;

    private RelativeLayout errLayout;
    private TextView errText, errBtn;

    //banner
    private Banner banner;//顶部广告栏控件
    private ArrayList<String> list_title;
    private ArrayList<String> list_path;
    private String[] arrayVideo = {
            "https://rtc.thcode.net:24699/assets/img/90.mp4",
            "https://rtc.thcode.net:24699/assets/img/91.mp4",
            "https://rtc.thcode.net:24699/assets/img/92.mp4",
            "https://rtc.thcode.net:24699/assets/img/93.mp4"
    };
    private int videoIndex = 0;
    private int State = 0;
    //private int _index = 0;



    public SecondPresentation(Context outerContext, Display display) {
        super(outerContext, display);
        // TODO Auto-generated constructor stub
        mContext = outerContext;
    }

    public SecondPresentation(Context outerContext, Display display,int state) {
        super(outerContext, display);
        // TODO Auto-generated constructor stub
        mContext = outerContext;
        State = state;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_second);

        try{
            LinearLayout zzc = (LinearLayout)findViewById(R.id.zzc);
            zzc.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }catch (Exception e){
            Log.d("异常：",e+"");
        }



        //mContext = this;
        //loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);
        initUI();



    }


    private void initApi() {
        //获取版本号
//        presenter.doVersion();
    }

    /**
     * 初始化UI
     */
    private void initUI() {


        try{


            //获取元素
            videoView = findViewById(R.id.videoView);

            //设置访问视频地址
            videoView.setVideoURI(Uri.parse(arrayVideo[videoIndex]));

            //隐藏操作栏
            MediaController mediaCtlr = new MediaController(mContext);
            mediaCtlr.setMediaPlayer(videoView);
            mediaCtlr.setVisibility(View.INVISIBLE);
            videoView.setMediaController(mediaCtlr);

            //视频静音
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0f, 0f);
                }
            });

            //视频播放完毕回调
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {



                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                Handler mainHandler = new Handler(Looper.getMainLooper());
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        videoIndex = (videoIndex + 1) % 4;
                                        Log.d("videoIndex:",videoIndex+"");
                                        //设置访问视频地址
                                        videoView.setVideoURI(Uri.parse(arrayVideo[videoIndex]));
                                        //开始播放
                                        videoView.start();
                                    }
                                });
                            }catch (Exception e){
                                Log.e("异常：",e.getMessage());
                            }
                        }
                    },10000);
                }
            });


            //开始播放
            videoView.start();

            //获取焦点
            videoView.requestFocus();








            gvSaleFlow = (ListView)findViewById(R.id.gvSaleFlow);
            lbQty = (TextView)findViewById(R.id.lbQty);
            lbAmt = (TextView)findViewById(R.id.lbAmt);


            //_adapterSaleFlowList = new AdapterSaleFlowList(mContext, R.layout.ls_gvsale_row, _saleFlowList);
            //gvSaleFlow.setAdapter(_adapterSaleFlowList);


            if (State == 1){
                //隐藏侧边栏
                LinearLayout left_con = findViewById(R.id.left_con);
                left_con.setVisibility(View.GONE);
            }

            //顶部
            //region BannerView
            banner = (Banner) findViewById(R.id.banner);
            list_path = new ArrayList<>();
            //放标题的集合
            list_title = new ArrayList<>();
            list_path.add("http://cxfwq4.thcode.net:24682/images/1005_1.jpg?date="+ DateUtil.getCurrentDateTime());
            list_path.add("http://cxfwq4.thcode.net:24682/images/1005_2.jpg?date="+ DateUtil.getCurrentDateTime());
            list_path.add("http://cxfwq4.thcode.net:24682/images/1005_3.jpg?date="+ DateUtil.getCurrentDateTime());
            list_path.add("http://cxfwq4.thcode.net:24682/images/1005_4.jpg?date="+ DateUtil.getCurrentDateTime());

            list_title.add("1.");
            list_title.add("2.");
            list_title.add("3.");
            list_title.add("4.");
            //设置内置样式，共有六种可以点入方法内逐一体验使用。
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            //设置图片加载器，图片加载器在下方
            banner.setImageLoader(new MyLoader());
            //设置图片网址或地址的集合
            banner.setImages(list_path);
            //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
            banner.setBannerAnimation(Transformer.Default);
            //设置轮播图的标题集合
            banner.setBannerTitles(list_title);
            //设置轮播间隔时间
            banner.setDelayTime(3000);
            //设置是否为自动轮播，默认是“是”。
            banner.isAutoPlay(true);
            //设置指示器的位置，小点点，左中右。
            banner.setIndicatorGravity(BannerConfig.CENTER);
            //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
            //.setOnBannerListener(this)
            //必须最后调用的方法，启动轮播图。
            try {
                banner.start();
                final String msg = "门店库存管理现场化、价签维护不用愁，移动技术来帮助、实时信息交互，支撑门店现场应变操作";
            } catch (Exception e) {
                //MsgBox.Show(this, e.getMessage());
            }
        }catch (Exception e){
            Log.d("异常：",e+"");
        }

        //endregion
    }

    public void loadData(AdapterSaleFlowList adapter){

        try{
            _adapterSaleFlowList = adapter;// new AdapterSaleFlowList(mContext, R.layout.ls_gvsale_row, _saleFlowList);
            gvSaleFlow.setAdapter(_adapterSaleFlowList);
        }catch (Exception e){
            Log.d("异常：",e+"");
        }


    }
    public void calc(String qty, String amt){

        try{
            lbQty.setText(qty);
            lbAmt.setText(amt);
        }catch (Exception e){
            Log.d("异常：",e+"");
        }

    }

}
