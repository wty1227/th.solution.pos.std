package cn.th.phonerf.activity.pos.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.model.TBdItemCls;

public class AdapterBigClsList extends ArrayAdapter<TBdItemCls> {

    private int resId;
    public AdapterBigClsList(@android.support.annotation.NonNull Context context, int resource, @android.support.annotation.NonNull List<TBdItemCls> objects) {
        super(context, resource, objects);
        this.resId = resource;
    }

    @android.support.annotation.NonNull
    @Override
    public View getView(int position, @android.support.annotation.Nullable View convertView, @android.support.annotation.NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        final TBdItemCls item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resId, null);

        TextView lbItemClsno = (TextView)view.findViewById(R.id.lbItemClsno);
        TextView lbItemClsname = (TextView)view.findViewById(R.id.lbItemClsname);
        lbItemClsno.setText(item.getItemClsno().trim());
        lbItemClsname.setText(item.getItemClsname().trim());
        if(mOnItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(item);
                }
            });
        }
        return view;
    }

    public interface onItemClickEvent{
        void onClick(TBdItemCls entity);
    }


    private onItemClickEvent mOnItemClickListener;

    public void setOnItemClickEvent(onItemClickEvent mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
