package cn.th.phonerf.activity.report.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.report.bean.ReportItem1;

/**
 * Created by FW on 2016-10-01.
 */

public class ReportItemAdapter extends ArrayAdapter<ReportItem1> {

    private int resId;

    public ReportItemAdapter(Context context, int resource, List<ReportItem1> objects) {
        super(context, resource, objects);
        this.resId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        ReportItem1 entity = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resId, null);

        TextView lbFlowId = (TextView)view.findViewById(R.id.ls_report_content_lbFlowId);
        TextView lbItemNo  = (TextView)view.findViewById(R.id.ls_report_content_lbItemNo);
        TextView lbItemName  = (TextView)view.findViewById(R.id.ls_report_content_lbItemName);
        TextView lbQty1  = (TextView)view.findViewById(R.id.ls_report_content_lbQty1);
        TextView lbQty2  = (TextView)view.findViewById(R.id.ls_report_content_lbQty2);

        //lbId.setText(entity.getDisplayMember());
        //lbValue.setText(entity.getValueMember());

        lbFlowId.setText(position);
        lbItemNo.setText(entity.getItemNo());
        lbItemName.setText(entity.getItemName());
        lbQty1.setText(entity.getQty1());
        lbQty2.setText(entity.getQty2());

        return view;
    }
}
