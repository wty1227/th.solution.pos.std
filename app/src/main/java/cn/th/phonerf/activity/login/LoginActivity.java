package cn.th.phonerf.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.histonepos.npsdk.api.IServiceManager;
import com.histonepos.npsdk.bind.IServiceConnector;
import com.histonepos.npsdk.bind.SmartService;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.login.present.ILoginPresenter;
import cn.th.phonerf.activity.login.present.LoginPresenterImpl;
import cn.th.phonerf.activity.login.view.ILoginView;
import cn.th.phonerf.activity.pos.PosActivity;
import cn.th.phonerf.activity.setup.SetupActivity;
import cn.th.phonerf.activity.sync.SyncActivity;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GPrinter;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.constant.GlobalDefines;
import cn.th.phonerf.dal.MerchantShopDal;
import cn.th.phonerf.model.MerchantPhone;
import cn.th.phonerf.model.MerchantUser;
import cn.th.phonerf.model.TSysOperator;
import cn.th.phonerf.model.TSysPosStatus;
import cn.th.phonerf.model.UserGrant;
import cn.th.phonerf.printer.HisensePrinter;
import cn.th.phonerf.utils.DBItemHelper;
import cn.th.phonerf.utils.DBUtil;
import cn.th.phonerf.utils.DateUtil;
import cn.th.phonerf.utils.DeviceUtils;
import cn.th.phonerf.utils.HttpUtils;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.UserUtil;
import cn.th.phonerf.view.CleanableEditText;

