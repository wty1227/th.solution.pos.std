package cn.th.phonerf.activity.query;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.CommonScanActivity;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.query.other.DisplayItemAdapter;
import cn.th.phonerf.activity.query.present.QueryPresenterImpl;
import cn.th.phonerf.activity.query.present.IQueryPresenter;
import cn.th.phonerf.activity.query.view.IQueryView;
import cn.th.phonerf.activity.zxing.utils.Constant;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.PrintDesignDal;
import cn.th.phonerf.dal.SystemDal;
import cn.th.phonerf.dal.t_bd_item_infoDal;
import cn.th.phonerf.model.BaseEntity;
import cn.th.phonerf.model.PrintDesignDetail;
import cn.th.phonerf.model.PrintDesignMaster;
import cn.th.phonerf.model.SysSystem;
import cn.th.phonerf.model.t_bd_item_info;
import cn.th.phonerf.utils.BluetoothUtil;
import cn.th.phonerf.utils.PlayerVoiceUtil;
import cn.th.phonerf.utils.ProgressUtils;
import cn.th.phonerf.utils.device.HmUtil;

public class QueryActivity extends BaseActivity implements IQueryView, View.OnClickListener {

    private Context mContext;
    private Dialog loadingDlg;
    private IQueryPresenter presenter;

    private TextView pageTitleText, rightText;
    private ImageView backText;
    private RelativeLayout errLayout;
    private TextView errText, errBtn;

    private ListView lvInfo;
    private EditText txtQuery;
    private Button btnQuery;
    private TextView txtName;
    private CheckBox ckOffline;
    private CheckBox ckAutoPrn;
    private DisplayItemAdapter _adapter;

