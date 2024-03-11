package cn.th.phonerf.activity.report;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histonepos.npsdk.bind.Const;
import com.histonepos.npsdk.bind.PrinterConsts;
import com.histonepos.npsdk.printer.IPrinterService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.pos.present.IPosPresenter;
import cn.th.phonerf.activity.report.bean.ReportItem1;
import cn.th.phonerf.activity.report.other.ReportItemAdapter;
import cn.th.phonerf.activity.report.present.IRptPresenter;
import cn.th.phonerf.activity.report.present.RptPresenterImpl;
import cn.th.phonerf.activity.report.view.IRptView;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.SaleBillDal;
import cn.th.phonerf.dal.SystemDal;
import cn.th.phonerf.model.AccountCashierInfo;
import cn.th.phonerf.model.SysSystem;
import cn.th.phonerf.model.t_sys_printer_head;
import cn.th.phonerf.utils.DateUtil;
import cn.th.phonerf.utils.ProgressUtils;
import cn.th.phonerf.utils.print.PrintPaper;
import cn.th.phonerf.utils.print.PrintString;

public class RptActivity  extends BaseActivity implements IRptView, View.OnClickListener  {

    private Context mContext;
    private Dialog loadingDlg;
    private IRptPresenter presenter;

    private TextView pageTitleText, rightText;
    private ImageView backText;
    private RelativeLayout errLayout;
    private TextView errText, errBtn;
    private List<String> Data;

    private String rptSql = "";

    private ListView lvInfo;
    ReportItemAdapter _adapter;

    EditText txtBgnDate;
    EditText txtEndDate;
    Button btnToday;
    Button btnYesterday;
    Button btnWeek;
    Button btnMoon;
    TextView lbMsg;
    boolean _isAll;

    private Calendar calendar; //通过Calendar获取系统时间

