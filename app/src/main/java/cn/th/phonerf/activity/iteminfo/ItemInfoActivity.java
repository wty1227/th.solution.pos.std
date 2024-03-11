package cn.th.phonerf.activity.iteminfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.base.BaseFragment;
import cn.th.phonerf.activity.iteminfo.present.IItemInfoPresenter;
import cn.th.phonerf.activity.iteminfo.present.ItemInfoPresenterImpl;
import cn.th.phonerf.activity.iteminfo.view.IItemInfoView;
import cn.th.phonerf.activity.pos.other.AdapterBigClsList;
import cn.th.phonerf.activity.pos.other.AdapterItemList;
import cn.th.phonerf.activity.pos.other.AdapterSmallClsList;
import cn.th.phonerf.activity.pos.other.GridItemDecoration;
import cn.th.phonerf.activity.pos.other.LinearItemDecoration;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.model.TBdItemCls;
import cn.th.phonerf.model.TBdItemInfo;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.utils.ProgressUtils;

public class ItemInfoActivity extends BaseFragment implements IItemInfoView {

    //private Dialog loadingDlg;
    private IItemInfoPresenter presenter;

    private Context mContext;
    private BaseActivity mActivity;
    private TextView pageTitleText, rightText;
    private ImageView backText;


    ListView plClsBig;
    AdapterBigClsList _adapterBigCls;

    RecyclerView plClsSmall;
    AdapterSmallClsList _adapterSmallCls;

    RecyclerView plItem;
    AdapterItemList _adapterItemList;

    String _itemClsNo = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //this.getActivity();
        View view = inflater.inflate(R.layout.fragment_item_info, container, false);
        //mContext = view.getContext();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();

        mContext = mActivity.getApplicationContext();
        loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), false);

        //loadingDlg = ProgressUtils.loadCircleProgress(mActivity, getString(R.string.loading), false);
        presenter = new ItemInfoPresenterImpl(mActivity, this);

        initUI(mActivity);
        loadData();
        loadItemData();
    }

    

    private void initUI(FragmentActivity view){

        plClsBig = (ListView)view.findViewById(R.id.plClsBig);
        plClsSmall = (RecyclerView)view.findViewById(R.id.plClsSmall);
        plItem = (RecyclerView)view.findViewById(R.id.plItem);

        int spanCount = 1; // 只显示一行
        // 定义一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        // 设置布局管理器
        plClsSmall.setLayoutManager(layoutManager);
        plClsSmall.addItemDecoration(new LinearItemDecoration(mContext, LinearLayoutManager.HORIZONTAL));

        GridLayoutManager manager = new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false);
        plItem.setLayoutManager(manager);
        plItem.addItemDecoration(new GridItemDecoration(mContext));


    }

    private void loadData() {
        List<TBdItemCls> lists = new ArrayList<>();
        _adapterBigCls = new AdapterBigClsList(mContext, R.layout.ls_big_cls_list, lists);
        plClsBig.setAdapter(_adapterBigCls);

        List<TBdItemCls> lists2 = new ArrayList<>();
        _adapterSmallCls = new AdapterSmallClsList(mContext, R.layout.ls_small_cls_list, lists2);
        plClsSmall.setAdapter(_adapterSmallCls);


        List<TBdItemInfo> lists3 = new ArrayList<>();
        _adapterItemList = new AdapterItemList(mContext, R.layout.ls_item_list, lists3);
        plItem.setAdapter(_adapterItemList);

    }

    private void loadItemData(){
        //大类点击事件
        _adapterBigCls.setOnItemClickEvent(new AdapterBigClsList.onItemClickEvent() {
            @Override
            public void onClick(TBdItemCls entity) {
                //_keyFunc = "bigcls";
                //lbMessage.setText("选择了[" + entity.getItemClsno() + "]" + entity.getItemClsname() + "类别");
                _itemClsNo = entity.getItemClsno();
                presenter.doGetSmallCls(null, entity.getItemClsno());
            }
        });
        //小类点击事件
        _adapterSmallCls.setOnItemClickEvent(new AdapterSmallClsList.onItemClickEvent() {
            @Override
            public void onClick(TBdItemCls entity) {
                //_keyFunc = "smallcls";
                //lbMessage.setText("选择了[" + entity.getItemClsno() + "]" + entity.getItemClsname() + "类别");
                presenter.doGetItemList(null, entity.getItemClsno());
            }
        });

        //region region:商品点击事件
        _adapterItemList.setOnItemClickEvent(new AdapterItemList.onItemClickEvent() {
            @Override
            public void onClick(TBdItemInfo entity) {

                TRmSaleflow  _item = new TRmSaleflow();
                _item.setBranch_no(GSale.StoreNo);
                _item.setItem_no(entity.getItemNo());
                _item.setSource_price(entity.getSalePrice());
                _item.setSale_price(entity.getSalePrice());
                _item.setCost_price(entity.getPrice());
                _item.setSale_qnty(1.00);
                _item.setSale_money(_item.getSale_qnty() * _item.getSale_price());
                _item.setSell_way("A");
                _item.setOper_id(GSale.CashierNo);
                _item.setSale_man(GSale.SaleMan);
                _item.setSale_name(GSale.SaleName);
                _item.setCard_id("");
                _item.setCard_no("");
                _item.setShift_no(0.00);
                _item.setCom_flag("1");//1：未上传   0：已上传

                _item.setItem_name(entity.getItemName());
                mActivity.addSaleFlowItem(_item);
            }
        });
        // 进入自动获取大类
        presenter.doGetBigCls(null);
    }

    @Override
    public void resGetBigCls(List<TBdItemCls> lists) {
        try{
            System.out.println("resGetBigCls");
            this._adapterBigCls.clear();
            this._adapterBigCls.addAll(lists);
            this._adapterBigCls.notifyDataSetChanged();

//            if(lists.size() > 0) {
//                presenter.doGetSmallCls(null, "10");
//            }
        }catch (Exception e){
            setToast("异常："+e);
        }
    }

    @Override
    public void resGetSmallCls(List<TBdItemCls> lists) {
        try{
            this._adapterSmallCls.clear();
            this._adapterSmallCls.addAll(lists);
            this._adapterSmallCls.notifyDataSetChanged();
            //模拟点击第一个小类
            if(lists.size() > 0)
                presenter.doGetItemList(null, lists.get(0).getItemClsno());
            else{
                presenter.doGetItemList(null, this._itemClsNo);
            }
        }catch (Exception e){
            setToast("异常："+e);
        }
    }

    @Override
    public void resGetItemList(List<TBdItemInfo> lists) {
        try{
            this._adapterItemList.clear();
            this._adapterItemList.addAll(lists);
            this._adapterItemList.notifyDataSetChanged();
        }catch (Exception e){
            setToast("异常："+e);
        }
    }

    @Override
    public void resRequestData() {

    }

    @Override
    public void showErrorMsg(String errStr) {

    }
}