    private t_bd_item_info item = new t_bd_item_info();
    private t_bd_item_infoDal dal = new t_bd_item_infoDal();
    private Boolean _isBusy = false;
    //private PRINT_FLAG _prnFlag = PRINT_FLAG.STOCK_LABEL_JQ;
    //标签
    private TextView btnDownLabel;
    //默认标签ID
    private Integer _printId;
    private ArrayList<PrintDesignDetail> _detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabPriceLabel);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectBT();

            }

        });

        mContext = this;
        loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);

        initUI();
        presenter = new QueryPresenterImpl(this);
        loadData();
        //读取打印设置
        //readPrintDesign();
    }
    /**
     * 初始化UI
     */
    private void initUI() {
        //顶部
        /*pageTitleText = (TextView) findViewById(R.id.top_title_text);
        pageTitleText.setText("消费");
        backText = (ImageView) findViewById(R.id.top_back_image);
        backText.setVisibility(View.INVISIBLE);
        //backText.setOnClickListener(this);
        rightText = (TextView) findViewById(R.id.top_right_text);
        rightText.setText("历史");
        rightText.setVisibility(View.INVISIBLE);
        rightText.setOnClickListener(this);*/
        txtName = (TextView) findViewById(R.id.txtName);
        txtQuery = (EditText) findViewById(R.id.query_txtQuery);
        btnQuery = (Button) findViewById(R.id.query_btnQuery);
        lvInfo = (ListView) findViewById(R.id.lvInfo);
        //ckOffline = (CheckBox) findViewById(R.id.ckOffline);
        ckAutoPrn = (CheckBox) findViewById(R.id.ckAutoPrn);
        btnDownLabel = (TextView)findViewById(R.id.btnDownLabel);
        btnQuery.setOnClickListener(this);
        btnDownLabel.setOnClickListener(this);

        txtQuery.setSingleLine();
        txtQuery.setText("");
        txtName.setText("");
        //--txtQuery.requestFocus();
        txtQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ) {
                    // do something
                   /* if (ckOffline.isChecked())
                        QueryDataOffline();
                    else*/
                        QueryData();

                    return true;
                }else{
                    try {
                        if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                            QueryData();

                            return true;
                        }
                    }catch (Exception e){
                        QueryData();return true;
                    }

                }
                return false;
            }
        });

    }

    /**
     * 获取查询语句
     */
    private void loadData(){
        List<BaseEntity> lists = new ArrayList<>();
        this._adapter = new DisplayItemAdapter(QueryActivity.this, R.layout.display_item, lists);
        lvInfo.setAdapter(this._adapter);
        try {
            SystemDal dal = new SystemDal();
            SysSystem entity = dal.selectById(AppParam.MerchantId, "query_sql");
            AppParam.QuerySql = entity.getSysVarValue();

            //获取打印指令
        }catch (Exception e) {
            this.setToast(e.getMessage());
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
               // startActivity(intent);
                break;
            }
            case R.id.btnDownLabel:{
                presenter.doDownLabel(btnDownLabel, AppParam.MerchantId);
                break;
            }
            case R.id.query_btnQuery: {
                //onScanQrcode(null);
                try {
                    Intent intent = new Intent(this, CommonScanActivity.class);
                    intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
                    startActivityForResult(intent, 101);
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //MsgBox.Show(QueryInfoActivity.this, e.getMessage());
                            setToast(e.getMessage());
                        }
                    });
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && data != null) {
            txtQuery.setText(data.getStringExtra("barcode"));
            //if (ckOffline.isChecked())
              //  QueryDataOffline();
            //else
                QueryData();
        }
    }

    /**
     * 自动打印
     */
    private void autoPrn() {
        if (ckAutoPrn.isChecked()) {
            //ConnectBT(this._prnFlag);
        }
    }

    /**
     * 在线查询
     */
    private void QueryData() {
        try{
            this._adapter.clear();
            txtName.setText("");

            final String query = txtQuery.getText().toString();
            txtQuery.setText("");
            if (query.isEmpty())
                return;

            setToast("开始扫描：" + query);

            this.item = null;
            this.item = new t_bd_item_info();

            //String sql = String.format(AppParam.QuerySql, query, GSale.StoreNo);
            String sql = AppParam.QuerySql.replace("{0}", query).replace("{1}", GSale.StoreNo).replace("{门店编码}", GSale.StoreNo).replace("{商品条码}", query);;
            presenter.doQuery(btnQuery, sql, GSale.StoreNo, AppParam.MShop);
            //--txtQuery.requestFocus();
        }catch (Exception e){
            setToast(e.getMessage());
        }

    }

    /**
     * 离线查询
     */
    private void QueryDataOffline() {
        this._adapter.clear();

        txtName.setText("");

        final String query = txtQuery.getText().toString();
        txtQuery.setText("");
        if (query.isEmpty())
            return;
        setToast("开始扫描:" + query);
        this.item = null;
        this.item = new t_bd_item_info();
        //this.item.setItemNo(query);

        //GFunc.showProgressDlg(this);

        try {
            final t_bd_item_infoDal dal = new t_bd_item_infoDal();
            final ArrayList<t_bd_item_info> listsquery = dal.Select(query);
            if (listsquery.size() == 0) {
                setToast("未找到该商品");
                return;
            }
            List<BaseEntity> lists = new ArrayList<>();
            BaseEntity item = new BaseEntity();
            item.setKey("商品编码");
            item.setValue(listsquery.get(0).getItem_no());
            lists.add(item);

            item = new BaseEntity();
            item.setKey("商品条码");
            item.setValue(listsquery.get(0).getItem_subno());
            lists.add(item);

            item = new BaseEntity();
            item.setKey("商品品名");
            item.setValue(listsquery.get(0).getItem_name());
            lists.add(item);


            item = new BaseEntity();
            item.setKey("单位");
            item.setValue(listsquery.get(0).getUnit_no());
            lists.add(item);

            item = new BaseEntity();
            item.setKey("规格");
            item.setValue(listsquery.get(0).getItem_size());
            lists.add(item);

            item = new BaseEntity();
            item.setKey("件装数");
            item.setValue(listsquery.get(0).getPackage_spec());
            lists.add(item);
            this._adapter.addAll(lists);

            txtName.setText(listsquery.get(0).getItem_name());
            this.item = listsquery.get(0);

            autoPrn();

            txtQuery.setText("");
            //--txtQuery.requestFocus();


        } catch (final Exception e) {
            //e.printStackTrace();
            setToast(e.getMessage());
            //--txtQuery.requestFocus();
        }
    }

    private void displayItem(JSONObject json) {

        try {
            this.item.setItem_no(json.getString("item_no"));
            this.item.setItem_subno(json.getString("item_subno"));
            this.item.setItem_name(json.getString("item_name"));
            this.item.setUnit_no(json.getString("单位"));
            this.item.setItem_size(json.getString("规格"));
            this.item.setSale_price(json.getDouble("参考售价"));
            this.item.setPackage_spec(json.getString("件装数"));
            this.item.setProduce_area(json.getString("产地"));
            //this.item.setLevel(json.getString("等级"));
            //Log.e("QueryData", item.getItem_subno());
            List<BaseEntity> lists = new ArrayList<>();

            Map<String, Object> params = JSONObject.parseObject(json.toJSONString(), new TypeReference<Map<String, Object>>(){});
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                try {
                    BaseEntity item = new BaseEntity();
                    String key = entry.getKey();
                    String value = entry.getValue().toString();

                    if (key.equals("item_no") || key.equals("item_subno") || key.equals("item_name"))
                        continue;

                    item.setKey(key);
                    item.setValue(value);
                    //item.setDisplayMember(key);
                    //item.setValueMember(value);

                    lists.add(item);

                } catch (Exception e) {
                }
            }
            this._adapter.addAll(lists);
            autoPrn();
            //--txtQuery.requestFocus();
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
            //--txtQuery.requestFocus();
        }

        //System.out.println(params);


        /*org.json.JSONObject jsonObject = (org.json.JSONObject) JsonUtils.JsonStrToClass(String.valueOf(json), org.json.JSONObject.class);
        for (Iterator iter = jsonObject.keys(); iter.hasNext(); ) { //先遍历整个 people 对象

            try {
                BaseEntity item = new BaseEntity();
                String key = (String) iter.next();
                String value = json.getString(key);

                if (key.equals("item_no") || key.equals("item_subno") || key.equals("item_name"))
                    continue;

                item.setKey(key);
                item.setValue(value);

                lists.add(item);

            } catch (Exception e) {
            }
        }*/
        //org.json.JSONObject data =
        //JSONArray.parse(String.valueOf(json)) (JSONArray) JSONArray.parse(String.valueOf(json));//

        /*JSONArray jarr = JSONArray.parseArray(String.valueOf(json));
        for (Iterator iterator = jarr.iterator(); iterator.hasNext();) {
            JSONObject job = (JSONObject) iterator.next();
            //System.out.println(job.get("companyId").toString());
            try {
                BaseEntity item = new BaseEntity();
                String key = (String) iterator.next();
                String value = job.getString(key);

                if (key.equals("item_no") || key.equals("item_subno") || key.equals("item_name"))
                    continue;
                item.setKey(key);
                item.setValue(value);
                lists.add(item);
            } catch (Exception e) {
            }
        }*/

    }


    @Override
    public void resRequestData(com.alibaba.fastjson.JSONArray data) {

        setToast("查询完成，开始显示");

        //JSONArray jsonArray = new JSONArray(ds);
        if (data.size() == 0) {
            //throw new Exception("系统中无此商品信息");
            setToast("系统中无此商品信息");
        }else {
            //获取第一个JSONObject
            com.alibaba.fastjson.JSONObject node = data.getJSONObject(0);
            //展示信息
            displayItem(node);
            txtName.setText(item.getItem_name());
            txtQuery.setText("");
            //--txtQuery.requestFocus();
        }
    }

    @Override
    public void resDownLabel(List<PrintDesignMaster> list) {
        //List<PrintDesignMaster> list
        //写入
        try {
            new PrintDesignDal().insertInfo(list);
            setToast("下载完成");
            //读取
            readPrintDesign();
        }catch (Exception e){
            setToast("异常:" + e.getMessage());
        }

    }

    private void readPrintDesign(){


        try {
            //读取
            this._printId = 0;
            this._detail = null;
            PrintDesignDal dal = new PrintDesignDal();
            ArrayList<PrintDesignMaster> master = dal.selectMaster(AppParam.MerchantId);
            if(master.size() == 0)
                return;
            ArrayList<PrintDesignDetail> detail = dal.selectDetail( master.get(0).getPrintId());
            if(detail.size() == 0)
                return;
            this._printId = master.get(0).getPrintId();
            this._detail = detail;
        }catch (Exception e){
            setToast("异常:" + e.getMessage());
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

    private void connectBT() {
        try {
            PlayerVoiceUtil.playerVoice(this, R.raw.print);

            Thread.sleep(600);
            //判断状态
            if (BluetoothUtil.getStatus()) {
                BluetoothUtil.disconnectBlueTooth(this);
                //return;
            }

            //连接
            if (!BluetoothUtil.connectBlueTooth(QueryActivity.this)) {
                setToast(BluetoothUtil.RetMsg);
            } else {
                printPriceLabelHMN();
            }
        } catch (Exception e) {
            setToast("异常:" + e.getMessage());
            //btnWeight.setEnabled(true);
        }

    }

    private void printPriceLabelHMN() throws IOException {

        OutputStream OutStream = null;
        InputStream InStream = null;

        OutStream = BluetoothUtil.getOutputStream();
        InStream = BluetoothUtil.getInputStream();

        HmUtil util = new HmUtil(OutStream, InStream);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Date now = new Date();
        String date = sdf.format(now);

        //读取打印机sn号
        String sn = util.getSN();
        /*if(sn.isEmpty()) {
            setToast("无法读取打印机序列号!");
            return;
        }*/
        //设置页面大小
        /*
         ["0", "纸张大小"],
            ["1", "品名"],
            ["2", "规格"],
            ["3", "产地"],
            ["4", "零售价"],
            ["5", "单位"],
            ["6", "等级"],
            ["7", "打印日期"],
            ["8", "商品编码"],
            ["9", "条形码"],
            ["10", "商品条码"],
        */
        //util.setup(76, 280, 3, 8, 0, 2, 0);
        String printContext = "";
        //循环
        for (PrintDesignDetail ele:this._detail
             ) {
            switch (ele.getTitleId()){
                case 0: {
                    util.setup(ele.getX(), ele.getY(), 3, 8, 0, 2, 0);
                    break;
                }
                case 9: {
                    String barcode = this.item.getItem_subno();
                    int x= ele.getX();
                    int y = ele.getY();
                    boolean displayFlag = ele.getDisplayFlag().equals("1");
                    if(!displayFlag)
                        continue;
                    if (barcode.length() == 13)
                        util.barcode(x, y, "EAN13", 50, 1, 1, barcode.substring(0, 12));
                    else if (barcode.length() == 8)
                        util.barcode(x, y, "EAN8", 50, 1, 1, barcode.substring(0, 7));
                    else
                        util.barcode(x, y, "128", 50, 1, 1, barcode);
                    break;
                }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 8:
                case 10: {
                    int titleId = ele.getTitleId();
                    if(titleId == 1)
                        printContext = this.item.getItem_name();
                    else if(titleId == 2)
                        printContext = this.item.getItem_size();
                    else if(titleId == 3)
                        printContext = this.item.getProduce_area();
                    else if(titleId == 4)
                        printContext = String.valueOf(this.item.getSale_price());
                    else if(titleId == 5)
                        printContext = this.item.getUnit_no();
                    else if(titleId == 6)
                        printContext = this.item.getLevel();
                    else if(titleId == 7)
                        printContext = date;
                    else if(titleId == 8)
                        printContext = this.item.getItem_no();
                    else if(titleId == 10)
                        printContext = this.item.getItem_subno();

                    int font = ele.getFont();
                    int size = ele.getSize();
                    int x= ele.getX();
                    int y = ele.getY();
                    String rotation = ele.getRotation();
                    int xmulip = ele.getXmulip();
                    int ymulip = ele.getYmulip();
                    String titleName = ele.getTitleName();
                    boolean printTitle = ele.getPrintTitle().equals("1");
                    boolean boldFlag = ele.getBoldFlag().equals("1");
                    boolean displayFlag = ele.getDisplayFlag().equals("1");

                    if(!displayFlag)
                        continue;
                    if(!printTitle)
                        titleName = "";
                    if(boldFlag)
                        util.setBold("2");
                    else
                        util.setBold("0");
                    util.printerfont(font, size, x, y, rotation, xmulip, ymulip, titleName + " " + printContext);
                    break;
                }
            }
        }

        //开始打印
        util.printForm();
        util.printlabel();
    }
}
