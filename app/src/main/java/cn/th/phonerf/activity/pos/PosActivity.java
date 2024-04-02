package cn.th.phonerf.activity.pos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.histonepos.npsdk.bind.SmartService;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.pay.PayActivity;
import cn.th.phonerf.activity.pay.PayXgActivity;
import cn.th.phonerf.activity.pos.dialog.InputDialogFragment;
import cn.th.phonerf.activity.pos.other.AdapterSaleFlowList;
import cn.th.phonerf.activity.pos.other.NoticeDialogListener;
import cn.th.phonerf.activity.pos.present.PosPresenterImpl;
import cn.th.phonerf.activity.pos.present.IPosPresenter;
import cn.th.phonerf.activity.pos.view.IPosView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.GConn;
import cn.th.phonerf.constant.GFunc;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.t_bd_item_infoDal;
import cn.th.phonerf.model.PosSaleWay;
import cn.th.phonerf.model.TBdItemInfo;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.model.t_rm_vip_info;
import cn.th.phonerf.utils.DBItemHelper;
import cn.th.phonerf.utils.DBUtil;
import cn.th.phonerf.utils.DateUtil;
import cn.th.phonerf.utils.ProgressUtils;


public class PosActivity extends BaseActivity implements IPosView, View.OnClickListener, NoticeDialogListener {

    // region ui
    private TextView pageTitleText, rightText;
    private ImageView backText;
    private RelativeLayout errLayout;
    private TextView errText, errBtn;
    TextView lbCashier;
    TextView lbTime;
    TextView lbSaleMan;
    TextView lbVipName;
    TextView lbPosId;
    TextView lbLastSheetText;
    TextView lbLastAmtText;
    TextView lbLastChgText;

    TextView lbInputBarcode;
//    TextView btnShoppingBag;
    TextView lbMessage;
    ImageView imgNoData;

    private TextView lbSaleTotalAmt;
    private TextView lbSaleTotalQty;
    private TextView lbMember;
    private TextView btnPayOnline;
    ListView gvSaleFlow;
    private TextView lbInput;
    // endregion

    // region data
    private Context mContext;
    private Dialog loadingDlg;
    private IPosPresenter presenter;
    AdapterSaleFlowList _adapterSaleFlowList;
    List<TRmSaleflow> _saleFlowList = new ArrayList<>();
    Double _saleTotalAmt = 0.00;
    Double _saleTotalQty = 0.00;

    /**
     * 倒计时
     */
    private int MAXIDLENUM = 300;
    private int _idleNum = MAXIDLENUM;
    private boolean _runningFlag = true;
    private Timer timer;
    private String TAG = "PosActivity";
    // endregion