    IPrinterService printer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        try {
            mContext = this;
            loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), false);

            presenter = new RptPresenterImpl(this);
        }catch (Exception e){
            setToast("异常："+e);
        }

        //initPrinter();
        initUI();
        loadData();




        try {
            Button btnPrinting = findViewById(R.id.btnPrinting);
            btnPrinting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (Data == null || Data.size() <= 0){
                            setToast("请先查询报表！");
                        }else {
                            printData(Data);
                        }
                    }catch (Exception e){
                        setToast("异常："+e);
                    }
                }
            });
        }catch (Exception e){
            setToast("异常："+e);
        }

    }
    /*private IPrinterService getPrinter() {
        try {
            if(GlobalDefines.sm != null)
                return IPrinterService.Stub.asInterface(GlobalDefines.sm.getService(Const.PRINTER));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initPrinter() {
        try {
            printer = getPrinter();
            if (printer == null) {
                //Toast.makeText(getActivity(), "get printer failed!", Toast.LENGTH_SHORT).show();
                setToast("获取打印机失败!");
                return;
            }
            // tell sdk the widthmode(58mm or 80mm) of printer in use.
            Bundle bundle = new Bundle();
            String widthType = PrinterConsts.PRT_CFG_VALUE_WIDTH_MODE_58;
            bundle.putString(PrinterConsts.PRT_CFG_KEY_WIDTH_MODE, widthType);
            try {
                printer.setConfig(bundle);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch (Exception e){
            setToast("异常："+e);
        }

    }*/
    private void initUI() {
        try {
            //顶部
            pageTitleText = (TextView) findViewById(R.id.top_title_text);
            pageTitleText.setText("报表");
            backText = (ImageView) findViewById(R.id.top_back_image);
            backText.setVisibility(View.VISIBLE);
            backText.setOnClickListener(this);
            rightText = (TextView) findViewById(R.id.top_right_text);
            rightText.setText("查询");
            rightText.setVisibility(View.VISIBLE);
            rightText.setOnClickListener(this);

            txtBgnDate = (EditText)findViewById(R.id.txtBgnDate);
            txtEndDate = (EditText)findViewById(R.id.txtEndDate);
            btnToday = (Button)findViewById(R.id.btnToday);
            btnYesterday = (Button)findViewById(R.id.btnYesterday);
            btnWeek = (Button)findViewById(R.id.btnWeek);
            btnMoon = (Button)findViewById(R.id.btnMoon);
            lbMsg = (TextView)findViewById(R.id.lbMsg);
            //txtDeptId.setOnClickListener(this);
            txtBgnDate.setOnClickListener(this);
            txtEndDate.setOnClickListener(this);
            btnToday.setOnClickListener(this);
            btnYesterday.setOnClickListener(this);
            btnWeek.setOnClickListener(this);
            btnMoon.setOnClickListener(this);


            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            //日期格式
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("%d-%02d-%02d",
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH)));
            txtEndDate.setText(sb);

            sb = new StringBuffer();
            sb.append(String.format("%d-%02d-%02d",
                    calendar.get(Calendar.YEAR) ,
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH) - 1));
            txtBgnDate.setText(sb);


            CheckBox ckbAll = findViewById(R.id.ckbAll);
            ckbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    _isAll = b;
                }
            });
        }catch (Exception e){
            setToast("异常："+e);
        }
    }

    private void loadData() {
        try {

        } catch (Exception e) {
            setToast(e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.top_back_image:
                finish();
                break;
            case R.id.top_right_text: {
                getAccount();
                break;
            }
            case R.id.txtBgnDate:
                selectDate(txtBgnDate);
                break;
            case R.id.txtEndDate:
                selectDate(txtEndDate);
                break;
            case R.id.btnToday: {
                try {
                    calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    //日期格式
                    StringBuffer sb = new StringBuffer();
                    sb.append(String.format("%d-%02d-%02d",
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH)));
                    txtEndDate.setText(sb);
                    txtBgnDate.setText(sb);
                    getAccount();
                }catch (Exception e){
                    setToast("异常："+e);
                }
                break;
            }
            case R.id.btnYesterday: {
                try {
                    calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.add(Calendar.DATE,-1);
                    //日期格式
                    StringBuffer sb = new StringBuffer();
                    sb.append(String.format("%d-%02d-%02d",
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH)));
                    txtEndDate.setText(sb);
                    txtBgnDate.setText(sb);
                    getAccount();
                }catch (Exception e){
                    setToast("异常："+e);
                }

                break;
            }
            case R.id.btnWeek: {
                try {
                    calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    //日期格式
                    StringBuffer sb = new StringBuffer();
                    sb.append(String.format("%d-%02d-%02d",
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH) ));
                    txtEndDate.setText(sb);
                    txtBgnDate.setText( DateUtil.dateToString(DateUtil.getBeginDayOfWeek()) );
                    getAccount();
                }catch (Exception e){
                    setToast("异常："+e);
                }

                break;
            }
            /*case R.id.btnMoon: {
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                //日期格式
                StringBuffer sb = new StringBuffer();
                sb.append(String.format("%d-%02d-%02d",
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH)));
                txtEndDate.setText(sb);

                sb = new StringBuffer();
                sb.append(String.format("%d-%02d-%02d",
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH) - 1));
                txtBgnDate.setText(sb);
                break;
            }*/

        }
    }

    private void getAccount(){
        try {
            SaleBillDal bll = new SaleBillDal();
            PrintPaper paper = new PrintPaper();
            t_sys_printer_head headModel = new t_sys_printer_head();
            headModel.branch_no = GSale.StoreNo;
            headModel.branch_name = GSale.StoreName;
            headModel.store_no = GSale.StoreNo;
            headModel.store_name = GSale.StoreName;


            headModel.cashier_no =  _isAll ? "%" : GSale.CashierNo;
            headModel.cashier_name = _isAll ? "全部" : GSale.CashierNo;
            headModel.oper_date = DateUtil.getCurrentDateTime();

//            AccountCashierInfo entity = bll.getPayWayAmount(GSale.StoreNo, GSale.CashierNo
            AccountCashierInfo entity = bll.getPayWayAmount(GSale.StoreNo, "%"
                    , txtBgnDate.getText().toString(), txtEndDate.getText() + " 23:59:59.997");
            List<String> data = paper.PrintAccount(entity, true, headModel, false, 0);
            String data2 = "";
            for (int i = 0;i < data.size();i++){
                data2 += data.get(i)+"\r\n";
            }

            lbMsg.setText(data2);
            Data = data;
//            printData(data);

        }catch (Exception ex){
            setToast(ex.getMessage());
        }

    }

    private void printData(List<String> data){
        try {
            if(printer!= null){
                //printer.openCashBox();
                printer.beginTranscation();
                //printer.addImage(getBitmap());

                for(String item : data){

                    printer.addText(item + "\n", 0);

                }
                printer.addFeedLine(10);
                printer.addCut();
                int transId = printer.endTranscation();
                printer.commit(transId);
            }
        }catch (Exception ex){
            setToast("打印异常!打印异常!打印异常!\n" + ex.getMessage());
        }

    }

    private void selectDate(final EditText txtDisplay){
        //通过自定义控件AlertDialog实现
        AlertDialog.Builder builder = new AlertDialog.Builder(RptActivity.this);
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        //设置日期简略显示 否则详细显示 包括:星期周
        datePicker.setCalendarViewShown(false);
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        //设置date布局
        builder.setView(view);
        builder.setTitle("设置日期信息");
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                StringBuffer sb = new StringBuffer();
                sb.append(String.format("%d-%02d-%02d",
                        datePicker.getYear(),
                        datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth()));
                txtDisplay.setText(sb);
                //赋值后面闹钟使用
                /*mYear = datePicker.getYear();
                mMonth = datePicker.getMonth();
                mDay = datePicker.getDayOfMonth();*/
                dialog.cancel();
            }
        });
        builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
/*
    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
*/
    @Override
    public void resRequestData() {
        setToast("返回!");
    }

    @Override
    public void resGetData(com.alibaba.fastjson.JSONArray data) {

    }
}
