package cn.th.phonerf.activity.index;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.index.present.IIndexPresenter;
import cn.th.phonerf.activity.index.present.IndexPresenterImpl;
import cn.th.phonerf.activity.index.view.IIndexView;
import cn.th.phonerf.utils.ProgressUtils;

public class IndexActivity extends BaseActivity implements IIndexView, View.OnClickListener {

    private Context mContext;
    private Dialog loadingDlg;
    private IIndexPresenter presenter;

    private TextView pageTitleText, rightText;
    private ImageView backText;
    private RelativeLayout errLayout;
    private TextView errText, errBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mContext = this;
        loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);

        initUI();
        presenter = new IndexPresenterImpl(this);
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
}
