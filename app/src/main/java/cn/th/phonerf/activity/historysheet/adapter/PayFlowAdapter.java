package cn.th.phonerf.activity.historysheet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.dal.SyncServiceDal;
import cn.th.phonerf.model.TRmPayflow;
import cn.th.phonerf.model.TRmSaleflow;

public class PayFlowAdapter extends RecyclerView.Adapter<PayFlowAdapter.myViewHodler>  {

    private Context context;
    private List<TRmPayflow> mPayflow;
    private View view;

    //创建构造函数
    public PayFlowAdapter(Context context, List<TRmPayflow> goodsEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//当前上下文
        this.mPayflow = goodsEntityList;//实体类数据ArrayList

    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        view = View.inflate(context, R.layout.pay_flow_adapter, null);
        return new myViewHodler(view);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(myViewHodler holder, int position) {

        try {
            //根据点击位置绑定数据
            TRmPayflow data = mPayflow.get(position);
            holder.FlowId.setText("序号:"+data.getFlowId()+"");
            holder.SaleAmount.setText("销售金额（元）:"+data.getSaleAmount()+"");

            SyncServiceDal dal = new SyncServiceDal();
            String payment = dal.getPayMent(data.getPayWay());
            holder.PayWay.setText("付款方式:"+payment+"");
            holder.SellWay.setText("销售方式:"+((data.getSellWay()+"").equals("A")?"销售":"退货"));

        }catch (Exception e){
            Log.e("异常：",e.getMessage());
        }

    }


    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mPayflow.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private TextView FlowId;        //序号
        private TextView SaleAmount;    //销售金额
        private TextView PayWay;        //付款方式
        private TextView SellWay;       //销售方式

        public myViewHodler(View itemView) {
            super(itemView);
            FlowId = (TextView) itemView.findViewById(R.id.flow_id);
            SaleAmount = (TextView) itemView.findViewById(R.id.sale_amount);
            PayWay = (TextView) itemView.findViewById(R.id.pay_way);
            SellWay = (TextView) itemView.findViewById(R.id.sell_way);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, mPayflow.get(getLayoutPosition()));
                    }
                }
            });

        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        public void OnItemClick(View view, TRmPayflow data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
