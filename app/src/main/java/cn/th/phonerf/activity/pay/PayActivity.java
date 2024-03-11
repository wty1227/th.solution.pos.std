package cn.th.phonerf.activity.pay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.histonepos.npsdk.bind.Const;
import com.histonepos.npsdk.bind.PrinterConsts;
import com.histonepos.npsdk.printer.IPrinterService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.pay.present.IPayPresenter;
import cn.th.phonerf.activity.pay.present.PayPresenterImpl;
import cn.th.phonerf.activity.pay.view.IPayView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GFunc;
import cn.th.phonerf.constant.GPrinter;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.constant.GlobalDefines;
import cn.th.phonerf.model.PosSaleWay;
import cn.th.phonerf.model.TRmPayflow;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.model.PayBodyVo;
import cn.th.phonerf.model.PayResult;
import cn.th.phonerf.model.t_rm_vip_info;
import cn.th.phonerf.model.t_sys_printer_head;
import cn.th.phonerf.model.t_sys_printer_pay;
import cn.th.phonerf.utils.JsonUtils;
//import cn.th.phonerf.utils.PlayerBeepVoiceUtil;
import cn.th.phonerf.utils.PlayerVoiceUtil;
import cn.th.phonerf.utils.SnowflakeIdWorker;
import cn.th.phonerf.utils.print.PrintPaper;
import cn.th.phonerf.utils.print.PrintString;

public class PayActivity extends BaseActivity implements IPayView, View.OnClickListener{

    private Context mContext;
    //    private Dialog loadingDlg;
    private IPayPresenter presenter;

    private TextView pageTitleText, rightText;
    private ImageView backText;
    private RelativeLayout errLayout;
    private TextView errText, errBtn;

    private TextView lbSaleAmount;
    private TextView lbSaleQnty;
    private TextView btnCancel;
    private TextView lbInput;
    private TextView lbMsg;

    /**
     * 倒计时
     */
    private int MAXIDLENUM = 180;
    private int _idleNum = MAXIDLENUM;
    private Integer _findMaxNum = 10;
    private Integer _queryCount = _findMaxNum;
    private List<TRmSaleflow> _listOrderFlow;
    private double _saleAmount;
    private double _saleQnty;
    private Long _flowNo;
    private String TAG = "PayActivity";
    // private String _payWay = "WXZ";
    //    private String transferId = "";
    private boolean _isBusy = false;
    private Timer timer;