    //String _flowNo = "";
    String _memo = "";
    PosSaleWay _curSaleWay = PosSaleWay.Sale;
    t_rm_vip_info _vipInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        try{
//            GSale.SaleMan = "";
            mContext = this;
            loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);
            presenter = new PosPresenterImpl(this);

        }catch (Exception e){
            setToast("异常："+e);
        }
        initUI();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetTouchEvent();
        _runningFlag = true;
        if(DBUtil.dbItemHelper == null) {
            DBUtil.dbItemHelper = new DBItemHelper(this);
            DBUtil.dbItemExec = DBUtil.dbItemHelper.getWritableDatabase();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        resetTouchEvent();
        _runningFlag = false;
    }

    @Override
    protected void onDestroy() {
        // _thread.interrupt();
        if(timer != null)
            timer.cancel();
        if(SmartService.getInstance() != null)
            SmartService.getInstance().unregister();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode >= 7 && keyCode <= 16){
            String str = lbInput.getText().toString();
            str += String.valueOf(keyCode - 7);
            lbInput.setText(str);
            return true;
        }else if(keyCode == KeyEvent.KEYCODE_ENTER){
            QueryData(lbInput.getText().toString());
            lbInput.setText("");
            return true;
        }
        System.err.println(keyCode);
        //lbVipName.setText();
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化UI
     */
    //int deviceIndex=-9999;//记录选中的item,-9999表示无选中记录
    private void initUI() {

        try{
//            GSale.SaleMan = "9999";
            //顶部
            pageTitleText = (TextView) findViewById(R.id.top_title_text);
            pageTitleText.setText(String.format("POS销售[%s]", GSale.PosId));
            backText = (ImageView) findViewById(R.id.top_back_image);
//            backText.setVisibility(View.VISIBLE);
//            backText.setOnClickListener(this);
            rightText = (TextView) findViewById(R.id.top_right_text);
            rightText.setText("不 买 了");
            rightText.setVisibility(View.VISIBLE);
            rightText.setBackgroundResource(R.drawable.shape_white_color);
            rightText.setTextColor(getResources().getColor(R.color.colorPrimary));
            rightText.setOnClickListener(this);

            lbSaleTotalAmt = findViewById(R.id.lbSaleTotalAmt);
//            lbSaleTotalQty = findViewById(R.id.lbSaleTotalQty);
            btnPayOnline = findViewById(R.id.btnPayOnline);
            lbInput = findViewById(R.id.lbInput);
            lbInputBarcode = findViewById(R.id.lbInputBarcode);
//            btnShoppingBag = findViewById(R.id.btnShoppingBag);

            lbMessage = (TextView)findViewById(R.id.lbMessage);
            lbCashier = (TextView)findViewById(R.id.lbCashier);
            lbTime = (TextView)findViewById(R.id.lbTime);
            lbSaleMan = (TextView)findViewById(R.id.lbSaleMan);
            lbVipName = (TextView)findViewById(R.id.lbVipName);
            lbPosId = (TextView)findViewById(R.id.lbPosId);
            lbLastSheetText = (TextView)findViewById(R.id.lbLastSheetText);
            lbLastAmtText = (TextView)findViewById(R.id.lbLastAmtText);
            lbLastChgText = (TextView)findViewById(R.id.lbLastChgText);
            //btnSaleForTot.setOnClickListener(this);
            gvSaleFlow = (ListView)findViewById(R.id.gvSaleFlow);
            imgNoData = findViewById(R.id.imgNoData);
            lbMember = findViewById(R.id.lbMember);

            lbMember.setOnClickListener(this);
            lbInputBarcode.setOnClickListener(this);
//            btnShoppingBag.setOnClickListener(this);
            btnPayOnline.setOnClickListener(this);
        }catch (Exception e){
            setToast("异常："+e);
        }
    }

    private void setPosInfo(){
        try{
            lbCashier.setText("[" + GSale.CashierNo + "]" + GSale.CashierName);
            lbPosId.setText("[" + GSale.PosId + "]" + (GConn.IsConnectServer() ? "联网" : "断网"));
        }catch (Exception e){
            setToast("异常："+e);
        }
    }

    private void loadData(){
        try{
            //_flowNo = GFunc.GetFlowNo(mContext);
            //界面显示
            loadThreadData();
            setPosInfo();
//            GSale.Discount = Double.valueOf(  SharePreferenceUtils.getValue(mContext, "total_amount", "1") );

            loadSaleData();
        }catch (Exception e){
            setToast("异常："+e);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        resetTouchEvent();
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 时间
     */
    private void loadThreadData() {
        /*if (timer == null){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                lbTime.setText(DateUtil.getCurrentDateTime());
                            }catch (Exception e){
                                Log.e("异常：",e.getMessage());
                            }
                        }
                    });
                }
            },0,1000);
        }*/
        if (timer == null){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                if(_runningFlag) {
                                    _idleNum--;
                                    // setToast(_idleNum + "");
                                }

                                if(_idleNum <= 0){
                                    resetTouchEvent();
                                    /*_saleFlowList.clear();
                                    _adapterSaleFlowList.notifyDataSetChanged();
                                    calcSheet();*/
                                    presenter.doClearCart(null, GSale.PosId);
                                }
                                lbTime.setText(DateUtil.getCurrentDateTime() + " " + _idleNum);
                                // rightText.setText(_idleNum + "");
                            }catch (Exception e){
                                Log.e("异常：",e.getMessage());
                            }

                        }
                    });

                }
            },0,1000);

        }
    }
    private void resetTouchEvent(){
        _idleNum = MAXIDLENUM;
    }

    private void loadSaleData(){
        _adapterSaleFlowList = new AdapterSaleFlowList(PosActivity.this, R.layout.ls_gvsale_row, _saleFlowList);
        gvSaleFlow.setAdapter(_adapterSaleFlowList);
        //region region:单击商品流水弹出对话框
        _adapterSaleFlowList.setOnItemClickEvent(new AdapterSaleFlowList.onItemClickEvent() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(final TRmSaleflow item, View v, final int position, int flag) {
                switch(flag){
                    case 1:
                    case -1:{
                        //calcSheet();
                        presenter.doUpdateCart(v, GSale.PosId, item.getCom_no(), item.getSale_qnty());
                        break;
                    }
                    case 0: {
                        //删除功能 弹出对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        final AlertDialog dialog = builder.create();
                        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.dialog_std, null);
                        TextView lbTitle = view.findViewById(R.id.lbTitle);
                        TextView lbDesc = view.findViewById(R.id.lbDesc);
                        Button btnCancel = view.findViewById(R.id.btnCancel);
                        Button btnOk = view.findViewById(R.id.btnOk);
                        lbTitle.setText("确定要删除");
                        lbDesc.setText(item.getItem_name());
                        btnCancel.setText("确认删除");
                        btnOk.setText("我再想想");
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //_saleFlowList.remove(position);
                                //_adapterSaleFlowList.notifyDataSetChanged();
                                presenter.doRemoveCart(v, GSale.PosId, item.getCom_no());
                                dialog.cancel();
                            }
                        });
                        dialog.setCancelable(false);
                        dialog.show();
                        dialog.getWindow().setContentView(view); // show()之后重新布局解决黑边
                    }
                }
            }
        });
        //endregion
        presenter.doQueryCart(null, GSale.PosId);
        presenter.doGetShoppingBagItem(null);

    }
    //endregion
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back_image:
                //funcFunc();
                break;
            case R.id.top_right_text:{
                //弹出对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                final AlertDialog dialog = builder.create();
                LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.dialog_std, null);
                TextView lbTitle = view.findViewById(R.id.lbTitle);
                TextView lbDesc = view.findViewById(R.id.lbDesc);
                Button btnCancel = view.findViewById(R.id.btnCancel);
                Button btnOk = view.findViewById(R.id.btnOk);
                lbTitle.setText("确定先不买了");
