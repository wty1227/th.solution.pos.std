package cn.th.phonerf.activity.historysheet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.model.TRmSaleflow;

public class SaleFlowAdapter  extends RecyclerView.Adapter<SaleFlowAdapter.myViewHodler>  {

    private Context context;
    private List<TRmSaleflow> mSaleflow;
    private View view;

    //创建构造函数
    public SaleFlowAdapter(Context context, List<TRmSaleflow> goodsEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//当前上下文
        this.mSaleflow = goodsEntityList;//实体类数据ArrayList

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
        view = View.inflate(context, R.layout.sale_flow_adapter, null);
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

        try{
            //根据点击位置绑定数据
            TRmSaleflow data = mSaleflow.get(position);
            holder.FlowId.setText("序号:"+data.getFlow_id()+"");
            holder.BranchNo.setText("门店:"+data.getBranch_no()+"");
            holder.ItemNo.setText("商品编码:"+data.getItem_no()+"");
            holder.SaleQnty.setText("数量:"+data.getSale_qnty()+"");
            holder.SaleMoney.setText("金额（元）:"+data.getSale_money()+"");
            holder.SellWay.setText("商品名称:"+data.getItem_name()+"");
        }catch (Exception e){
            Log.d("异常：",e+"");
        }
    }


    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mSaleflow.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private TextView FlowId;        //序号
        private TextView BranchNo;      //门店
        private TextView ItemNo;        //商品编码
        private TextView SaleQnty;      //数量
        private TextView SaleMoney;     //金额
        private TextView SellWay;       //销售方式

        public myViewHodler(View itemView) {
            super(itemView);
            FlowId = (TextView) itemView.findViewById(R.id.flow_id);
            BranchNo = (TextView) itemView.findViewById(R.id.branch_no);
            ItemNo = (TextView) itemView.findViewById(R.id.item_no);
            SaleQnty = (TextView) itemView.findViewById(R.id.sale_qnty);
            SaleMoney = (TextView) itemView.findViewById(R.id.sale_money);
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
                        onItemClickListener.OnItemClick(v, mSaleflow.get(getLayoutPosition()));
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
        public void OnItemClick(View view, TRmSaleflow data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
