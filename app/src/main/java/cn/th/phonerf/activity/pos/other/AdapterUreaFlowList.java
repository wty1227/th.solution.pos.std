package cn.th.phonerf.activity.pos.other;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.model.TRmPayflowUrea;

public class AdapterUreaFlowList extends ArrayAdapter<TRmPayflowUrea> {

    private int defSelection = -1;
    private int resId;
    public AdapterUreaFlowList(@android.support.annotation.NonNull Context context, int resource, @android.support.annotation.NonNull List<TRmPayflowUrea> objects) {
        super(context, resource, objects);
        this.resId = resource;
    }

    @android.support.annotation.NonNull
    @Override
    public View getView(final int position, @android.support.annotation.Nullable View convertView, @android.support.annotation.NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        final TRmPayflowUrea item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resId, null);
        if(position == defSelection)
            view.setBackgroundColor(Color.YELLOW);
        else
            view.setBackgroundColor(Color.TRANSPARENT);
        TextView lbFlowId = (TextView)view.findViewById(R.id.lbFlowId);
        TextView lbItemNo = (TextView)view.findViewById(R.id.lbItemNo);
        TextView lbQty = (TextView)view.findViewById(R.id.lbQty);
        TextView lbSalePrice = (TextView)view.findViewById(R.id.lbSalePrice);
        TextView lbSubAmt = (TextView)view.findViewById(R.id.lbSubAmt);
        TextView lbItemName = (TextView)view.findViewById(R.id.lbItemName);
        TextView lbVipName = (TextView)view.findViewById(R.id.lbVipName);
        TextView lbDeviceNo = (TextView)view.findViewById(R.id.lbDeviceNo);
        TextView lbValidSubAmt = (TextView)view.findViewById(R.id.lbValidSubAmt);

        item.setFlowId(position + 1);
        lbFlowId.setText(String.valueOf(position + 1));
        lbItemNo.setText(item.getTime().trim());
        lbItemName.setText(item.getPushId().trim());
        lbVipName.setText(item.getCardName());
        /*String deviceNo = "";
        switch(item.getDevice().trim()){
            case "863412042130032":
                deviceNo = "1号机";
                break;
            case "868704045919774":
                deviceNo = "2号机";
                break;
            case "868704045929534":
                deviceNo = "3号机";
                break;
            case "863412042129984":
                deviceNo = "4号机";
                break;
        }*/
        lbDeviceNo.setText(item.getDevNum().trim() + "号机");

        lbQty.setText(item.getLiter().toString().trim());
        lbSalePrice.setText(item.getPrice().toString().trim());
        lbSubAmt.setText(item.getSale().toString().trim());

        if(item.getFlag() == 1){
            lbValidSubAmt.setText(item.getSubAmt().toString());
        }else {
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
            lbValidSubAmt.setText(df.format(item.getSale() * GSale.Discount));
        }

        if(item.getCardType() != null && ( item.getCardType().startsWith("A") || item.getCardType().startsWith("B") || item.getCardType().startsWith("C") ) && (item.getCardName() != null && !item.getCardName().startsWith("55555") )){
            view.setBackgroundColor(Color.parseColor("#6600A9DF") );
        }
        if(mOnItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(item, v, position);
                }
            });
        }
        return view;
    }

    public interface onItemClickEvent{
        void onClick(TRmPayflowUrea entity, View view, int position);
    }


    private onItemClickEvent mOnItemClickListener;

    public void setOnItemClickEvent(onItemClickEvent mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public void setSelectPosition(int position) {
        if (!(position < 0 || position > this.getCount())) {
            defSelection = position;
            notifyDataSetChanged();
        }
    }

}

