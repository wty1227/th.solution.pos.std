package cn.th.phonerf.activity.pos.other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.model.TBdItemInfo;

public class AdapterItemList extends RecyclerView.Adapter {

    private Context mContext;
    private List<TBdItemInfo> mEntityList;
    private int resId;

    public AdapterItemList(Context context, int resource, List<TBdItemInfo> objects){
        this.mContext = context;
        this.mEntityList = objects;
        this.resId = resource;
    }

    public void clear(){
        mEntityList.clear();
    }

    public void addAll(List<TBdItemInfo> objects){
        mEntityList = objects;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(this.resId, parent, false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TBdItemInfo item = mEntityList.get(position);
        ((DemoViewHolder)holder).lbItemNo.setText(item.getItemNo().trim());
        ((DemoViewHolder)holder).lbItemName.setText(item.getItemName().trim());
        if(item.getSalePrice() != 0.00)
            ((DemoViewHolder)holder).lbSalePrice.setText(item.getSalePrice().toString().trim());

        if(mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }

    private class DemoViewHolder extends RecyclerView.ViewHolder{

        private TextView lbItemNo;
        private TextView lbItemName;
        private TextView lbSalePrice;

        public DemoViewHolder(View view) {
            super(view);
            lbItemNo = (TextView)view.findViewById(R.id.lbItemNo);
            lbItemName = (TextView)view.findViewById(R.id.lbItemName);
            lbSalePrice = (TextView)view.findViewById(R.id.lbSalePrice);
        }
    }

    public interface onItemClickEvent{
        void onClick(TBdItemInfo entity);
    }
    private onItemClickEvent mOnItemClickListener;

    public void setOnItemClickEvent(onItemClickEvent mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    /*
    private int resId;
    public AdapterSmallClsList(@android.support.annotation.NonNull Context context, int resource, @android.support.annotation.NonNull List<TBdItemInfo> objects) {
        super(context, resource, objects);
        this.resId = resource;
    }


    @android.support.annotation.NonNull
    @Override
    public View getView(int position, @android.support.annotation.Nullable View convertView, @android.support.annotation.NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);


        final TBdItemInfo item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resId, null);

        TextView lbItemClsno = (TextView)view.findViewById(R.id.lbItemClsno);
        TextView lbItemClsname = (TextView)view.findViewById(R.id.lbItemClsname);
        lbItemClsno.setText(item.getItemClsno());
        lbItemClsname.setText(item.getItemClsname());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(item);
            }
        });

        return view;
    }

    public interface onItemClickEvent{
        void onClick(TBdItemInfo entity);
    }


    private onItemClickEvent mOnItemClickListener;

    public void setOnItemClickEvent(onItemClickEvent mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }*/
}