    TextView btnTest;
    t_rm_vip_info _vipInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        mContext = this;
//        loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);
        presenter = new PayPresenterImpl(this);
        initUI();
        //initPrinter();
    }

    @Override
    protected void onDestroy() {
        if(timer != null)
            timer.cancel();
        super.onDestroy();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        //顶部
        pageTitleText = (TextView) findViewById(R.id.top_title_text);
        pageTitleText.setText("付款");
        /*backText = (ImageView) findViewById(R.id.top_back_image);
        backText.setVisibility(View.INVISIBLE);
        //backText.setOnClickListener(this);
         */
        rightText = (TextView) findViewById(R.id.top_right_text);
//        rightText.setText("历史");
        rightText.setVisibility(View.VISIBLE);
        rightText.setOnClickListener(this);

        lbSaleAmount = findViewById(R.id.lbSaleAmount);
        lbSaleQnty = findViewById(R.id.lbSaleQnty);
        btnCancel = findViewById(R.id.btnCancel);
        lbInput = findViewById(R.id.lbInput);
        lbMsg = findViewById(R.id.lbMsg);
        btnTest = findViewById(R.id.btnTest);

        btnCancel.setOnClickListener(this);
        btnTest.setOnClickListener(this);
        loadData();
    }



    private void loadData() {

        Intent intent = getIntent();
        _listOrderFlow =  (List<TRmSaleflow>)intent.getSerializableExtra("order_flow");
        _vipInfo = (t_rm_vip_info) intent.getSerializableExtra("vip_info");
        _saleAmount = intent.getDoubleExtra ("sale_amount", 0.00);
        _saleQnty = intent.getDoubleExtra ("sale_qnty", 0.00);
        // _amount = 0.01;
        if(_saleAmount <= 0 || _listOrderFlow.size() == 0){
            setToast("传值错误，请重新点餐");
            finish();
            return;
        }
        //BigDecimal qty = new BigDecimal()
        lbSaleQnty.setText(String.format("应付(共%s件)", String.valueOf(_saleQnty)));
        lbSaleAmount.setText("￥" + String.valueOf(_saleAmount)  );

        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(Integer.valueOf( GSale.PosId.substring(GSale.PosId.length()-1) ) , 0);
        _flowNo = idWorker.nextId();
        rightText.setText(_flowNo.toString());

        loadThreadData();
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
                break;
            }

            case R.id.btnCancel: {
                finish();
                break;
            }
            case R.id.btnTest:{

                    PlayerVoiceUtil.playerVoice(mContext, R.raw.pay_success);
                    printSheet(_listOrderFlow, "123");
                break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        try {
            if (keyCode >= 7 && keyCode <= 16) {
                String str = lbInput.getText().toString();
                str += String.valueOf(keyCode - 7);
                lbInput.setText(str);
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //QueryData(lbInput.getText().toString());
                startPay(lbInput.getText().toString());
                lbInput.setText("");
                return true;
            }
//            System.err.println(keyCode);

        } catch (Exception e) {
            setToast(e.getMessage());
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void resRequestData() {

        setToast("成功!");

    }

    public void startPay(String authCode) throws JSONException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        try {
            if(_isBusy){
                setToast("请等待上一次扫码支付结果");
                return;
            }

            _isBusy = true;
            _idleNum = MAXIDLENUM;
            lbMsg.setText("");

            PayBodyVo vo = new PayBodyVo();
            getFlowNo();
            vo.setOrderNo(_flowNo.toString());
            vo.setAuthCode(authCode);
            vo.setAmount(_saleAmount);
            vo.setBody(JsonUtils.objectToJsonStr(_listOrderFlow));
            vo.setPosId(GSale.PosId);
            presenter.doPayPayFlow(null, vo);
        }catch (Exception e) {
            setToast(e.getMessage());
        }
        // GSale.AppId, GSale.AppKey, GSale.StoreNo, _flow_no.toString(), authCode
        // , _amount, _listOrderFlow.toString(), GSale.StoreName, _payWay, "");
    }

    public void queryPayResult() {
        // _idleNum = _maxIdleNum;
        presenter.doPayQueryFlow(null, GSale.PosId, _flowNo.toString());
    }

    @Override
    public void resRequestData(String tag, String msg, JSONObject response) {
        try {
            switch (tag) {
                case ApiConstants.ACTION_PAY_PAY: {
                    // recordOperLog(TAG, "p_p:", response.toString());
                    PayResult data = (PayResult) JsonUtils.JsonStrToClass (response.getString("data"), PayResult.class);
                    if(data == null || !data.getRetCode().equals("0")){
                        showErrorMsg(data == null ? "返回异常" : data.getRetMsg() );
                        return;
                    }
                    _queryCount = _findMaxNum;
                    queryPayResult();
                    break;
                }

                case ApiConstants.ACTION_PAY_QUERY: {

                    PayResult data = (PayResult) JsonUtils.JsonStrToClass (response.getString("data"), PayResult.class);
                    if(data == null || data.getRetCode().equals("-1")){
                        showErrorMsg(data == null ? "返回异常" : data.getRetMsg() );
                        return;
                    }

                    if(data.getRetCode().equals("0")){
                        showErrorMsg("");
                        PlayerVoiceUtil.playerVoice(mContext, R.raw.pay_success);
                        printSheet(_listOrderFlow, data.getPayOrderNo() );

                        //printPosBill();
                        Intent intent =  new Intent();
                        //intent.putExtra("barcode",rawResult.getText() );
                        setResult(RESULT_OK, intent);
                        finish();

                    }else {  // if(data.getRetcode().equals("1"))

                        this.lbMsg.setText((_queryCount--) + " 查询结果：" + data.getRetMsg());
                        if (_queryCount <= 0)
                        {
                            _queryCount = _findMaxNum;
                            this.setPayProgressDlg(false);
                            //弹出对话框，是否继续查询

                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            final AlertDialog dialog = builder.create();
                            LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.dialog_std, null);
                            TextView lbTitle = view.findViewById(R.id.lbTitle);
                            TextView lbDesc = view.findViewById(R.id.lbDesc);
                            Button btnCancel = view.findViewById(R.id.btnCancel);
                            Button btnOk = view.findViewById(R.id.btnOk);
                            lbTitle.setText("是否继续查询支付结果");
                            lbDesc.setText("可能因为网络问题，暂时未获取支付结果； \r\n\r\n如果您已经支付成功，建议点击【继续查询结果】");
                            btnCancel.setText("没有支付成功");
                            btnOk.setText("继续查询支付结果");
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    queryPayResult();
                                    dialog.cancel();
                                }
                            });
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    _isBusy = false;
                                    dialog.cancel();
                                }
                            });
                            dialog.setCancelable(false);
                            dialog.show();
                            dialog.getWindow().setContentView(view); // show()之后重新布局解决黑边

                            /*AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("是否继续查询支付结果")
                                    .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            // recordOperLog(TAG, "R2.3", "continue" );
                                            queryPayResult();
                                            dialog.cancel();
                                        }
                                    });
                            builder.setNegativeButton("没有支付成功", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                    showErrorMsg("");
                                    // recordOperLog(TAG, "R1.2", "cancel" );
                                }
                            });
                            builder.setCancelable(false);
                            builder.create().show();*/


                            return;
                        }else{
                            Thread.sleep(2000);
                            queryPayResult();
                        }
                    }
                    break;
                }
                default:
                    showErrorMsg(msg);
                    break;
            }
        }catch (Exception e){
            // e.printStackTrace();
            showErrorMsg(e.getMessage());
        }
    }

    @Override
    public void resPayOrderError(String msg) {
        try{
            this._isBusy = false;
            this.setPayProgressDlg(false);
            lbInput.setText("");
            getFlowNo();
            setToast(msg);
            lbMsg.setText("订单创建失败，请重新扫描手机付款码进行支付!");
        }catch (Exception e){
            setToast("异常："+e);
        }
    }

    private void getFlowNo(){
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(Integer.valueOf( GSale.PosId.substring(GSale.PosId.length()-1) ) , 0);
        _flowNo = idWorker.nextId();
        /*for (TRmSaleflow item: _listOrderFlow
             ) {
            item.setMemo(_flowNo.toString());
        }*/
        rightText.setText(_flowNo.toString());
    }

    @Override
    public void showErrorMsg(String errStr) {
        this._isBusy = false;
        this.setPayProgressDlg(false);
        if(!errStr.isEmpty()) {
            setToast(errStr);
            lbMsg.setText(errStr);
        }
    }


    private void printSheet(List<TRmSaleflow> list, String payOrderNo) {
        try {
            List<TRmPayflow> _listPayFlow = new ArrayList<>();
            TRmPayflow payflow = new TRmPayflow();
            if(_vipInfo!= null) {
                payflow.setVipNo(_vipInfo.getCard_id());

            }
            payflow.setPayName("移动支付");
//            payflow.setPayName(String.format("移动支付[%s]", payOrderNo));
            payflow.setRemark(payOrderNo);
            payflow.setPayAmount(_saleAmount);
            _listPayFlow.add(payflow);


            PrintPaper paper = new PrintPaper();
            List<PrintString> listPrtStr = new ArrayList<PrintString>();
            t_sys_printer_head headModel = new t_sys_printer_head();

            headModel.branch_no = GSale.StoreNo;    //服务区id
            headModel.branch_name = GSale.StoreName;//服务区名称
            headModel.store_no = GSale.StoreNo;         //门店id
            headModel.store_name = GSale.StoreName;     //门店名称
            headModel.cashier_no = GSale.CashierNo;     //登录id
            headModel.cashier_name = GSale.CashierName; //登录名称
            headModel.sale_man = GSale.SaleMan;         //营业员
            headModel.sale_name = GSale.SaleName;       //营业员名称
            headModel.flow_no = _flowNo.toString();// list.get(0).getFlow_no();        //单号
            headModel.oper_date = list.get(0).getOper_date();    //下单时间
            headModel.rdmNo = "";//_listPayFlow.get(0).getRdmNo();

            t_sys_printer_pay printPay = new t_sys_printer_pay();
            printPay.pos_sale_way = 0;// (_curSaleWay == PosSaleWay.Sale ? 0 : 1);   //销售方式
            printPay.total_amt = _saleAmount;                                 //销售金额
            printPay.total_qty = _saleQnty;                                 //销售数量
            //printPay.dis_amt = this._frmPay.GetDisAmt(),
            //printPay.source_amt = this._frmPay.GetSourceAmt(),
            printPay.total_packages = _saleQnty;                            //ToDo 件数

            /*
            PrintInfo.IsPrtSalesMore = false;
            if (GParam.IsSalesMore && (this._frmMain.ListSaleFlow.Count > 0))
            {
                string str2 = this._frmMain.ListSaleFlow[0].sale_man;
                foreach (t_rm_saleflow _saleflow in this._frmMain.ListSaleFlow)
                {
                    if (str2 != SIString.TryStr(_saleflow.sale_man))
                    {
                        PrintInfo.IsPrtSalesMore = true;
                        break;
                    }
                }
            }
            */
            //GInit.InitPrintParmOnlyWidth();
            boolean isHzPrinter = true;
            GFunc.AddListPrt(listPrtStr, paper.PrintHeadEmptyLine());
            GFunc.AddListPrt(listPrtStr, paper.PrintHeader(true, headModel, isHzPrinter, 0x63));
            GFunc.AddListPrt(listPrtStr, paper.PrintDetailTitle(isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintDetail(list, isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintPayInfo(_listPayFlow, null, printPay, isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintFooter(isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintFooterEmptyLine());

            if(GPrinter.basePrinter != null)
                GPrinter.basePrinter.printContext(listPrtStr);
            /*if(GPrinter.basePrinter != null){

                GPrinter.basePrinter.beginTranscation();
                for(PrintString item : listPrtStr){
                    if(item.getIsBigSize()){
                        GPrinter.printer.setTextBold();
                        GPrinter.printer.addTextSizeDouble();

                    }
                    GPrinter.printer.addText(item.getPrtStr() + "\n", 0);
                    if(item.getIsBigSize()){
                        GPrinter.printer.cancelTextBold();
                        GPrinter.printer.addTextSizeNormal();

                    }
                }
                GPrinter.printer.addFeedLine(10);
                GPrinter.printer.addCut();
                int transId = GPrinter.printer.endTranscation();
                GPrinter.printer.commit(transId);

            }*/


        }catch (Exception ex){
            setToast("打印异常!打印异常!打印异常!\n" + ex.getMessage());
        }

    }
    /*
       printer.setTextBold();
       printer.addText("-----------Payment receipts--------------\n",1 );
       printer.cancelTextBold();
       printer.addTextSizeDouble();
       printer.addText("     Welcome\n", 1);
       printer.addTextSizeNormal();
       printer.addText("operator:01    Jadeite Restaurant\n", 1);
       printer.addText("------------------------------\n", 1);
       printer.addText("product     amount    price\n", 1);
       printer.addText("1.Lobster dumplings      x2     64.00\n", 1);
       printer.addText("2.Qingdao Prawn        x10     380.00\n", 1);
       printer.addBarcode("ABCD0123456789012345");
       printer.addQRCode("This Is A Simple QR Code", 6);
       printer.addFeedLine(3);
       printer.addCut();
       int transId = printer.endTranscation();
       printer.commit(transId);
        */
    private void loadThreadData(){
//         new Thread(new Runnable() {
//             @Override
//             public void run() {
//                 while (true) {
//                     try {
//                         Thread.sleep(1000);
//
//                         if(!_isBusy)
//                             _idleNum--;
//                         if(_idleNum <= 0)
//                             finish();
//                     } catch (InterruptedException e) {
//                         e.printStackTrace();
//                     }
//                     runOnUiThread(new Runnable() {
//                         @Override
//                         public void run() {
//                             btnCancel.setText(String.format("取消支付( %d 秒 )", _idleNum));
// //                            rightText.setText(DateUtil.getCurrentDateTime());
//                         }
//                     });
//                 }
//             }
//         }).start();
        if (timer == null){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    try {


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!_isBusy)
                                    _idleNum--;
                                if(_idleNum <= 0)
                                    finish();
                                btnCancel.setText(String.format("取消支付( %d 秒 )", _idleNum));
                            }
                        });

                    }catch (Exception e){
                    }
                }
            },0,1000);

        }
    }
