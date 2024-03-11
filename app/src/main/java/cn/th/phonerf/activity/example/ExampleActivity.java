package cn.th.phonerf.activity.example;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.example.present.IExamplePresenter;
import cn.th.phonerf.activity.example.present.ExamplePresenterImpl;
import cn.th.phonerf.activity.example.view.IExampleView;
import cn.th.phonerf.utils.HttpUtils;
import cn.th.phonerf.utils.ProgressUtils;

public class ExampleActivity extends BaseActivity implements IExampleView, View.OnClickListener {

    private Context mContext;
    private Dialog loadingDlg;
    private IExamplePresenter presenter;

    private TextView pageTitleText, rightText;
    private ImageView backText;
    private RelativeLayout errLayout;
    private TextView errText, errBtn;

    private Button index_btnSubmit;
    private Button index_btn0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mContext = this;
        loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);

        initUI();
        presenter = new ExamplePresenterImpl(this);

    }

    /**
     * 初始化UI
     */
    private void initUI() {
        //顶部
        pageTitleText = (TextView) findViewById(R.id.top_title_text);
        pageTitleText.setText("消费");
        backText = (ImageView) findViewById(R.id.top_back_image);
        backText.setVisibility(View.INVISIBLE);
        //backText.setOnClickListener(this);
        rightText = (TextView) findViewById(R.id.top_right_text);
        rightText.setText("历史");
        rightText.setVisibility(View.VISIBLE);
        rightText.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back_image:
                finish();
                break;
            case R.id.top_right_text: {
                //Intent intent = new Intent(this, HistoryActivity.class);
                //startActivity(intent);
            }
            break;
        }
    }

    @Override
    public void resRequestData() {

        setToast("成功!");

    }

    @Override
    public void showErrorMsg(String errStr) {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.text_shake);
        errLayout.startAnimation(animation);
        errLayout.setVisibility(View.VISIBLE);
        if (errStr.contains("手机号")) {
            errText.setText("您输入的手机号未注册，点击去");
            errBtn.setText("注册");
        } else if (errStr.contains("密码")) {
            errText.setText("您输入的密码错误，点击");
            errBtn.setText("找回密码");
        } else {
            errText.setText("");
            errBtn.setText("");
            errLayout.setVisibility(View.GONE);
        }
    }

    public static byte[]bmpToByteArray(final Bitmap bmp,final boolean needRecycle){
        ByteArrayOutputStream output=new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,output);
        if(needRecycle){
            bmp.recycle();
        }
        byte[]result=output.toByteArray();
        try{
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