//                lbDesc.setText("确定先不买了？");
                btnCancel.setText("确认删除");
                btnOk.setText("我再想想");
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       presenter.doClearCart(rightText, GSale.PosId);
                        dialog.cancel();
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
                dialog.getWindow().setContentView(view); // show()之后重新布局解决黑边
                break;
            }
            case R.id.lbInputBarcode:{
                InputDialogFragment dlg = new InputDialogFragment();
                //dlg.show(getSupportFragmentManager(), "InputDialogFragment");
                dlg.show(getFragmentManager(), "InputDialogFragment");
                break;
            }
            case R.id.lbMember:{
                if(_vipInfo != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    final AlertDialog dialog = builder.create();
                    LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.dialog_std, null);
                    TextView lbTitle = view.findViewById(R.id.lbTitle);
                    TextView lbDesc = view.findViewById(R.id.lbDesc);
                    Button btnCancel = view.findViewById(R.id.btnCancel);
                    Button btnOk = view.findViewById(R.id.btnOk);
                    lbTitle.setText("是否取消当前会员的交易");
                    lbDesc.setText("");
                    btnCancel.setText("确认清除");
                    btnOk.setText("我再想想");
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //_saleFlowList.remove(position);
                            //_adapterSaleFlowList.notifyDataSetChanged();
                            //presenter.doRemoveCart(v, GSale.PosId, item.getCom_no());
                            lbVipName.setText("");
                            _vipInfo = null;
                            presenter.doVipUpdateFlow(null, GSale.PosId, _vipInfo != null ? _vipInfo.getCard_id() : "");
                            dialog.cancel();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.show();
                    dialog.getWindow().setContentView(view); // show()之后重新布局解决黑边
                    return;
                }
                InputDialogFragment dlg = new InputDialogFragment("输入会员", 1);
                //dlg.setTitle("输入会员", 1);
                dlg.show(getFragmentManager(), "InputDialogFragment");
                break;
            }