public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener {
    private Context mContext;
    // private Dialog loadingDlg;
    private ILoginPresenter presenter;

    private TextView pageTitleText, rightText;
    private TextView backText;

    CleanableEditText txtLoginLogin;
    CleanableEditText txtLoginPass;
    Button btnLogin;
    private TextView lbPhoneSn;
    FloatingActionButton fabCancel;

    private RelativeLayout errLayout;
    private TextView errText, errBtn;


//    TextView lbSoftwareType;

    String TAG = "LoginActivity";

    Button btnEditNum0; // = (Button)view.findViewById(R.id.btnEditNum0);
    Button btnEditNum1; // = (Button)view.findViewById(R.id.btnEditNum1);
    Button btnEditNum2; // = (Button)view.findViewById(R.id.btnEditNum2);
    Button btnEditNum3; // = (Button)view.findViewById(R.id.btnEditNum3);
    Button btnEditNum4; // = (Button)view.findViewById(R.id.btnEditNum4);
    Button btnEditNum5; // = (Button)view.findViewById(R.id.btnEditNum5);
    Button btnEditNum6; // = (Button)view.findViewById(R.id.btnEditNum6);
    Button btnEditNum7; // = (Button)view.findViewById(R.id.btnEditNum7);
    Button btnEditNum8; // = (Button)view.findViewById(R.id.btnEditNum8);
    Button btnEditNum9; // = (Button)view.findViewById(R.id.btnEditNum9);
    Button btnEditNumDot; // = (Button)view.findViewById(R.id.btnEditNumDot);
    Button btnEditNumBackspace; // = (Button)view.findViewById(R.id.btnEditNumBackspace);

    TextView lbTest1;
    TextView lbTest2;
    TextView lbTest3;
    TextView lbTest4;
    TextView lbTest5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //判断日期
        /*if(DateUtil.getCurrentDate().compareTo("2019-08-31") > 0){
            finish();
        }*/

        mContext = this;
        // loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);
        //获取用户信息
//            UserUtil.getMerchantInFo(mContext);
        presenter = new LoginPresenterImpl(this);
        UserUtil.getBaseSetup(mContext);

        initPrinterService();
        initUI();
        loadData();
        getPhoneInfo();
//        //版本号等等
        initApi();
        txtLoginLogin.requestFocus();

//        wxPayFaceInit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(DBUtil.dbItemHelper == null) {
            DBUtil.dbItemHelper = new DBItemHelper(this);
            DBUtil.dbItemExec = DBUtil.dbItemHelper.getWritableDatabase();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //SmartService.getInstance().unregister();
        //android.os.Process.killProcess(android.os.Process.myPid());
    }


    private void initApi() {
        //获取版本号
        presenter.doVersion();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        //顶部

        try{
//            lbSoftwareType = findViewById(R.id.lbSoftwareType);
            txtLoginLogin = findViewById(R.id.login_login);
            txtLoginPass = findViewById(R.id.login_password);
            btnLogin = findViewById(R.id.login_submit);
            btnLogin.setOnClickListener(this);

            //错误提示
            errLayout = findViewById(R.id.login_err_layout);
            errLayout.setOnClickListener(this);
            errText = findViewById(R.id.login_err_text);
            errBtn = findViewById(R.id.login_err_btn);
            //
            //spShopId = (Spinner) findViewById(R.id.login_shop_info);
            lbPhoneSn = findViewById(R.id.login_lbDeviceSn);
            fabCancel = findViewById(R.id.fabCancel);

            //initShop();
            txtLoginLogin.setText("");
            txtLoginPass.setText("");

            btnEditNum0 = findViewById(R.id.btnEditNum0);
            btnEditNum1 = findViewById(R.id.btnEditNum1);
            btnEditNum2 = findViewById(R.id.btnEditNum2);
            btnEditNum3 = findViewById(R.id.btnEditNum3);
            btnEditNum4 = findViewById(R.id.btnEditNum4);
            btnEditNum5 = findViewById(R.id.btnEditNum5);
            btnEditNum6 = findViewById(R.id.btnEditNum6);
            btnEditNum7 = findViewById(R.id.btnEditNum7);
            btnEditNum8 = findViewById(R.id.btnEditNum8);
            btnEditNum9 = findViewById(R.id.btnEditNum9);
            btnEditNumDot = findViewById(R.id.btnEditNumDot);
            btnEditNumBackspace = findViewById(R.id.btnEditNumBackspace);
            lbTest1 = findViewById(R.id.lbTest1);
            lbTest2 = findViewById(R.id.lbTest2);
            lbTest3 = findViewById(R.id.lbTest3);
            lbTest4 = findViewById(R.id.lbTest4);
            lbTest5 = findViewById(R.id.lbTest5);

            fabCancel.setOnClickListener(this);
            btnEditNum0.setOnClickListener(this);
            btnEditNum1.setOnClickListener(this);
            btnEditNum2.setOnClickListener(this);
            btnEditNum3.setOnClickListener(this);
            btnEditNum4.setOnClickListener(this);
            btnEditNum5.setOnClickListener(this);
            btnEditNum6.setOnClickListener(this);
            btnEditNum7.setOnClickListener(this);
            btnEditNum8.setOnClickListener(this);
            btnEditNum9.setOnClickListener(this);
            btnEditNumDot.setOnClickListener(this);
            btnEditNumBackspace.setOnClickListener(this);
            lbTest1.setOnClickListener(this);
            lbTest2.setOnClickListener(this);
            lbTest3.setOnClickListener(this);
            lbTest4.setOnClickListener(this);
            lbTest5.setOnClickListener(this);
        }catch (Exception e){
            setToast("异常："+e);
        }


    }

    private void loadData(){
        try{
            UserUtil.getLogin(mContext);
            txtLoginLogin.setText(GSale.CashierNo);
            txtLoginPass.setText(GSale.CashierPassword);
        }catch (Exception e){
            setToast("异常："+e);
        }

    }


    private void getPhoneInfo(){
        try{
            TelephonyManager mTm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            AppParam.PhoneSn = DeviceUtils.getIMEI(this);
            //AppParam.phone = DeviceUtils.getPhoneNumber(this);
            lbPhoneSn.setText("sn: " + AppParam.PhoneSn  + " 版本:" +  DeviceUtils.getAppVersion(this) );
            if (AppParam.PhoneSn.isEmpty()) {
                setToast("无法读取机器状态,不能继续使用!");
                return;
            }
        }catch (Exception e){
            setToast("异常："+e);
        }

    }

