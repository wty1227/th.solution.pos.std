package cn.th.phonerf.activity.pos.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.pos.other.AdapterItemList;
import cn.th.phonerf.activity.pos.other.GridItemDecoration;
import cn.th.phonerf.activity.pos.other.NoticeDialogListener;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.t_bd_item_infoDal;
import cn.th.phonerf.model.TBdItemInfo;
import cn.th.phonerf.model.TRmSaleflow;

public class ShoppingBagDialogFragment  extends DialogFragment implements View.OnClickListener{

    NoticeDialogListener listener;
    private Context mContext;
    TextView btnNumCancel;
    RecyclerView plItem;
    AdapterItemList _adapterItemList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface.
        try {
            // Instantiate the NoticeDialogListener so you can send events to
            // the host.
            if (context instanceof NoticeDialogListener)
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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();
//        return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater.
        LayoutInflater inflater = getActivity().getLayoutInflater();

        LinearLayout view = (LinearLayout)inflater.inflate(R.layout.dialog_shopping_bag, null);
        btnNumCancel = view.findViewById(R.id.btnNumCancel);
        btnNumCancel.setOnClickListener(this);
        plItem = (RecyclerView)view.findViewById(R.id.plItem);

        GridLayoutManager manager = new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false);
        plItem.setLayoutManager(manager);
        plItem.addItemDecoration(new GridItemDecoration(mContext));


        List<TBdItemInfo> lists3 = new t_bd_item_infoDal().Select();
        _adapterItemList = new AdapterItemList(mContext, R.layout.ls_item_list, lists3);
        plItem.setAdapter(_adapterItemList);

        _adapterItemList.setOnItemClickEvent(new AdapterItemList.onItemClickEvent() {
            @Override
            public void onClick(TBdItemInfo entity) {
                dismiss();
                listener.onDialogPositiveClick(ShoppingBagDialogFragment.this, entity.getItemNo(), 0);
            }
        });
        builder.setView(view).setCancelable(false) ;
        return builder.create();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNumCancel:{
                dismiss();
                listener.onDialogNegativeClick(this);
                break;
            }
            default:break;
        }
    }
}
