package cn.th.phonerf.activity.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.main.other.AdapterBtAddr;
import cn.th.phonerf.activity.main.present.IMainPresenter;
import cn.th.phonerf.activity.main.present.MainPresenterImpl;
import cn.th.phonerf.activity.main.view.IMainView;
import cn.th.phonerf.activity.query.QueryActivity;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.SystemDal;
import cn.th.phonerf.dal.t_bd_item_infoDal;
import cn.th.phonerf.model.BaseEntity;
import cn.th.phonerf.model.MerchantShop;
import cn.th.phonerf.model.SysSystem;
import cn.th.phonerf.utils.BluetoothUtil;
import cn.th.phonerf.utils.ProgressUtils;
import cn.th.phonerf.utils.SharePreferenceUtils;
import cn.th.phonerf.utils.TextUtil;

public class MainActivity extends BaseActivity implements IMainView, View.OnClickListener {

    private Context mContext;
    private Dialog loadingDlg;
    private IMainPresenter presenter;

    private TextView pageTitleText, rightText;
    private ImageView backText;
    private RelativeLayout errLayout;
    private TextView errText, errBtn;

    private TextView lbBranchNo;
    private TextView lbOperId;
    private TextView lbBtAddr;
    private TextView lbCommPort;

    private TextView btnQuery;
    private TextView btnPrint;
    private TextView btnStocktake;
    private TextView btnBtSetup;
    private TextView btnModifyPass;
    private TextView btnDownload;


    private ProgressDialog pBar;
    private Integer _downCount = 100;
    private String[] condition;

    private int _downIndex = 0;


    private ArrayList<BaseEntity> _listBtAddr = new ArrayList<>();
    private String downItemSql = "";
    private String downBarcodeSql = "";
    private String downCountSql = "";

