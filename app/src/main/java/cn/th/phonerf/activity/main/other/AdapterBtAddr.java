package cn.th.phonerf.activity.main.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.model.BaseEntity;

public class AdapterBtAddr extends ArrayAdapter<BaseEntity>{

    private int resId;
    public AdapterBtAddr(Context context, int resource, List<BaseEntity> objects) {
        super(context, resource, objects);
        this.resId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        BaseEntity entity = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resId, null);

        TextView lbItemNo = (TextView)view.findViewById(R.id.stockrpt_list_lbItemNo);
        TextView lbCheckQty = (TextView)view.findViewById(R.id.stockrpt_list_lbCheckQty);

        lbItemNo.setText(entity.getKey());
        lbCheckQty.setText(entity.getValue());

        return view;
    }
}