/*
    String _inputStr = "";
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode >= 7 && keyCode <= 54) || keyCode == 70) {
            String str = _inputStr;// lbInput.getText().toString();
            str += String.valueOf((char) event.getUnicodeChar());
            _inputStr = (str);
            return true;
        } else if(keyCode == KeyEvent.KEYCODE_ENTER){
            funcSetup(_inputStr);
            //setToast(lbInput.getText().toString());
            _inputStr = "";
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }*/

    private void funcSetup(String str) {
        try {
            //String decode = Des3.decode(str, Des3.key);
            switch (str) {
                case "246813": {
//                    initApi();
                    Intent intent = new Intent(this, SetupActivity.class);
                    //intent.putExtra("initFlag", false);
                    //startActivityForResult(intent, 101);
                    startActivity(intent);
                    break;
                }
                // 交换数据
                case "002468": {
                    initApi();
                    //downloadData();
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 获取APP版本信息
     * @param ctx
     * @return
     */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            String verName = packageInfo.versionName;
            Log.d("TAG", "当前版本号：" + verName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.fabCancel:
                finish();
                break;
            case R.id.top_left_text:

                break;
            case R.id.login_submit:
                try {
                    if(txtLoginPass.getText().toString().equals("246813")
                            || txtLoginPass.getText().toString().equals("002468")){
                        funcSetup(txtLoginPass.getText().toString());
                                return;
                    }
                    if(!printStatus()){
                        setToast("打印机异常，不能继续下一步");
                        return;
                    }
                    AppParam.PhoneSn = DeviceUtils.getIMEI(this);
                    presenter.doGetPosInfo(btnLogin, AppParam.PhoneSn, DeviceUtils.getAppVersion(this));
                    //开启service服务,轮询上传流水信息
//                    Intent startIntent = new Intent(mContext, FlowService.class);
//                    startService(startIntent);
                }catch (Exception e){
                    setToast(e.getMessage());
                }
                break;
            case R.id.btnEditNum0:
            case R.id.btnEditNum1:
            case R.id.btnEditNum2:
            case R.id.btnEditNum3:
            case R.id.btnEditNum4:
            case R.id.btnEditNum5:
            case R.id.btnEditNum6:
            case R.id.btnEditNum7:
            case R.id.btnEditNum8:
            case R.id.btnEditNum9:
            case R.id.btnEditNumDot:
            case R.id.btnEditNumBackspace:{
                try{
                    EditText editText;
                    if(txtLoginPass.isFocused())
                        editText = txtLoginPass;
                    else
                        editText = txtLoginLogin;
                    String str = editText.getText().toString();

                    Button sender = (Button)view;
                    String num = (String) sender.getText();
                    if(num.equals("退格")) {
                        editText.setText(str.substring(0, str.length() == 0 ? 0 : str.length() - 1));
                        return;
                    }
                    //5位宽度
                    if(str.length() >= 6){
                        return;
                    }
                    else if(num.equals(".")){
                        if(str.indexOf(".") != -1 )
                            break;
                        else
                            editText.setText(str+num);
                    }
                    else
                        editText.setText(str+num);
                    editText.setSelection(editText.getText().length());
                }catch (Exception e){
                    setToast("异常："+e);
                }
                break;
            }
            case R.id.lbTest1:{
                wxPayFaceInit();
                break;
            }
            case R.id.lbTest2:{
                getWxpayfaceRawdata();
                break;
            }
            case R.id.lbTest3:{
                get_wxpayface_authinfo();
                break;
            }

        }
    }

    public boolean printStatus(){
        boolean status = false;
        switch (GPrinter.printName){
            case "none":
                status = true;
                break;
            case "hisense":
                status = GPrinter.basePrinter.getPrinterReady();
                break;
            default:
                break;
        }
        return status;
    }

    public void initPrinterService(){
        switch (GPrinter.printName){
            case "none":

                break;
            case "hisense": {
                GPrinter.basePrinter = new HisensePrinter();
                SmartService.getInstance().register(GlobalDefines.mApplicationContext, new IServiceConnector() {
                    @Override
                    public void onServiceDisconnected() {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onServiceConnected(IServiceManager serviceManager) {
                        // TODO Auto-generated method stub
                        //Log.i(TAG,"onServiceConnected:" + (System.currentTimeMillis() - rtcClick));
                        //Log.i("PosPad_Activity", "onServiceConnected");
                        //Step 2: get apiHander
                        GlobalDefines.sm  = serviceManager;
                        if (GlobalDefines.sm!=null) {
                                //					Toast.makeText(PosPad_Activity.this, "Bind SmartService Success", Toast.LENGTH_SHORT).show();
                            try {
                                GPrinter.basePrinter.initPrinter();// .initPrinter();
                            } catch (Exception e) {
                                setToast(e.getMessage());
                            }
                        }
                    }
                });
                break;
            }
            default:
                break;
        }

    }

    /**
     * 1. 获取机器信息,门店信息
     * @param data
     */
    @Override
    public void resGetPosInfo(TSysPosStatus data){
        try{
            AppParam.appId = data.getAppId();
            AppParam.appKey = data.getAppKey();

            GSale.PosId = data.getPosid();
//            GSale.StoreNo = data.getHostip().substring(0,5);
            GSale.StoreNo = data.getHostip();
            GSale.PosId = data.getPosid();
            GSale.StoreName = data.getBranch_name();
            //验证密码
            presenter.doLogin(btnLogin, txtLoginLogin.getText().toString(), txtLoginPass.getText().toString(), GSale.StoreNo);
        }catch (Exception e){
            setToast("异常："+e);
        }

    }

    /**
     * 2. 判断用户名和密码是否正确
     * @param data
     */
    @Override
    public void resGetLoginInfo(TSysOperator data){
        try{
            UserUtil.saveLogin(mContext, data);
            GSale.CashierNo = data.getOper_id();
            GSale.CashierName = data.getOper_name();

            Intent intent = new Intent(this, SyncActivity.class);
            startActivity(intent);
        }catch (Exception e){
            setToast("异常："+e);
        }

    }

    /**
     * 1. 判断用户名和密码是否正确
     * @param data
     */
    @Override
    public void saveUserData(MerchantUser data) {

        try{
            AppParam.UId = data.getuId();
            GSale.CashierNo = data.getUserId();
            GSale.CashierName = data.getUserName();
            //GSale.StoreNo = shopId;
            //GSale.StoreName = shopName;
            //ApiConstants.LOCAL_API_URL = apiUrl;
            //AppParam.MShop = (MerchantShop)spShopId.getSelectedItem();

            presenter.doGetGrants(btnLogin, AppParam.UId, GSale.CashierNo, GSale.StoreNo, AppParam.MerchantId);
        }catch (Exception e){
            setToast("异常："+e);
        }


    }

    /**
     * 1.5 获取权限信息
     * @param data
     */
    @Override
    public void resGetGrants(List<UserGrant> data) {
        try{
            AppParam.Grants = (ArrayList<UserGrant>) data;
            //获取手机信息
            presenter.doGetPhoneInfo(btnLogin, AppParam.phone, AppParam.PhoneSn, DeviceUtils.getAppVersion(this), GSale.CashierNo, AppParam.MerchantId );
        }catch (Exception e){
            setToast("异常："+e);
        }

    }
    /**
     * 2. 获取手机信息, 并且判断手机是否可用, 判断是否可以登录选择的门店
     * @param data
     */
    @Override
    public void resGetPhoneInfo(MerchantPhone data) {
        try{
            if(data.getClearFlag().equals("1")){
                setToast("本手机被禁止使用,请联系本地系统管理员");
                return;
            }
            //GSale.PosId = data.getPhoneId();
            //判断是否可以登录该门店
            presenter.doCanLoginShop(btnLogin, data.getUserId(),  GSale.StoreNo, data.getMerchantId());
        }catch (Exception e){
            setToast("异常："+e);
        }

    }

    /**
     * 3. 成功登录, 更新本地门店时间信息
     */
    @Override
    public void successLogin() {
        //更新门店登录信息
        MerchantShopDal dal = new MerchantShopDal();
        try {
            dal.updateLoginDate(AppParam.MerchantId, GSale.StoreNo);
        }catch (Exception ex){
            this.setToast("异常:" + ex.getMessage());
        }
        //打开主界面
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }

    @Override
    public void saveShopData(JSONArray data) {

        final String sqlInsert = "replace into merchant_shop(shop_id,shop_name,merchant_id,api_url,clear_flag,oper_date," +
                "deploy_flag, jdbc_driver, jdbc_url, jdbc_username, jdbc_password) " +
                "values(?,?,?,?,?,'2019-01-01', ?,?,?,?,?)";
        final String[] lstParam = { "shopId", "shopName", "merchantId", "apiUrl", "clearFlag",
                "deployFlag","jdbcDriver","jdbcUrl","jdbcUsername","jdbcPassword"  };


        try {
            //开启事物
            DBUtil.dbItemExec.beginTransaction();
            DBUtil.dbItemExec.execSQL("delete from merchant_shop");
            for (int i = 0; i < data.size(); i++) {
                com.alibaba.fastjson.JSONObject node = data.getJSONObject(i);
                DBUtil.dbItemExec.execSQL(sqlInsert, DBUtil.GetSqliteParam(node, lstParam));
            }
            DBUtil.dbItemExec.setTransactionSuccessful();
        }catch (Exception e) {
            this.setToast(e.getMessage());
        }finally {
            DBUtil.dbItemExec.endTransaction();
        }
    }

    @Override
    public void saveSysSystem(JSONArray data) {

        final String sqlInsert = "replace into sys_system(sys_var_id,sys_var_name,sys_var_value,merchant_id) " +
                "values(?,?,?,?)";
        final String[] lstParam = { "sysVarId", "sysVarName", "sysVarValue", "merchantId"};
        try {
            //开启事物
            DBUtil.dbItemExec.beginTransaction();
            DBUtil.dbItemExec.execSQL("delete from sys_system");
            for (int i = 0; i < data.size(); i++) {
                com.alibaba.fastjson.JSONObject node = data.getJSONObject(i);
                DBUtil.dbItemExec.execSQL(sqlInsert, DBUtil.GetSqliteParam(node, lstParam));
            }
            DBUtil.dbItemExec.setTransactionSuccessful();
        }catch (Exception e) {
            this.setToast(e.getMessage());
        }finally {
            DBUtil.dbItemExec.endTransaction();
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
            setToast("异常："+e);
        }

    }

    @Override
    public void resRequestData(String tag, String msg, JSONObject response) {
        try {
            switch (tag) {
                case ApiConstants.ACTION_POSINFO_SELECTBYSN:{
                    TSysPosStatus data = (TSysPosStatus) JsonUtils.JsonStrToClass (response.getString("data"), TSysPosStatus.class);


//                    GSale.PosId = data.getPosid();
//                    GSale.StoreNo = data.getHostip();//.substring(0,5);

                    GSale.StoreNo = data.getHostip();
//                    GSale.StoreName = data.getBranchName();
                    GSale.PosId = data.getPosid();
                    GSale.StoreName = data.getBranch_name();
                    //验证密码
                    presenter.doLogin(btnLogin, txtLoginLogin.getText().toString(), txtLoginPass.getText().toString(), GSale.StoreNo);
                    break;
                }
                case ApiConstants.LOGIN_ACTION:{
                    TSysOperator data = (TSysOperator) JsonUtils.JsonStrToClass (response.getString("data"), TSysOperator.class);
                    UserUtil.saveLogin(mContext, data);
                    GSale.CashierNo = data.getOper_id();
                    GSale.CashierName = data.getOper_name();

                    Intent intent = new Intent(this, PosActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
               /* case ApiConstants.BRANCH_LIST: {
                    //JSONObject data = JSONObject. JSONObject.  .parseObject(responseData);
//                    com.alibaba.fastjson.JSONArray data = com.alibaba.fastjson.JSONArray.parseArray( responseData);
                    /*String data = response.getString("data");
                    List<BranchInfoVo> list = JSONArray.parseArray(data, BranchInfoVo.class);
                    _listBranch.clear();
                    _listBranch.addAll(list);
                    _adapterBranch.notifyDataSetChanged();* /
                    com.alibaba.fastjson.JSONArray data = com.alibaba.fastjson.JSONArray.parseArray( response.getString("data") );
                    new DownloadDal().insertBranchInfo(data);
                    loadBranchData();
                    break;
                }*/
            }
//            isRefresh = false;
//            mSwipeLayout.setRefreshing(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    String _rawdata;
    private void wxPayFaceInit(){
        /**
         *	初始化
         *
         */
        Map<String, String> m1 = new HashMap<>();
//      m1.put("ip", "192.168.1.1"); //若没有代理,则不需要此行
//      m1.put("port", "8888");//若没有代理,则不需要此行
//      m1.put("user", mEtnUser.getText().toString());//若没有代理,则不需要此行
//      m1.put("passwd", mEtnPassword.getText().toString());//若没有代理,则不需要此行
//      m1.put("proxy_type", 1 ); //若没有代理,则不需要此行
//      m1.put("perform_mode", "LOW_PERFORM");//低性能表现，默认关闭美颜等
        WxPayFace.getInstance().initWxpayface(this, m1, new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {

                if (info == null) {

                    setToast("调用返回为空, 请查看日志");
                    new RuntimeException("调用返回为空").printStackTrace();
                    return;

                }
                String code = (String) info.get("return_code");
                String msg = (String) info.get("return_msg");
                setToast("初始化完成");

            }
        });
    }

    private void getWxpayfaceRawdata(){
        /**
         *	获取rawdata
         *
         */
        WxPayFace.getInstance().getWxpayfaceRawdata(new IWxPayfaceCallback() {
            @Override
            public void response(final Map info) throws RemoteException {

                if (info == null) {

                    setToast("调用返回为空, 请查看日志");
                    new RuntimeException("调用返回为空").printStackTrace();
                    return;

                }
                String code = (String) info.get("return_code");
                String msg = (String) info.get("return_msg");
                _rawdata = (String) info.get("rawdata");
                setToast(_rawdata);
            }
        });
    }

    private void get_wxpayface_authinfo(){
        String apiUrl = "https://payapp.weixin.qq.com/face/get_wxpayface_authinfo";

        Map<String, String> m1 = new HashMap<>();
        m1.put("store_id","001011");
        m1.put("store_name","001011test测试");
        m1.put("device_id",AppParam.PhoneSn);
        m1.put("rawdata",_rawdata);
        m1.put("appid","00193801");
        m1.put("mch_id","56142605311C64M");
        m1.put("now", String.valueOf(System.currentTimeMillis()));
        m1.put("version","1");
        m1.put("sign_type","MD5");
        m1.put("nonce_str",String.valueOf(System.currentTimeMillis()));
        m1.put("sign","");
        WxPayFace.getInstance().getWxpayAuth(m1, new IWxPayfaceCallback() {
            @Override
            public void response(Map map) throws RemoteException {
                System.out.println(map.toString());
            }
        });
    }

}
