package cn.th.phonerf.activity.sync;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;

import java.util.Date;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.login.LoginActivity;
import cn.th.phonerf.activity.pos.PosActivity;
import cn.th.phonerf.activity.sync.present.ISyncPresenter;
import cn.th.phonerf.activity.sync.present.SyncPresenterImpl;
import cn.th.phonerf.activity.sync.view.ISyncView;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GFunc;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.SyncServiceDal;
import cn.th.phonerf.dal.t_bd_item_infoDal;
import cn.th.phonerf.utils.DBItemHelper;
import cn.th.phonerf.utils.DBSaleHelper;
import cn.th.phonerf.utils.DBUtil;
import cn.th.phonerf.utils.DateUtil;
import cn.th.phonerf.utils.ProgressUtils;
import cn.th.phonerf.utils.SharePreferenceUtils;
import cn.th.phonerf.utils.TextUtil;


/**
 * @Info 启动页
 * @Auth Bello
 * @Time 17-2-28 下午2:19
 * @Ver
 */

public class SyncActivity extends BaseActivity implements ISyncView {
    //private BDListener mListener;
    private Context mContext;
    private Dialog loadingDlg;
    private ISyncPresenter presenter;

    private TextView pageTitleText, rightText;
    private ImageView backText;

    private TextView lbMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try{
            mContext = this;
            loadingDlg = ProgressUtils.loadCircleProgress(mContext, "开始下载数据", true, Color.WHITE);
            presenter = new SyncPresenterImpl(this);
        }catch (Exception e){
            setToast("异常："+e);
        }


        initUI();
        //检测网络

        //同步流水号
        presenter.doGetMaxTodayFlow(null , GSale.PosId);
        //

        //SystemClock.sleep(1500);
        //startActivity(new Intent(this, LoginActivity.class));
        //finish();

    }

    private void initUI() {
        try{
            //顶部
            pageTitleText = (TextView) findViewById(R.id.top_title_text);
            pageTitleText.setText("数据传输");
            backText = (ImageView) findViewById(R.id.top_back_image);
            backText.setVisibility(View.VISIBLE);
            backText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            rightText = (TextView) findViewById(R.id.top_right_text);
            rightText.setText("历史");
            rightText.setVisibility(View.INVISIBLE);
            //rightText.setOnClickListener(this);
            lbMsg = (TextView)findViewById(R.id.lbMsg);
        }catch (Exception e){
            setToast("异常："+e);
        }

    }


    @Override
    protected void onStop() {
        //mListener.BDStop();
        super.onStop();
    }

    /**
     * 返回 回去流水号
     * @param flowNo
     */
    @Override
    public void resGetMaxTodayFlow(String flowNo) {
        String str2 = "";
        try {
            //1.获取本地流水日期数据
            String str = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.FLOW_NO, "");
            if (!str.isEmpty())
            {
                String str3 = str.substring(0, 10);
                Date time = DateUtil.stringToDate(str3);
                long span = DateUtil.getDayByMinusDate(time, new Date());
                str = str.substring(12);
                if (span > 0) {
                    str = "1";
                }
            }
            else {
                str = "1";
            }
            if ((!flowNo.isEmpty()) && (Integer.valueOf(str) < Integer.valueOf(flowNo)))
            {
                str = String.valueOf(Integer.valueOf(flowNo) + 1);
            }
            GSale.FlowNo = Integer.valueOf(str);
            if ((GSale.FlowNo <= 0) || (GSale.FlowNo >= 0x270f))
            {
                str2 = "初始化全局变量FileRead()读取的流水号不在范围[0-9999]！读取值：" + str;
            }
            SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.FLOW_NO, DateUtil.getCurrentDate() + "  " + TextUtil.padRight(String.valueOf(GSale.FlowNo), 4, '0'));
            if (str2.length() > 0)
            {
                //GSys.ErrMsg = str2;
                GFunc.setFlowNo(mContext);
            }
            //开始下载商品资料
            lbMsg.setText(lbMsg.getText().toString() + "\r\n开始下载商品资料\r\n");
            Long minFlowID = new SyncServiceDal().getMinFlowID(GSale.PosId, "t_bd_item_info");
