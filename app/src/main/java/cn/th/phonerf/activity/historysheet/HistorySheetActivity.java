package cn.th.phonerf.activity.historysheet;

import android.content.Context;
import android.media.MediaRouter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.histonepos.npsdk.printer.IPrinterService;

import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.historysheet.adapter.PayFlowAdapter;
import cn.th.phonerf.activity.historysheet.adapter.SaleFlowAdapter;
import cn.th.phonerf.activity.second.SecondPresentation;
import cn.th.phonerf.constant.GFunc;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.SyncServiceDal;
import cn.th.phonerf.model.TRmPayflow;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.model.t_sys_printer_head;
import cn.th.phonerf.model.t_sys_printer_pay;
import cn.th.phonerf.utils.print.PrintPaper;
import cn.th.phonerf.utils.print.PrintString;

public class HistorySheetActivity extends BaseActivity {

    private TextView pageTitleText, rightText;
    private ImageView backText;

    private TextView lbMsg;
    private RecyclerView mRecyclerViewSaleFlow;
    private RecyclerView mRecyclerViewPayFlow;
    private List<TRmSaleflow> tRmSaleflow;
    private List<TRmPayflow> tRmPayflows;

    private String FlowNo;
    //外设
    IPrinterService printer = null;
    private SecondPresentation mPresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_sheet);


        initUI();
        //initPrinter();

        initDialog();

        final SyncServiceDal dal = new SyncServiceDal();
        try {
            FlowNo = dal.getMaxFlowNo(GSale.StoreNo);
            if(FlowNo == null || FlowNo.equals("")){
                ScrollView scr = findViewById(R.id.scr);
                LinearLayout btn = findViewById(R.id.btn);
                TextView noneText = findViewById(R.id.noneText);
                scr.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);
                noneText.setVisibility(View.VISIBLE);
            }

            tRmPayflows = dal.selectFlowNoPayflow(FlowNo);
            tRmSaleflow = dal.selectBigSaleflow(FlowNo);

        }catch (Exception e){
            Log.e("异常：",e.getMessage());
        }

        recyclerInit();


        //上一单
        LinearLayout upper_order = findViewById(R.id.upper_order);
        upper_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String flowNo = dal.getUpperOrder(GSale.StoreNo,FlowNo);
                    if (flowNo == null || flowNo.equals("")){
                        setToast("没有上一单");
                    }else {
                        FlowNo = flowNo;
                    }

                    tRmPayflows = dal.selectFlowNoPayflow(FlowNo);
                    tRmSaleflow = dal.selectBigSaleflow(FlowNo);

                    mRecyclerViewSaleFlow.getAdapter().notifyDataSetChanged();
                    mRecyclerViewPayFlow.getAdapter().notifyDataSetChanged();
                    recyclerInit();

                }catch (Exception e){
                    setToast("异常：" + e.getMessage());
                }
            }
        });


        //打印
        LinearLayout printing = findViewById(R.id.printing);
        printing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    printData();
                }catch (Exception e){
                    setToast("异常：" + e.getMessage());
                }
            }
        });


        //下一单
        LinearLayout next_order = findViewById(R.id.next_order);
        next_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String flowNo = dal.getNextOrder(GSale.StoreNo,FlowNo);
                    if (flowNo.equals("")){
                        setToast("没有下一单");
                    }else {
                        FlowNo = flowNo;
                    }

                    tRmPayflows = dal.selectFlowNoPayflow(FlowNo);
                    tRmSaleflow = dal.selectBigSaleflow(FlowNo);

                    mRecyclerViewSaleFlow.getAdapter().notifyDataSetChanged();
                    mRecyclerViewPayFlow.getAdapter().notifyDataSetChanged();
                    recyclerInit();

                }catch (Exception e){
                    setToast("异常：" + e.getMessage());
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresentation != null){
            //Log.i(TAG, "********stop*****");
            mPresentation.dismiss();
            mPresentation = null;
        }
        //if(_threadTime != null)
        //_threadTime.
    }

    private void initUI() {
        try{
            //顶部
            pageTitleText = (TextView) findViewById(R.id.top_title_text);
            pageTitleText.setText("历史单据");
            backText = (ImageView) findViewById(R.id.top_back_image);
            backText.setVisibility(View.VISIBLE);
            backText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            rightText = (TextView) findViewById(R.id.top_right_text);
            rightText.setText("历史");
            rightText.setVisibility(View.INVISIBLE);
            //rightText.setOnClickListener(this);
            lbMsg = (TextView)findViewById(R.id.lbMsg);
        }catch (Exception e){
            setToast("异常："+e);
        }

    }

    //初始化适配器
    private void recyclerInit(){
        try{
            //让item撑满RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };

            //获取RecyclerView
            mRecyclerViewSaleFlow = (RecyclerView) findViewById(R.id.saleflow);
            //创建adapter
            SaleFlowAdapter mSaleFlowRecyclerAdapter = new SaleFlowAdapter(this, tRmSaleflow);
            //给RecyclerView设置adapter
            mRecyclerViewSaleFlow.setAdapter(mSaleFlowRecyclerAdapter);
            //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
            //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
            mRecyclerViewSaleFlow.setLayoutManager(layoutManager);
            //设置item的分割线
//        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            mSaleFlowRecyclerAdapter.setOnItemClickListener(new SaleFlowAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, TRmSaleflow data) {

                }
            });



            //让item撑满RecyclerView
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(this) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };
            //获取RecyclerView
            mRecyclerViewPayFlow = (RecyclerView) findViewById(R.id.payflow);
            //创建adapter
            PayFlowAdapter mPayFlowRecyclerAdapter = new PayFlowAdapter(this, tRmPayflows);
            //给RecyclerView设置adapter
            mRecyclerViewPayFlow.setAdapter(mPayFlowRecyclerAdapter);
            //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
            //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
