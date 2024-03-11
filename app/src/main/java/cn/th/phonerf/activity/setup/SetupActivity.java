package cn.th.phonerf.activity.setup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.setup.present.ISetupPresenter;
import cn.th.phonerf.activity.setup.present.SetupPresenterImpl;
import cn.th.phonerf.activity.setup.view.ISetupView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GPrinter;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.model.MerchantInfo;
import cn.th.phonerf.utils.ProgressUtils;
import cn.th.phonerf.utils.SharePreferenceUtils;
import cn.th.phonerf.utils.UserUtil;
import cn.th.phonerf.utils.print.PrintInfo;
import cn.th.phonerf.view.CleanableEditText;

public class SetupActivity extends BaseActivity implements ISetupView, View.OnClickListener{
    private Context mContext;
    private Dialog loadingDlg;
    private ISetupPresenter presenter;

    private TextView pageTitleText, rightText;
    private ImageView backText;

    CleanableEditText txtBaseApiUrl;
    CleanableEditText txtBranchName;
    Button btnSetup;
    RadioGroup rbPrintName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        mContext = this;
        loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);

        initUI();
        presenter = new SetupPresenterImpl(this);
    }

    private void initUI(){
        //顶部
        pageTitleText = (TextView) findViewById(R.id.top_title_text);
        pageTitleText.setText("设置");

        backText = (ImageView) findViewById(R.id.top_back_image);
        backText.setOnClickListener(this);
        backText.setVisibility(View.VISIBLE);

        rightText = (TextView) findViewById(R.id.top_right_text);
        rightText.setText("");
        rightText.setVisibility(View.INVISIBLE);
        rightText.setOnClickListener(this);

        txtBaseApiUrl = (CleanableEditText)findViewById(R.id.txtBaseApiUrl);
        txtBranchName = (CleanableEditText)findViewById(R.id.txtBranchName);
        rbPrintName = findViewById(R.id.rbPrintName);
        btnSetup = (Button)findViewById(R.id.setup_submit);
        btnSetup.setOnClickListener(this);

        loadData();
        //txtMerchantNo.setText(SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.MERCHANTER_NO, ""));
    }
    private void loadData(){
        txtBaseApiUrl.setText(ApiConstants.BASE_API_URL);
        txtBranchName.setText(AppParam.BranchName);
        //rbPrintName.check(rbPrintName.getchi);
        //Intent intent = getIntent();
        //_initFlag = intent.getBooleanExtra("initFlag", true);
       /* switch (GPrinter.printName){
            case "none":
                rbPrintName.check(rbPrintName.getChildAt(1));
        }*/
        for(int i = 0; i < rbPrintName.getChildCount(); i++){
            RadioButton child = (RadioButton)rbPrintName.getChildAt(i);
            child.setChecked(child.getTag().toString().equals(GPrinter.printName));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_back_image:
                finish();
                break;
            case R.id.setup_submit:
                if( txtBaseApiUrl.getText().toString().trim().isEmpty()){
                    setToast("请输入完成信息");
                    break;
                }
                //presenter.doSetup(btnSetup, txtMerchantNo.getText().toString());
//                String printName
                for(int i = 0; i < rbPrintName.getChildCount(); i++){
                    RadioButton child = (RadioButton)rbPrintName.getChildAt(i);
//                    child.setChecked(child.getTag().toString().equals(GPrinter.printName));
                    if(child.isChecked())
                        GPrinter.printName = child.getTag().toString();
                }

                ApiConstants.BASE_API_URL = txtBaseApiUrl.getText().toString();
                AppParam.BranchName = txtBranchName.getText().toString();
                PrintInfo.title = AppParam.BranchName;
                UserUtil.setBaseSetup(this);
                finish();
                break;
        }

    }

    @Override
    public void saveMerchantData(MerchantInfo data) {
        UserUtil.saveMerchantInfo(mContext, data);
        setToast("保存成功！");
        //UserUtil.getMerchantInFo(mContext);
        finish();
    }
}