//            presenter.doDownloadItem(null, minFlowID );
            startActivity(new Intent(this, PosActivity.class));
            finish();

        }catch (Exception ex){
            str2 = "系统初始化流水号文件失败！\n" + ex.getMessage();
            setToast(str2);
            //获取本地离线数据
        }
    }



    //下载商品信息
    @Override
    public void resGetItemInfo(JSONArray data, Long num) {
        try{
            if (data.size() == 0) {
                lbMsg.setText(lbMsg.getText().toString() + "\r\n下载商品信息完成\r\n");
                //开始下载类别信息
                Long minFlowID = new SyncServiceDal().getMinFlowID(GSale.PosId, "t_bd_item_cls");
                presenter.doDownloadCls(null, minFlowID );


            }else {
                //获取第一个JSONObject
                lbMsg.setText(lbMsg.getText().toString() + "\r\n下载商品数量：" + data.size() + "\r\n");
                //写入数据库
                SyncServiceDal dal = new SyncServiceDal();
                try {
                    dal.insertList(data, GSale.PosId, "t_bd_item_info", num);
                    Long minFlowID = dal.getMinFlowID(GSale.PosId, "t_bd_item_info");
                    presenter.doDownloadItem(null, minFlowID );
                    //process[0] += data.size();
                }catch (Exception e){
                    this.setToast("异常：" + e.getMessage());
                }
            }
        }catch (Exception e){
            setToast("异常："+e);
        }

    }

    //下载类别信息
    @Override
    public void resGetItemCls(JSONArray data, Long num) {
        try{
            if (data.size() == 0) {
                lbMsg.setText(lbMsg.getText().toString() + "\r\n下载类别信息完成\r\n");
                //开始下载营业员信息
                Long minFlowID = new SyncServiceDal().getMinFlowID(GSale.PosId, "t_rm_saleman");
                presenter.doDownloadSaleman(null, minFlowID );

            }else {
                //获取第一个JSONObject
                lbMsg.setText(lbMsg.getText().toString() + "\r\n下载类别信息：" + data.size() + "\r\n");
                //写入数据库
                SyncServiceDal dal = new SyncServiceDal();
                try {
                    dal.insertCls(data, GSale.PosId, "t_bd_item_cls", num);
                    Long minFlowID = dal.getMinFlowID(GSale.PosId, "t_bd_item_cls");
                    presenter.doDownloadCls(null, minFlowID );
                    //process[0] += data.size();
                }catch (Exception e){
                    this.setToast("异常：" + e.getMessage());
                }
            }
        }catch (Exception e){
            setToast("异常："+e);
        }

    }

    //下载营业员信息
    @Override
    public void resGetSaleman(JSONArray data, Long num) {
        try{
            if (data.size() == 0) {
                lbMsg.setText(lbMsg.getText().toString() + "\r\n下载营业员信息完成\r\n");
                //开始下载价格信息
                Long minFlowID = new SyncServiceDal().getMinFlowID(GSale.PosId, "t_pc_branch_price");
                presenter.doDownloadPrice(null, minFlowID );

            }else {
                //获取第一个JSONObject
                lbMsg.setText(lbMsg.getText().toString() + "\r\n下载营业员信息：" + data.size() + "\r\n");
                //写入数据库
                SyncServiceDal dal = new SyncServiceDal();
                try {
                    dal.insertSaleman(data, GSale.PosId, "t_rm_saleman", num);
                    Long minFlowID = dal.getMinFlowID(GSale.PosId, "t_rm_saleman");
                    presenter.doDownloadSaleman(null, minFlowID );
                    //process[0] += data.size();
                }catch (Exception e){
                    this.setToast("异常：" + e.getMessage());
                }
            }
        }catch (Exception e){
            setToast("异常："+e);
        }

    }

    //下载价格信息
    @Override
    public void resGetPrice(JSONArray data, Long num) {
        try{
            if (data.size() == 0) {
                //throw new Exception("系统中无此商品信息");
                lbMsg.setText(lbMsg.getText().toString() + "\r\n下载价格信息完成\r\n");

                //进入登陆界面
                Long minFlowID = new SyncServiceDal().getMinFlowID(GSale.PosId, "t_bd_payment_info");
                presenter.doDownloadPayment(null, minFlowID );
            }else {
                //获取第一个JSONObject
                lbMsg.setText(lbMsg.getText().toString() + "\r\n下载价格信息：" + data.size() + "\r\n");
                //写入数据库
                SyncServiceDal dal = new SyncServiceDal();
                try {
                    dal.insertPrice(data, GSale.PosId, "t_pc_branch_price", num);
                    Long minFlowID = dal.getMinFlowID(GSale.PosId, "t_pc_branch_price");
                    presenter.doDownloadPrice(null, minFlowID );
                    //process[0] += data.size();
                }catch (Exception e){
                    this.setToast("异常：" + e.getMessage());
                }
            }
        }catch (Exception e){
            setToast("异常："+e);
        }

    }

    @Override
    public void resGetPayment(JSONArray data, Long num) {
        try{
            if (data.size() == 0) {
                //throw new Exception("系统中无此商品信息");
                lbMsg.setText(lbMsg.getText().toString() + "\r\n下载付款方式信息完成\r\n");
                setToast("下载完成, 进入登陆界面...");
                //进入登陆界面
                startActivity(new Intent(this, PosActivity.class));
                finish();
            }else {
                //获取第一个JSONObject
                lbMsg.setText(lbMsg.getText().toString() + "\r\n下载付款方式信息：" + data.size() + "\r\n");
                //写入数据库
                SyncServiceDal dal = new SyncServiceDal();
                try {
                    dal.insertPayment(data, GSale.PosId, "t_bd_payment_info", num);
                    Long minFlowID = dal.getMinFlowID(GSale.PosId, "t_bd_payment_info");
                    presenter.doDownloadPayment(null, minFlowID );
                    //process[0] += data.size();
                }catch (Exception e){
                    this.setToast("异常：" + e.getMessage());
                }
            }
        }catch (Exception e){
            setToast("异常："+e);
        }

    }


    @Override
    public void resSyncError(String msg) {
        try{
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);

            builder.setTitle("失败");
            builder.setMessage("同步失败, 是否重新同步");
            //builder.setCancelable(false);

            //final Double finalConsumAmt = consumAmt;
            builder.setPositiveButton("重新同步", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {

                    presenter.doGetMaxTodayFlow(null , GSale.PosId);
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("退出系统", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
            final android.app.AlertDialog dlg = builder.create();
            dlg.show();
        }catch (Exception e){
            setToast("异常："+e);
        }

    }
}