//        mRecyclerViewPayFlow.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerViewPayFlow.setLayoutManager(layoutManager1);
            //设置item的分割线

//        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            mPayFlowRecyclerAdapter.setOnItemClickListener(new PayFlowAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, TRmPayflow data) {

                }
            });
        }catch (Exception e){
            setToast("异常："+e);
        }



    }
/*
    private void initPrinter() {
        try{
            printer = getPrinter();
            if (printer == null) {
                //Toast.makeText(getActivity(), "get printer failed!", Toast.LENGTH_SHORT).show();
                setToast("获取打印机失败!");
                return;
            }
            // tell sdk the widthmode(58mm or 80mm) of printer in use.
            Bundle bundle = new Bundle();
            String widthType = PrinterConsts.PRT_CFG_VALUE_WIDTH_MODE_58;
            bundle.putString(PrinterConsts.PRT_CFG_KEY_WIDTH_MODE, widthType);
            try {
                printer.setConfig(bundle);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch (Exception e){
            setToast("异常："+e);
        }
    }*/

    //打印
    public boolean printData()
    {

        try {

            PrintPaper paper = new PrintPaper();
            List<PrintString> listPrtStr = new ArrayList<PrintString>();
            t_sys_printer_head headModel = new t_sys_printer_head();

            headModel.branch_no = GSale.StoreNo;    //服务区id
            headModel.branch_name = GSale.StoreName;//服务区名称
            headModel.store_no = GSale.StoreNo;         //门店id
            headModel.store_name = GSale.StoreName;     //门店名称
            headModel.cashier_no = GSale.CashierNo;     //登录id
            headModel.cashier_name = GSale.CashierName; //登录名称
            headModel.sale_man = tRmPayflows.get(0).getSaleMan();         //营业员
            headModel.sale_name = "";       //营业员名称
            headModel.flow_no = tRmPayflows.get(0).getFlowNo();        //单号
            headModel.oper_date = tRmSaleflow.get(0).getOper_date();    //下单时间
            headModel.rdmNo = "";//_listPayFlow.get(0).getRdmNo();

            t_sys_printer_pay printPay = new t_sys_printer_pay();

            printPay.pos_sale_way = (tRmPayflows.get(0).getSellWay().equals("A")? 0 : 1);   //销售方式
            printPay.total_amt = tRmPayflows.get(0).getSaleAmount();                //销售金额
            printPay.total_qty = tRmSaleflow.get(0).getSale_qnty();                  //销售数量
            //printPay.dis_amt = this._frmPay.GetDisAmt(),
            //printPay.source_amt = this._frmPay.GetSourceAmt(),
            printPay.total_packages = Double.valueOf(tRmSaleflow.size());           //ToDo 件数

            /*
            PrintInfo.IsPrtSalesMore = false;
            if (GParam.IsSalesMore && (this._frmMain.ListSaleFlow.Count > 0))
            {
                string str2 = this._frmMain.ListSaleFlow[0].sale_man;
                foreach (t_rm_saleflow _saleflow in this._frmMain.ListSaleFlow)
                {
                    if (str2 != SIString.TryStr(_saleflow.sale_man))
                    {
                        PrintInfo.IsPrtSalesMore = true;
                        break;
                    }
                }
            }
            */
            //GInit.InitPrintParmOnlyWidth();
            boolean isHzPrinter = true;
            GFunc.AddListPrt(listPrtStr, paper.PrintHeadEmptyLine());
            GFunc.AddListPrt(listPrtStr, paper.PrintHeader(true, headModel, isHzPrinter, 0x63));
            GFunc.AddListPrt(listPrtStr, paper.PrintDetailTitle(isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintDetail(tRmSaleflow, isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintPayInfo(tRmPayflows, null, printPay, isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintFooter(isHzPrinter));
            GFunc.AddListPrt(listPrtStr, paper.PrintFooterEmptyLine());

            if(printer!= null){
                printer.openCashBox();
                printer.beginTranscation();
                //printer.
                //printer.addImage(getBitmap());

                for(PrintString item : listPrtStr){
                    if(item.getIsBigSize()){
                        printer.setTextBold();
                        printer.addTextSizeDouble();

                    }
                    printer.addText(item.getPrtStr() + "\n", 0);
                    if(item.getIsBigSize()){
                        printer.cancelTextBold();
                        printer.addTextSizeNormal();

                    }
                }
                printer.addFeedLine(10);
                printer.addCut();
                int transId = printer.endTranscation();
                printer.commit(transId);
                /*
                printer.setTextBold();
                printer.addText("-----------Payment receipts--------------\n",1 );
                printer.cancelTextBold();
                printer.addTextSizeDouble();
                printer.addText("     Welcome\n", 1);
                printer.addTextSizeNormal();
                printer.addText("operator:01    Jadeite Restaurant\n", 1);
                printer.addText("------------------------------\n", 1);
                printer.addText("product     amount    price\n", 1);
                printer.addText("1.Lobster dumplings      x2     64.00\n", 1);
                printer.addText("2.Qingdao Prawn        x10     380.00\n", 1);
                printer.addBarcode("ABCD0123456789012345");
                printer.addQRCode("This Is A Simple QR Code", 6);
                printer.addFeedLine(3);
                printer.addCut();
                int transId = printer.endTranscation();
                printer.commit(transId);
                 */
            }


        }catch (Exception ex){
            setToast("打印异常!打印异常!打印异常!\n" + ex.getMessage());
        }

            /*if (GLog.IsLogBillPrint)
            {
                GLog.WritePrintInfo(listPrtStr);
            }*/

        return true;
    }


    //region region:外设区域
    private void initDialog(){
        // Get the current route and its presentation display.
        try{
            MediaRouter mMediaRouter = (MediaRouter)mContext.getSystemService(Context.MEDIA_ROUTER_SERVICE);
            MediaRouter.RouteInfo route = mMediaRouter.getSelectedRoute(
                    MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
            Display presentationDisplay = route != null ? route.getPresentationDisplay() : null;

            // Dismiss the current presentation if the display has changed.
            if (mPresentation != null && mPresentation.getDisplay() != presentationDisplay) {
                //Log.w(TAG, "Dismissing presentation because the current route no longer "
                //+ "has a presentation display.");
                mPresentation.dismiss();
                mPresentation = null;
            }

            // Show a new presentation if needed.
            if (mPresentation == null && presentationDisplay != null) {
                mPresentation = new SecondPresentation(mContext, presentationDisplay,1);

            }
            if(mPresentation != null) {
                mPresentation.show();
            }
        }catch (Exception e){
            setToast("异常："+e);
        }
    }
}