/*
    //打印
    public boolean printData()
    {

        try {

            PrintPaper paper = new PrintPaper();
            List<PrintString> listPrtStr = new ArrayList<PrintString>();
            t_sys_printer_head headModel = new t_sys_printer_head();

            headModel.branch_no = AppParam.BranchNo;    //服务区id
            headModel.branch_name = AppParam.BranchName;//服务区名称
            headModel.store_no = GSale.StoreNo;         //门店id
            headModel.store_name = GSale.StoreName;     //门店名称
            headModel.cashier_no = GSale.CashierNo;     //登录id
            headModel.cashier_name = GSale.CashierName; //登录名称
            headModel.sale_man = GSale.SaleMan;         //营业员
            headModel.sale_name = GSale.SaleName;       //营业员名称
            headModel.flow_no = _listPayFlow.get(0).getFlowNo();        //单号
            headModel.oper_date = _listPayFlow.get(0).getOperDate();    //下单时间
            headModel.rdmNo = "";//_listPayFlow.get(0).getRdmNo();

            t_sys_printer_pay printPay = new t_sys_printer_pay();
            printPay.pos_sale_way = (_curSaleWay == PosSaleWay.Sale ? 0 : 1);   //销售方式
            printPay.total_amt = _saleTotalAmt;                                 //销售金额
            printPay.total_qty = _saleTotalQty;                                 //销售数量
            //printPay.dis_amt = this._frmPay.GetDisAmt(),
            //printPay.source_amt = this._frmPay.GetSourceAmt(),
            printPay.total_packages = _saleTotalQty;                            //ToDo 件数

            /*
            PrintInfo.IsPrtSalesMore = false;
            if (GParam.IsSalesMore && (this._frmMain.ListSaleFlow.Count > 0))
            {
                string str2 = this._frmMain.ListSaleFlow[0].sale_man;
                foreach (t_rm_saleflow _saleflow in this._frmMain.ListSaleFlow)
                {
                    if (str2 != SIString.TryStr(_saleflow.sale_man))
                    {
                        PrintInfo.IsPrtSalesMore = true;
                        break;
                    }
                }
            }
            * /
            //GInit.InitPrintParmOnlyWidth();
            boolean isHzPrinter = true;
            GFunc.AddListPrt(listPrtStr, paper.PrintHeadEmptyLine());
            GFunc.AddListPrt(listPrtStr, paper.PrintHeader(true, headModel, isHzPrinter, 0x63));
            GFunc.AddListPrt(listPrtStr, paper.PrintDetailTitle(isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintDetail(_saleFlowList, isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintPayInfo(_listPayFlow, null, printPay, isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintFooter(isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintFooterEmptyLine());

            if(printer!= null){
                printer.openCashBox();
                printer.beginTranscation();
                //printer.
                //printer.addImage(getBitmap());

                for(PrintString item : listPrtStr){
                    if(item.getIsBigSize()){
                        printer.setTextBold();
                        printer.addTextSizeDouble();

                    }
                    printer.addText(item.getPrtStr() + "\n", 0);
                    if(item.getIsBigSize()){
                        printer.cancelTextBold();
                        printer.addTextSizeNormal();

                    }
                }
                printer.addFeedLine(10);
                printer.addCut();
                int transId = printer.endTranscation();
                printer.commit(transId);
                /*
                printer.setTextBold();
                printer.addText("-----------Payment receipts--------------\n",1 );
                printer.cancelTextBold();
                printer.addTextSizeDouble();
                printer.addText("     Welcome\n", 1);
                printer.addTextSizeNormal();
                printer.addText("operator:01    Jadeite Restaurant\n", 1);
                printer.addText("------------------------------\n", 1);
                printer.addText("product     amount    price\n", 1);
                printer.addText("1.Lobster dumplings      x2     64.00\n", 1);
                printer.addText("2.Qingdao Prawn        x10     380.00\n", 1);
                printer.addBarcode("ABCD0123456789012345");
                printer.addQRCode("This Is A Simple QR Code", 6);
                printer.addFeedLine(3);
                printer.addCut();
                int transId = printer.endTranscation();
                printer.commit(transId);
                 * /
            }


        }catch (Exception ex){
            setToast("打印异常!打印异常!打印异常!\n" + ex.getMessage());
        }

            /*if (GLog.IsLogBillPrint)
            {
                GLog.WritePrintInfo(listPrtStr);
            }* /

        return true;
    }*/
}
