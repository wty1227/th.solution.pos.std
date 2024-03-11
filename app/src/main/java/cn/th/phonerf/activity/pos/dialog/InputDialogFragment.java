package cn.th.phonerf.activity.pos.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.pos.other.NoticeDialogListener;


public class InputDialogFragment extends DialogFragment implements View.OnClickListener {

    TextView lbTitle;
    TextView txtInputObject;
    TextView btnNum0;
    TextView btnNum1;
    TextView btnNum2;
    TextView btnNum3;
    TextView btnNum4;
    TextView btnNum5;
    TextView btnNum6;
    TextView btnNum7;
    TextView btnNum8;
    TextView btnNum9;
    TextView btnNumBackspace;
    TextView btnNumClear;
    TextView btnNumEnter;
    TextView btnNumCancel;
    TextView lbMsg;
    int _inputFlag = 0;
    String _title = "输入条码";
/*
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String object);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
*/
    NoticeDialogListener listener;


    @SuppressLint("ValidFragment")
    public InputDialogFragment(){

    }
    @SuppressLint("ValidFragment")
    public InputDialogFragment(String title, int inputFlag){
        _title = title;
        _inputFlag = inputFlag;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface.
        try {
            // Instantiate the NoticeDialogListener so you can send events to
            // the host.
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface. Throw exception.
            /*throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");*/
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so you can send events to
            // the host.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                if (activity instanceof NoticeDialogListener) {
                    listener = (NoticeDialogListener) activity;
                } else {
                    throw new RuntimeException(activity.toString()
                            + " must implement ABC_Listener");
                }
            }
        } catch (Exception e) {
            // The activity doesn't implement the interface. Throw exception.
            /*throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");*/
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater.
        LayoutInflater inflater = getActivity().getLayoutInflater();

        LinearLayout view = (LinearLayout)inflater.inflate(R.layout.dialog_input_barcode, null);


        /*TextView*/ lbTitle = view.findViewById(R.id.lbTitle);
        /*TextView*/ txtInputObject = view.findViewById(R.id.txtInputObject);
        /*TextView*/ btnNum0 = view.findViewById(R.id.btnNum0);
        /*TextView*/ btnNum1 = view.findViewById(R.id.btnNum1);
        /*TextView*/ btnNum2 = view.findViewById(R.id.btnNum2);
        /*TextView*/ btnNum3 = view.findViewById(R.id.btnNum3);
        /*TextView*/ btnNum4 = view.findViewById(R.id.btnNum4);
        /*TextView*/ btnNum5 = view.findViewById(R.id.btnNum5);
        /*TextView*/ btnNum6 = view.findViewById(R.id.btnNum6);
        /*TextView*/ btnNum7 = view.findViewById(R.id.btnNum7);
        /*TextView*/ btnNum8 = view.findViewById(R.id.btnNum8);
        /*TextView*/ btnNum9 = view.findViewById(R.id.btnNum9);
        /*TextView*/ btnNumBackspace = view.findViewById(R.id.btnNumBackspace);
        /*TextView*/ btnNumClear = view.findViewById(R.id.btnNumClear);
        /*TextView*/ btnNumEnter = view.findViewById(R.id.btnNumEnter);
        btnNumCancel = view.findViewById(R.id.btnNumCancel);
        lbMsg = view.findViewById(R.id.lbMsg);
        btnNum0.setOnClickListener(this);
        btnNum1.setOnClickListener(this);
        btnNum2.setOnClickListener(this);
        btnNum3.setOnClickListener(this);
        btnNum4.setOnClickListener(this);
        btnNum5.setOnClickListener(this);
        btnNum6.setOnClickListener(this);
        btnNum7.setOnClickListener(this);
        btnNum8.setOnClickListener(this);
        btnNum9.setOnClickListener(this);
        btnNumBackspace.setOnClickListener(this);
        btnNumClear.setOnClickListener(this);
        btnNumEnter.setOnClickListener(this);
        btnNumCancel.setOnClickListener(this);

        lbTitle.setText(_title);
        txtInputObject.setHint("请" + lbTitle.getText() );
        // Inflate and set the layout for the dialog.
        // Pass null as the parent view because it's going in the dialog layout.
        /*builder.setView(inflater.inflate(R.layout.dialog_input_barcode, null))
                // Add action buttons
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Sign in the user.
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //LoginDialogFragment.this.getDialog().cancel();
                        dismiss();
                    }
                });*/
        builder.setView(view).setCancelable(false) ;
        return builder.create();
    }

    public void setTitle(String title, int inputFlag){
        lbTitle.setText(title);
        this._inputFlag = inputFlag;
    }



    private void btnNumClick(View v){
        try {
            String str = txtInputObject.getText().toString();
            TextView sender = (TextView)v;
            String num = (String) sender.getTag();

            if(num.equals("clear")){
                txtInputObject.setText("");
                return;
            }
            if(num.equals("backspace")){
                txtInputObject.setText(str.substring(0, str.length() == 0 ? 0 : str.length()-1));
                return;
            }
            /*if (lbTitle.getText().equals("退货单")){

            }else {
                //5位宽度
                if(str.length() >= 13)
                    return;
            }*/
            if(str.length() >= 13)
                return;

            if(num.equals(".")){
                if(str.indexOf(".") != -1 ){

                }
                else
                    txtInputObject.setText(str+num);
            }
            else {
                int dotIndex = str.indexOf(".");
                if(dotIndex != -1 && str.length() > dotIndex + 2 ){

                }else
                    txtInputObject.setText(str + num);
            }
        }catch (Exception ex){
            //setToast("异常:" + ex.getMessage());
            lbMsg.setText("异常:" + ex.getMessage());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNumEnter:{
                dismiss();
                if(listener != null)
                listener.onDialogPositiveClick(this, txtInputObject.getText().toString(), _inputFlag);
                break;
            }
            case R.id.btnNumCancel:{
                dismiss();
                if(listener != null)
                listener.onDialogNegativeClick(this);
                break;
            }
            default:{
                btnNumClick(v);
                break;
            }
        }
    }
}
