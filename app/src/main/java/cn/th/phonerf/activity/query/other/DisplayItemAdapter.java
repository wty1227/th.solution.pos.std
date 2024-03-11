package cn.th.phonerf.activity.query.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.model.BaseEntity;

/**
 * Created by FW on 2016-10-01.
 */

public class DisplayItemAdapter extends ArrayAdapter<BaseEntity> {

    private int resId;

    public DisplayItemAdapter(Context context, int resource, List<BaseEntity> objects) {
        super(context, resource, objects);
        this.resId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        final BaseEntity entity = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resId, null);

        TextView lbKey = (TextView)view.findViewById(R.id.lbKey);
        TextView lbValue  = (TextView)view.findViewById(R.id.lbValue);

        //lbId.setText(entity.getDisplayMember());
        //lbValue.setText(entity.getValueMember());

        lbKey.setText(entity.getKey());
        lbValue.setText(entity.getValue());


        if(mOnItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(entity);
                }
            });
        }

        return view;
    }

    public interface onItemClickEvent{
        void onClick(BaseEntity entity);
    }


    private onItemClickEvent mOnItemClickListener;

    public void setOnItemClickEvent(onItemClickEvent mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
