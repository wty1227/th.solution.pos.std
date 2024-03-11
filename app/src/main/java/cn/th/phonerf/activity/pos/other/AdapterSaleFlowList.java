package cn.th.phonerf.activity.pos.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.model.TRmSaleflow;

public class AdapterSaleFlowList extends ArrayAdapter<TRmSaleflow> {

    private int defSelection = -1;
    private int resId;
    private List<TRmSaleflow> _list = null;
    public AdapterSaleFlowList(@android.support.annotation.NonNull Context context, int resource, @android.support.annotation.NonNull List<TRmSaleflow> objects) {
        super(context, resource, objects);
        this.resId = resource;
        _list = objects;
    }

    @android.support.annotation.NonNull
    @Override
    public View getView(final int position, @android.support.annotation.Nullable View convertView, @android.support.annotation.NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        final TRmSaleflow item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resId, null);
//        if(position == defSelection)
//            view.setBackgroundColor(Color.YELLOW);
//        else
//            view.setBackgroundColor(Color.TRANSPARENT);
        TextView lbFlowId = (TextView)view.findViewById(R.id.lbFlowId);
        TextView lbItemNo = (TextView)view.findViewById(R.id.lbItemNo);


        TextView lbQty = (TextView)view.findViewById(R.id.lbQty);
        TextView lbSalePrice = (TextView)view.findViewById(R.id.lbSalePrice);
        TextView lbSubAmt = (TextView)view.findViewById(R.id.lbSubAmt);
        TextView lbItemName = (TextView)view.findViewById(R.id.lbItemName);
        TextView lbRemoveItem = view.findViewById(R.id.lbRemoveItem);
        TextView lbAddQty = view.findViewById(R.id.lbAddQty);
        TextView lbReduceQty = view.findViewById(R.id.lbReduceQty);
        if(item.getMeasure_flag() == 1){
            lbAddQty.setVisibility(View.INVISIBLE);
            lbReduceQty.setVisibility(View.INVISIBLE);
        }


        item.setFlow_id(position + 1);
//        lbFlowId.setText(String.valueOf(position + 1));
//        lbItemNo.setText(item.getItemNo().trim());
        lbItemName.setText(item.getItem_name().trim());
        lbQty.setText(item.getSale_qnty().toString().trim());
        lbSalePrice.setText(item.getSale_price().toString().trim());
        lbSubAmt.setText(item.getSale_money().toString().trim());

        lbReduceQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getSale_qnty().compareTo(1.00) <= 0) {
                    return;
                }
                item.setSale_qnty(item.getSale_qnty() - 1);
                item.setSale_money(item.getSale_qnty() * (item.getSale_price()));
                /*if(item.getSale_qnty().compareTo(0.00) <= 0){
                    _list.remove(item);
                }*/
                notifyDataSetChanged();
                if(mOnItemClickListener != null)
                    mOnItemClickListener.onClick(item, view, position, -1);
            }
        });

        lbAddQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getSale_qnty().compareTo(99.00) >= 0) {
                    return;
                }
                item.setSale_qnty(item.getSale_qnty() + 1.00);
                item.setSale_money(item.getSale_qnty() * (item.getSale_price()));
                notifyDataSetChanged();
                if(mOnItemClickListener != null)
                    mOnItemClickListener.onClick(item, view, position, 1);
            }
        });

        lbRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener != null)
                    mOnItemClickListener.onClick(item, view, position, 0);
            }
        });
        return view;
    }

    public interface onItemClickEvent{
        void onClick(TRmSaleflow entity, View view, int position, int flag);
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