    private MerchantShop _merchantShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), false);

        presenter = new MainPresenterImpl(this);
        initUI();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setToast("12321312321");
    }



    /**
     * 初始化UI
     */
    private void initUI() {
        //顶部
        pageTitleText = (TextView) findViewById(R.id.top_title_text);
        pageTitleText.setText("功能选择");
        backText = (ImageView) findViewById(R.id.top_back_image);
        backText.setVisibility(View.VISIBLE);
        backText.setOnClickListener(this);
        rightText = (TextView) findViewById(R.id.top_right_text);
        rightText.setText("历史");
        rightText.setVisibility(View.INVISIBLE);
        rightText.setOnClickListener(this);

        btnQuery = (TextView) findViewById(R.id.main_btnQuery);
        btnPrint = (TextView) findViewById(R.id.main_btnPrint);

        btnStocktake = (TextView) findViewById(R.id.main_btnStocktake);

        btnBtSetup = (TextView) findViewById(R.id.main_btnBtSetup);
        btnModifyPass = (TextView) findViewById(R.id.main_btnModifyPass);
        btnDownload = (TextView) findViewById(R.id.main_btnDownload);


        btnModifyPass.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
        btnStocktake.setOnClickListener(this);
        btnBtSetup.setOnClickListener(this);

        lbBranchNo = (TextView) findViewById(R.id.main_lbBranchNo);
        lbOperId = (TextView) findViewById(R.id.main_lbOperId);
        lbBtAddr = (TextView) findViewById(R.id.main_lbBtAddr);
        lbCommPort = (TextView) findViewById(R.id.main_lbCommPort);

        lbBranchNo.setText(GSale.StoreNo + " " + GSale.StoreName);
        lbOperId.setText(GSale.CashierNo + " " + GSale.CashierName);
        lbBtAddr.setText("蓝牙地址： " + AppParam.BtAddr);

        /*if(GOper.Grants.size() < 16 || GOper.Grants.get(15).finish.equals("0"))
        {
            MsgBox.Show(this,"没有权限！请向管理员申请。");
            return;
        }*/

        if(AppParam.Grants.size() >= 5 ) {
            if (AppParam.Grants.get(0).getGrant0().equals("0"))
                btnQuery.setEnabled(false);
            if (AppParam.Grants.get(1).getGrant0().equals("0"))
                btnPrint.setEnabled(false);
            if (AppParam.Grants.get(2).getGrant0().equals("0"))
                btnStocktake.setEnabled(false);
            if (AppParam.Grants.get(3).getGrant0().equals("0"))
                btnBtSetup.setEnabled(false);
            if (AppParam.Grants.get(4).getGrant0().equals("0"))
                btnDownload.setEnabled(false);
        }

        getLocalItemCount();
    }

    private void loadData() {
        try {
            SystemDal dal = new SystemDal();
            SysSystem entity = dal.selectById(AppParam.MerchantId, "down_item_sql");
            this.downItemSql = entity.getSysVarValue();

            entity = dal.selectById(AppParam.MerchantId, "down_barcode_sql");
            if(entity != null)
                this.downBarcodeSql = entity.getSysVarValue();

            entity = dal.selectById(AppParam.MerchantId, "down_count_sql");
            this.downCountSql = entity.getSysVarValue();

            String str = "";
            try{
                entity = dal.selectById(AppParam.MerchantId, "down_condition");
                str = entity.getSysVarValue();
            }catch (Exception e){
            }

            //this.downCountSql = entity.getSysVarValue();
            //String str = entity.getSysVarValue();
            if(TextUtil.isEmpty( str ))
                str = "%";
            this.condition = str.split(",");
            //getDownloadCount();
        } catch (Exception e) {
            this.setToast(e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back_image:
                finish();
                break;
            case R.id.main_btnQuery: {
                Intent intent = new Intent(MainActivity.this, QueryActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.main_btnPrint: {
                try {
                    //Intent intent = new Intent(MainActivity.this, SelReceiveActivity.class);
                    //startActivity(intent);
                }catch (Exception e){
                    setToast("异常:" + e.getMessage());
                }

                break;
            }
            case R.id.main_btnStocktake: {

                break;
            }
            case R.id.main_btnBtSetup: {
                setBtAddr();
                break;
            }
            case R.id.main_btnModifyPass:{
                modifyPass();
                break;
            }
            case R.id.main_btnDownload: {
                getDownloadCount();
                //downloadItemInfo();
                break;
            }
        }
    }

    private EditText txtOldPass;
    private EditText txtNewPass;
    private EditText txtNewPass2;
    private void modifyPass(){
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        View view =(LinearLayout)getLayoutInflater().inflate(R.layout.dlg_password, null);

        //btnModifyPass = (Button) findViewById(R.id.xbtnModifyPass);
        txtOldPass = (EditText)view.findViewById(R.id.xtxtOldPass);
        txtNewPass = (EditText)view.findViewById(R.id.xtxtNewPass);
        txtNewPass2 = (EditText)view.findViewById(R.id.xtxtNewPass2);

        builder.setView(view);
        builder.setTitle("修改密码");
        //builder.setCancelable(false);

        //final Double finalConsumAmt = consumAmt;
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final String oldPass = txtOldPass.getText().toString();
                final String newPass = txtNewPass.getText().toString();
                String newPass2 = txtNewPass2.getText().toString();
                if(!newPass.equals(newPass2)){
                    setToast("两次输入的密码不一致!");
                    return;
                }
                presenter.doModifyPass(btnModifyPass, oldPass, newPass, GSale.CashierNo, AppParam.MerchantId);


            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        final android.app.AlertDialog dlg = builder.create();
        dlg.show();
    }

    private void setBtAddr(){
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        View view =(LinearLayout)getLayoutInflater().inflate(R.layout.dlg_bt_addr, null);
        ListView lsBtaddr = (ListView)view.findViewById(R.id.lsBtAddr);

        this._listBtAddr = BluetoothUtil.getApapterList();
        AdapterBtAddr adapter = new AdapterBtAddr(MainActivity.this, R.layout.stockrpt_list, _listBtAddr);
        lsBtaddr.setAdapter(adapter);

        builder.setView(view);
        builder.setTitle("请选择");
        //builder.setCancelable(false);

        //final Double finalConsumAmt = consumAmt;
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();


            }
        });
        final android.app.AlertDialog dlg = builder.create();
        dlg.show();

        lsBtaddr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppParam.BtAddr = ((BaseEntity)(_listBtAddr.get(position))).getKey();

                BluetoothUtil.Innerprinter_Address = AppParam.BtAddr;
                /*SharedPreferences share = getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit(); //编辑文件
                edit.putString("btaddr", AppParam.BtAddr);
                edit.commit();  //保存数据信息
                */
                SharePreferenceUtils.setValue(MainActivity.this, SharePreferenceUtils.BT_ADDR, AppParam.BtAddr);
                lbBtAddr.setText("蓝牙地址： " + AppParam.BtAddr);
                dlg.dismiss();
                setToast("选择成功");

            }
        });

    }

    private void getLocalItemCount(){
        //本地数据
        int numCount = 0;
        t_bd_item_infoDal dalItem = new t_bd_item_infoDal();
        try {
            numCount = dalItem.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lbCommPort.setText(" 本地商品总数：" + numCount);
    }

    private void getDownloadCount() {
        presenter.doGetDownloadCount(backText, downCountSql, GSale.StoreNo, GSale.CashierNo, AppParam.MShop);
    }

    /*private void getItemCount(){
        presenter.doGetItemCount(backText, downCountSql, GSale.StoreNo, GSale.CashierNo);
    }*/

    @Override
    public void resGetItemCount(String data) {
        this._downCount = Integer.valueOf(data);
    }

    @Override
    public void resGetDownloadCount(String data) {
        this._downCount = Integer.valueOf(data);

        //下载之前先清空
        t_bd_item_infoDal dal = new t_bd_item_infoDal();
        dal.deleteList();
        //开始下载数据
        if(this.downBarcodeSql.isEmpty())
            downloadItemInfo();
        else {
            String sql = this.downBarcodeSql
                    .replace("{0}", GSale.StoreNo)
                    .replace("{1}", this.condition[_downIndex])
                    .replace("{门店编码}", GSale.StoreNo)
                    .replace("{商品条码}", this.condition[_downIndex])
                    .replace("商品条码", this.condition[_downIndex]);
            presenter.doDownloadBarcode(btnDownload, sql, GSale.StoreNo, GSale.CashierNo, AppParam.MShop);
        }
    }


    @Override
    public void resDownloadBarcode(JSONArray data) {
        if (data.size() == 0) {
            //throw new Exception("系统中无此商品信息");
            setToast("请等待下载完成...");
        }else {
            //获取第一个JSONObject
            //写入数据库
            t_bd_item_infoDal dal = new t_bd_item_infoDal();
            try {
                dal.insertBarcode(data);
                //开始下载数据
                downloadItemInfo();
            }catch (Exception e){
                this.setToast("异常：" + e.getMessage());
            }
        }

    }


    //private Timer mTimer = null;
    //PowerManager pManager = null;
    //PowerManager.WakeLock mWakeLock = null;
    final int process[] = {0};
    @SuppressLint("InvalidWakeLockTag")
    private void downloadItemInfo() {
        if (_downCount == 100)
            _downCount = 58000;
        //下载数据
        /*
        try {
            for (int m = 0; m < condition.length; m++) {
                String sql = this.downItemSql.replace("{0}", GSale.StoreNo).replace("{1}", this.condition[m]);
                presenter.doDownloadItem(btnDownload, sql, GSale.StoreNo, GSale.CashierNo, AppParam.MShop);
            }
        } catch (final Exception e) {
            this.setToast("异常：" + e.getMessage());
        } finally {
        }*/
        //禁止休眠

        //开始下载
        this._downIndex = 0;
        String sql = this.downItemSql
                .replace("{0}", GSale.StoreNo)
                .replace("{1}", this.condition[_downIndex])
                .replace("{门店编码}", GSale.StoreNo)
                .replace("{商品条码}", this.condition[_downIndex])
                .replace("商品条码", this.condition[_downIndex]);
        presenter.doDownloadItem(btnDownload, sql, GSale.StoreNo, GSale.CashierNo, AppParam.MShop);
    }

    @Override
    public void resDownloadItem(JSONArray data) {
        if (data.size() == 0) {
            //throw new Exception("系统中无此商品信息");
            setToast("请等待下载完成...");
        }else {
            //获取第一个JSONObject
            //写入数据库
            t_bd_item_infoDal dal = new t_bd_item_infoDal();
            try {
                dal.insertList(data);
                process[0] += data.size();
            }catch (Exception e){
                this.setToast("异常：" + e.getMessage());
            }
        }
        this._downIndex++;
        if(this._downIndex < this.condition.length) {
            String sql = this.downItemSql
                    .replace("{0}", GSale.StoreNo)
                    .replace("{1}", this.condition[_downIndex])
                    .replace("{门店编码}", GSale.StoreNo)
                    .replace("{商品条码}", this.condition[_downIndex])
                    .replace("商品条码", this.condition[_downIndex]);;
            presenter.doDownloadItem(btnDownload, sql, GSale.StoreNo, GSale.CashierNo, AppParam.MShop);
        }else {
            setToast("下载完成! 开始刷新本地数");
            getLocalItemCount();
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