//            case R.id.btnShoppingBag:{
//                //presenter.doGetShoppingBagItem(null);
//                ShoppingBagDialogFragment dlg = new ShoppingBagDialogFragment();
//                //dlg.show(getSupportFragmentManager(), "InputDialogFragment");
//                dlg.show(getFragmentManager(), "ShoppingBagDialogFragment");
//                break;
//            }
            case R.id.btnPay: {
                calcSheet();
                if(_saleFlowList.size() == 0){
                    setToast("请先扫描商品，再付款");
                    return;
                }


                try{
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    final AlertDialog dialog = builder.create();
                    //region ----布局----
                    LinearLayout dialogView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.dlg_pay_way,null);
                    ImageButton btnPayXg = dialogView.findViewById(R.id.btnPayXg);
                    ImageButton btnPayWx = dialogView.findViewById(R.id.btnPayWx);
                    btnPayXg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            // 访问折扣服务
                            presenter.doPreForAllDis(btnPayXg, GSale.StoreNo, GSale.PosId );
                            /*
                            Intent intent = new Intent(PosActivity.this, PayXgActivity.class);
                            intent.putExtra("order_flow", (Serializable)_saleFlowList);
                            intent.putExtra("vip_info", (Serializable)_vipInfo);
                            intent.putExtra("sale_amount", _saleTotalAmt.doubleValue());
                            intent.putExtra("sale_qnty", _saleTotalQty.doubleValue());
                            startActivityForResult(intent, 100);*/
                        }
                    });
                    btnPayWx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            Intent intent = new Intent(PosActivity.this, PayActivity.class);
                            intent.putExtra("order_flow", (Serializable)_saleFlowList);
                            intent.putExtra("vip_info", (Serializable)_vipInfo);
                            intent.putExtra("sale_amount", _saleTotalAmt.doubleValue());
                            intent.putExtra("sale_qnty", _saleTotalQty.doubleValue());
                            startActivityForResult(intent, 100);
                        }
                    });

                    dialog.show();
                    dialog.getWindow().setContentView(dialogView); // show()之后重新布局解决黑边

                }catch (Exception e){
                    setToast("5异常："+e);
                }



                break;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 101 && data != null) {
//        }
        switch (requestCode){
            // 付款返回
            case 100:{
                if(resultCode == RESULT_OK) {
                    clearInput();
                    dialogView("");
                }
                break;
            }
            // 设置窗口保存返回
            case 101:{
                if(resultCode == RESULT_OK) {
                    /*initApi();
                    loadSetupData();
                    // 删除并下载数据
                    downloadData();*/
                }
                break;
            }
        }
    }

    private void clearInput(){
        try{

            //txtBarcode.setText("");
            //lbSaleMan.setText("9999");
            //GSale.SaleMan = "9999";
            //GSale.SaleName = "999";
            //_flowNo = GFunc.GetFlowNo(mContext);
            _memo = "";
            _curSaleWay = PosSaleWay.Sale;
            _saleFlowList.clear();
            _adapterSaleFlowList.clear();
            _adapterSaleFlowList.notifyDataSetChanged();
            calcSheet();
        }catch (Exception e){
            setToast("2异常："+e);
        }
    }
    private void resetSaleState(boolean bOnLoading, boolean bSetFlowNo) {
        try {
            if(bSetFlowNo)
                GFunc.setFlowNo(mContext);
            lbMessage.setText("开始新的交易.....");
            //1.清除输入内容
            clearInput();
            setPosInfo();
        }catch (Exception ex){

        }
    }
    @Override
    public void showErrorMsg(String errStr) {
        try{
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
        }catch (Exception e){
            setToast("3异常："+e);
        }

    }

    private void calcSheet(){
        try {
            Double qty = 0.00;
            Double amt = 0.00;

            for (TRmSaleflow item: _saleFlowList
            ) {
                qty += (item.getMeasure_flag() == 1 ? 1 : item.getSale_qnty());
                amt += item.getSale_money();
            }

            DecimalFormat df = new DecimalFormat("0.##");
            String qnty = df.format(qty);
            String amty = df.format(amt);
            lbSaleTotalAmt.setText(amty);
            lbSaleTotalQty.setText(qnty);
            this._saleTotalAmt = Double.parseDouble(amty);
            this._saleTotalQty = Double.parseDouble(qnty);

            if(_saleFlowList.size() == 0){
                imgNoData.setVisibility(View.VISIBLE);
                gvSaleFlow.setVisibility(View.GONE);
            } else {
                gvSaleFlow.setVisibility(View.VISIBLE);
                imgNoData.setVisibility(View.GONE);
            }
            _adapterSaleFlowList.notifyDataSetChanged();
        }catch (Exception ex){
            setToast(ex.getMessage());
        }

    }

    private void calcData(List<TRmSaleflow> data){
        try {
            Double qty = 0.00;
            Double amt = 0.00;

            for (TRmSaleflow item: _saleFlowList
            ) {
                qty += (item.getMeasure_flag() == 1 ? 1 : item.getSale_qnty());
                amt += item.getSale_money();
            }

            DecimalFormat df = new DecimalFormat("0.##");
            String qnty = df.format(qty);
            String amty = df.format(amt);
            lbSaleTotalAmt.setText(amty);
            lbSaleTotalQty.setText(qnty);
            this._saleTotalAmt = Double.parseDouble(amty);
            this._saleTotalQty = Double.parseDouble(qnty);

            if(_saleFlowList.size() == 0){
                imgNoData.setVisibility(View.VISIBLE);
                gvSaleFlow.setVisibility(View.GONE);
            } else {
                gvSaleFlow.setVisibility(View.VISIBLE);
                imgNoData.setVisibility(View.GONE);
            }
            _adapterSaleFlowList.notifyDataSetChanged();
        }catch (Exception ex){
            setToast(ex.getMessage());
        }

    }

    private void dialogView(String dialogText){
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            final AlertDialog dialog = builder.create();
            //region ----布局----
            LinearLayout dialogView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.dlg_paysuccess,null);
            TextView btnNumCancel = (TextView)dialogView.findViewById(R.id.btnNumCancel);
            btnNumCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            dialog.getWindow().setContentView(dialogView); // show()之后重新布局解决黑边
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, 3000);
        }catch (Exception e){
            setToast("4异常："+e);
        }
    }

    //#region region: 商品操作区域
    private void addGvSaleFlowItem(TRmSaleflow item){
        try{
            _saleFlowList.add(item);
//            _saleFlowList.add(item);
            _adapterSaleFlowList.notifyDataSetChanged();
//            _adapterSaleFlowList.setSelectPosition(_saleFlowList.size() - 1 /* _adapterSaleFlowList.getCount()-1*/);
            gvSaleFlow.setSelection(_saleFlowList.size() - 1 );
            calcSheet();
        }catch (Exception e){
            setToast("异常："+e);
        }
    }

    @Override
    public void addSaleFlowItem(TRmSaleflow item) {
//        this.drawerLayout.closeDrawers();
        if(_curSaleWay == PosSaleWay.RetSale){
            //setToast("");
            lbMessage.setText("退货状态，禁止添加商品");
            return;
        }
        addGvSaleFlowItem(item);
    }

    @Override
    public void resRequestData() {
        setToast("成功!");
    }


    @Override
    public void resItemInfo(TBdItemInfo data) {

    }

    @Override
    public void resPromote(TBdItemInfo data){

    }

    /**
     * 在线查询
     */
    private void QueryData(String barcode) {
        try{
            switch (barcode){
                case "00000000":
                    finish();
                    break;
                case "00002468":
                    presenter.doVersion();
                    break;
                default:
                    presenter.doAddCart(null, (_vipInfo !=null? _vipInfo.getCard_id() :""), barcode, GSale.StoreNo, GSale.PosId, GSale.CashierNo);
                    break;
            }
        }catch (Exception e){
            setToast(e.getMessage());
        }
    }
    //endregion


    @Override
    public void resRequestData(String tag, String msg, JSONObject response) {
        try {
            switch (tag) {
                case ApiConstants.ADD_TO_CART:
                case ApiConstants.QUERY_CART:
                case ApiConstants.UPDATE_CART:
                case ApiConstants.CLEAR_CART:
                case ApiConstants.REMOVE_CART:
                case ApiConstants.VIP_UPDATE_FLOW:
                {
                    //List<TRmSaleflow> data = (List<TRmSaleflow>) JsonUtils.JsonStrToClass (response.getString("data"), TRmSaleflow.class);

                    List<TRmSaleflow> data = JSONArray.parseArray(response.getString("data"), TRmSaleflow.class);
//                    addGvSaleFlowItem(data);
                    _saleFlowList.clear();
                    if(data != null)
                        _saleFlowList.addAll(data);
                    _adapterSaleFlowList.notifyDataSetChanged();
                    gvSaleFlow.setSelection(_saleFlowList.size() - 1 );
                    calcSheet();
                    break;
                }
                /*case ApiConstants.GOODS_LIST:{
                    String data = response.getString("data");
                    List<GoodsInfoVo> list = JSONArray.parseArray(data, GoodsInfoVo.class);
                    _listGoods.clear();
                    _listGoods.addAll(list);
                    _adapterGoods.notifyDataSetChanged();
                    break;
                }*/
                /*case ApiConstants.CLEAR_CART:{
                    _saleFlowList.clear();
                    _adapterSaleFlowList.notifyDataSetChanged();
                    break;
                }*/
                case ApiConstants.PRE_FOR_ALL_DISCOUNT:{
                    List<TRmSaleflow> data = JSONArray.parseArray(response.getString("data"), TRmSaleflow.class);

                    Intent intent = new Intent(PosActivity.this, PayXgActivity.class);
                    if(data == null){
                        intent.putExtra("order_flow", (Serializable)_saleFlowList);
                        intent.putExtra("vip_info", (Serializable)_vipInfo);
                        intent.putExtra("sale_amount", _saleTotalAmt.doubleValue());
                        intent.putExtra("sale_qnty", _saleTotalQty.doubleValue());
                    }else{
                        Double qty = 0.00;
                        Double amt = 0.00;
                        for (TRmSaleflow item: data
                        ) {
                            qty += (item.getMeasure_flag() == 1 ? 1 : item.getSale_qnty());
                            amt += item.getSale_money();
                        }

                        DecimalFormat df = new DecimalFormat("0.##");
                        String qnty = df.format(qty);
                        String amty = df.format(amt);

                        intent.putExtra("order_flow", (Serializable)data);
                        intent.putExtra("vip_info", (Serializable)_vipInfo);
                        intent.putExtra("sale_amount",  Double.parseDouble(amty));
                        intent.putExtra("sale_qnty", Double.parseDouble(qnty));
                    }
                    startActivityForResult(intent, 100);
                    calcSheet();
                    break;
                }
                case ApiConstants.GET_SHOPPINGBAG_ITEM:{
                    JSONArray jsonArray = JSONArray.parseArray(response.getString("data"));
                    if(jsonArray != null) {
                        t_bd_item_infoDal dal = new t_bd_item_infoDal();
                        dal.insertList(jsonArray);
                    }
                    break;
                }
                case ApiConstants.QUERY_VIP:{
                    lbVipName.setText("");
                    _vipInfo = com.alibaba.fastjson.JSONObject.parseObject(response.getString("data"), t_rm_vip_info.class);
                    if(_vipInfo != null)
                        lbVipName.setText(String.format("会员卡号[%s] 姓名[%s] \n积分：%.2f 类型：%s  \n余额：%.2f"
                            , _vipInfo.getCard_id()
                            , _vipInfo.getVip_name()
                            , _vipInfo.getAcc_num()
                            , _vipInfo.getType_name()
                            , _vipInfo.getRemain_amt()));
                    presenter.doVipUpdateFlow(null, GSale.PosId, _vipInfo != null ? _vipInfo.getCard_id() : "");
                    break;
                }
//                case ApiConstants.VIP_UPDATE_FLOW: {
//
//
//                    break;
//                }
                default:
                    break;

            }
        }catch (Exception e){
//            e.printStackTrace();
            setToast(e.getMessage());
        }
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Object object, int inputFlag) {
//        dialog.dismiss();
        //if(object is)
       switch (inputFlag){
           case 0:
               QueryData(object.toString());
               break;
           case 1: // vip
               presenter.doGetVipInfo(null, object.toString());
               break;
       }

        //lbInput.setText("");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}

