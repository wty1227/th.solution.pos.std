package cn.th.phonerf.activity.systemsetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.constant.SetUpState;
import cn.th.phonerf.model.TRmPayflow;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.utils.SharePreferenceUtils;

public class SystemSetupActivity extends BaseActivity {

    private TextView pageTitleText, rightText;
    private ImageView backText;
    private HashMap Data = new HashMap();
    private JSONObject object;


    private TextView lbMsg;
    private RecyclerView mRecyclerViewSaleFlow;
    private RecyclerView mRecyclerViewPayFlow;
    private List<TRmSaleflow> tRmSaleflow;
    private List<TRmPayflow> tRmPayflows;
    private Button btnOk;
    private EditText txtDiscount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setup);


        initUI();

        try{

            CheckBox printing = findViewById(R.id.printing);
            CheckBox tips = findViewById(R.id.tips);
            SharedPreferences settings = SystemSetupActivity.this.getSharedPreferences("SETUP", Context.MODE_PRIVATE);
            String setup = settings.getString("setup","0");
            object = new JSONObject(setup);
            if (setup.equals("0")){
                Data.put("printing","1");
                Data.put("tips","0");
                setUpShared(1,"setup",Data);
            }else {
                if (object.getString("printing").equals("1")){
                    printing.setChecked(true);
                }
                if (object.getString("tips").equals("1")){
                    tips.setChecked(true);
                }
                SetUpState.printingState = object.getString("printing");
                SetUpState.tipsState = object.getString("tips");
            }

            printing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    if(b){
                        Data.put("printing","1");
                        Data.put("tips",object.getString("tips"));
                        setUpShared(1,"setup",Data);
                        SetUpState.printingState = "1";
                    }else{
                        Data.put("printing","0");
                        Data.put("tips",object.getString("tips"));
                        setUpShared(1,"setup",Data);
                        SetUpState.printingState = "0";
                    }
                }catch (Exception e){

                }
                }
            });

            tips.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    try {
                        if(b){
                            Data.put("printing",object.getString("printing"));
                            Data.put("tips","1");
                            setUpShared(1,"setup",Data);
                            SetUpState.tipsState = "1";
                        }else{
                            Data.put("printing",object.getString("printing"));
                            Data.put("tips","0");
                            setUpShared(1,"setup",Data);
                            SetUpState.tipsState = "0";
                        }
                    }catch (Exception e){

                    }
                }
            });
        }catch (Exception e){
            Log.d("err:",e+"");
            Data.put("printing","1");
            Data.put("tips","0");
            setUpShared(1,"setup",Data);
        }



    }

    private void setUpShared(int type, String key, HashMap data){
        //

        if (type == 1){
            GsonBuilder gsonbuilder = new GsonBuilder();
            gsonbuilder.serializeNulls();
            Gson gson = gsonbuilder.create();
            String userJson = gson.toJson(data);
            //存储token
            SharedPreferences settings = getSharedPreferences("SETUP", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(key,userJson);
            editor.commit();
        }
    }

    private void initUI() {
        try{
            //顶部
            pageTitleText = (TextView) findViewById(R.id.top_title_text);
            pageTitleText.setText("设置");
            backText = (ImageView) findViewById(R.id.top_back_image);
            backText.setVisibility(View.VISIBLE);
            backText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            rightText = (TextView) findViewById(R.id.top_right_text);
            rightText.setText("设置");
            rightText.setVisibility(View.INVISIBLE);
            //rightText.setOnClickListener(this);
            lbMsg = (TextView)findViewById(R.id.lbMsg);
            txtDiscount = findViewById(R.id.txtDiscount);

            txtDiscount.setText( SharePreferenceUtils.getValue(mContext, "total_amount", "1") );

            btnOk = findViewById(R.id.btnOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double discount = Double.valueOf(txtDiscount.getText().toString());
                    if(discount > 1){
                        setToast("折扣不能大于11");
                    }

                    SharePreferenceUtils.setValue(mContext, "total_amount", txtDiscount.getText().toString());
                    GSale.Discount = discount;
                    setToast("保存成功！");
                }
            });
        }catch (Exception e){
            setToast("异常："+e);
        }

    }

}
