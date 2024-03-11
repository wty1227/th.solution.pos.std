package cn.th.phonerf.activity.exceptflow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.exceptflow.present.ExceptFlowPresenterImpl;
import cn.th.phonerf.activity.exceptflow.present.IExceptFlowPresenter;
import cn.th.phonerf.activity.exceptflow.view.IExceptFlowView;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.SaleBillDal;
import cn.th.phonerf.dal.SyncServiceDal;
import cn.th.phonerf.model.PayResult;
import cn.th.phonerf.model.TRmPayflow;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.model.TRmSaleman;
import cn.th.phonerf.utils.DateUtil;
import cn.th.phonerf.utils.ProgressUtils;
import cn.th.phonerf.view.CleanableEditText;

public class ExceptFlowActivity extends BaseActivity implements IExceptFlowView, View.OnClickListener {

    private Context mContext;
    private Dialog loadingDlg;
    private IExceptFlowPresenter presenter;

    private TextView pageTitleText, rightText;
    private ImageView backText;
    private RelativeLayout errLayout;
    private TextView errText, errBtn;
    private PayResult payResult;

    private CleanableEditText txtBarcode;
    private TextView saleMan;
    private int thisDom;
    private TextView lbMsg;
    private String saleManId;
    private int OnEditorState = 0;
    Button btnTotNumEnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exceptflow);

        mContext = this;
        loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);

        initUI();
        presenter = new ExceptFlowPresenterImpl(this);



    }

    /**
     * 初始化UI
     */
    private void initUI() {
        //顶部
        pageTitleText = (TextView) findViewById(R.id.top_title_text);
        pageTitleText.setText("历史单据");
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

        txtBarcode = (CleanableEditText)findViewById(R.id.txtBarcode);
        saleMan = findViewById(R.id.saleMan);
        lbMsg = (TextView)findViewById(R.id.lbMsg);

        Button btnTotNum0 = (Button)findViewById(R.id.btnTotNum0);
        Button btnTotNum1 = (Button)findViewById(R.id.btnTotNum1);
        Button btnTotNum2 = (Button)findViewById(R.id.btnTotNum2);
        Button btnTotNum3 = (Button)findViewById(R.id.btnTotNum3);
        Button btnTotNum4 = (Button)findViewById(R.id.btnTotNum4);
        Button btnTotNum5 = (Button)findViewById(R.id.btnTotNum5);
        Button btnTotNum6 = (Button)findViewById(R.id.btnTotNum6);
        Button btnTotNum7 = (Button)findViewById(R.id.btnTotNum7);
        Button btnTotNum8 = (Button)findViewById(R.id.btnTotNum8);
        Button btnTotNum9 = (Button)findViewById(R.id.btnTotNum9);
        Button btnTotNumBackspace = (Button)findViewById(R.id.btnTotNumBackspace);
        //final Button
        btnTotNumEnter = (Button)findViewById(R.id.btnTotNumEnter);
        btnTotNum0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });
        btnTotNum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });
        btnTotNum2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });
        btnTotNum3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });
        btnTotNum4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });
        btnTotNum5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });
        btnTotNum6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });
        btnTotNum7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });
        btnTotNum8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });
        btnTotNum9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });
        btnTotNumBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTotNumClick(v);
            }
        });

        btnTotNumEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saleManId = saleMan.getText().toString();
                saleMan.setText("");
                presenter.doGetSalerInfo(btnTotNumEnter,saleManId);
            }
        });

        txtBarcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                try {
                    if (OnEditorState == 0){
                        OnEditorState = 1;
                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ) {
                            presenter.doPayQueryPhoneOrderNo(btnTotNumEnter, AppParam.appId, AppParam.appKey, txtBarcode.getText().toString());
                            return true;
                        }else{
                            try {
                                if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                                    String payWay = "zf9";
                                    presenter.doPayQueryPhoneOrderNo(btnTotNumEnter, AppParam.appId, AppParam.appKey, txtBarcode.getText().toString());
                                    return true;
                                }
                            }catch (Exception e){
                                presenter.doPayQueryPhoneOrderNo(btnTotNumEnter, AppParam.appId, AppParam.appKey, txtBarcode.getText().toString());
                                return true;
                            }
                        }
                        return false;
                    }else {
                        setToast("正在补入订单请稍后...");
                        return false;
                    }
                }catch (Exception e){
                    setToast("异常："+e);
                    return false;
                }
            }
        });


    }

    private void btnTotNumClick(View v){
        String str = saleMan.getText().toString();

        Button sender = (Button)v;
        String num = (String) sender.getText();

        if(num.equals("退格")) {
            saleMan.setText(str.substring(0, str.length() == 0 ? 0 : str.length() - 1));
            return;
        }

        if (saleMan.getText().equals("退货单")){

        }else {
            //5位宽度
            if(str.length() >= 8)
                return;
        }

        if(num.equals(".")){
            if(str.indexOf(".") != -1 ){

            }
            else
                saleMan.setText(str+num);
        }
        else {
            int dotIndex = str.indexOf(".");
            if(dotIndex != -1 && str.length() > dotIndex + 2 ){

            }else
                saleMan.setText(str + num);
        }
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
    public void resPayQuery(PayResult data) {
        lbMsg.setText("");
        if(data.getRetCode().equals("1")){
            //支付成功
            //1.写入流水
            try {
                payResult = data;
                //1. 判断流水是不是本机器上产生的
                if(!GSale.StoreNo.equals(data.getAttach())){
                    setToast("该单是在"+ data.getDeviceInfo() +"站消费的。");
                    lbMsg.setText("消息：该单是在"+ data.getDeviceInfo() +"站消费的。");
                    return;
                }
                // 判断本地或者服务器上是否有本流水

                presenter.doGetFlow(btnTotNumEnter,data.getBody(),GSale.StoreNo);


                //播放支付成功音频
//                PlayerVoiceUtil.playerVoice(mContext, R.raw.pay_success);

                //savePosBill();
                //printData();
                //2.关闭对话框
                //_dlgTot.dismiss();
                //写入上一单内容
                //resetSaleState(false, true);

                //printPosBill();
            }catch (Exception ex){
                //setToast(ex.getMessage());
                lbMsg.setText(ex.getMessage());
            }
        }else {
            String msg = " 查询结果：" + " 返回代码：" + data.getRetMsg().replace("NOTPAY","未支付订单不能补入").replace("CLOSED","已关闭订单不能补入")
                    .replace("SUCCESS","支付成功").replace("REFUND","退款订单不能补入").replace("REFUNDING","退款订单不能补入");
            lbMsg.setText(msg);
            setToast(msg);
            txtBarcode.setText("");
            OnEditorState = 0;
        }
    }


    @Override
    public void resGetFlow(String code,String msg) {
        try {
            if (code.equals("1")){
                TextView saleManName = findViewById(R.id.saleManName);
                Double Price = Double.parseDouble(payResult.getTotalFee())/100;

                //查询本地是否有该条流水
                SyncServiceDal dal = new SyncServiceDal();
                List<TRmPayflow> payflows = dal.selectFlowNoPayflow(payResult.getBody());
                List<TRmSaleflow> saleflows = new ArrayList<>();
                if (payflows.size() == 0){
                    //本地没有该条数据插入
                    //支付信息
                    TRmPayflow tRmPayflow = new TRmPayflow();
                    tRmPayflow.setFlowId(1);
                    tRmPayflow.setFlowNo(payResult.getBody());
                    tRmPayflow.setSaleAmount(Price);
                    tRmPayflow.setBranchNo(payResult.getAttach());
                    tRmPayflow.setPayWay(payResult.getTradeType().toUpperCase().equals("WXPAY.MICROPAY")?"WXZ":"ZFB");
                    tRmPayflow.setSellWay("A");
                    tRmPayflow.setCardId("");
                    tRmPayflow.setCardNo("");
                    tRmPayflow.setVipNo("");
                    tRmPayflow.setCoinNo("RMB");
                    tRmPayflow.setCoinRate(1.0);
                    tRmPayflow.setPayAmount(Price);
                    tRmPayflow.setOperDate(DateUtil.getCurrentFileTime());
                    tRmPayflow.setOperId(saleManId);
                    tRmPayflow.setCounterNo("");
                    tRmPayflow.setSaleMan(saleManName.getText().toString());
                    tRmPayflow.setCashierNo(saleManId);
                    tRmPayflow.setMemo(payResult.getOutTradeNo());
                    tRmPayflow.setComFlag("1");
                    payflows.add(tRmPayflow);

                    //商品信息
                    TRmSaleflow _item = new TRmSaleflow();
                    _item.setBranch_no(payResult.getAttach());
                    _item.setItem_no("999999");
                    _item.setSource_price(Price);
                    _item.setSale_price(Price);
                    _item.setCost_price(Price);
                    _item.setSale_qnty(1.00);
                    _item.setSale_money((1.00 * Price));
                    _item.setSell_way("A");
                    _item.setOper_id(GSale.CashierNo);
                    _item.setSale_man(saleManId);
                    _item.setSale_name(saleManName.getText().toString());
                    _item.setCard_id("");
                    _item.setCard_no("");
                    _item.setShift_no(0.00);
                    _item.setCom_flag("1");//1：未上传   0：已上传
                    _item.setItem_name("单边账补入");
                    saleflows.add(_item);
                    new SaleBillDal().savePosBill(false, saleflows, payflows);
                    setToast("补录成功！");
                    lbMsg.setText("消息：补录成功！");
                }else {
                    //本地已有该条数据更改状态重新上传
                    setToast("已有该条流水记录无需补录！");
                    lbMsg.setText("消息：已有该条流水记录无需补录！");
                }

            }else {
                setToast("补录失败，"+msg);
                lbMsg.setText("消息：补录失败，"+msg);
            }
            txtBarcode.setText("");
            OnEditorState = 0;
        }catch (Exception e){
            setToast("异常："+e);
            lbMsg.setText("异常："+e);
        }
    }

    @Override
    public void resGetSalerInfo(TRmSaleman data){
        try{
            TextView saleManName = findViewById(R.id.saleManName);
            saleManName.setText(data.getSaleName());
            txtBarcode.requestFocus();
        }catch (Exception e){
            setToast("异常："+e);
        }
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
