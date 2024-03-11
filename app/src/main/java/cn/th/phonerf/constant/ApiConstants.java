package cn.th.phonerf.constant;

public class ApiConstants {

    //public static String BASE_API_URL= "http://phonerf.thcode.net:24698/phonerf-api/"; //

//     public static String BASE_API_URL= "http://123.60.188.53:19094/"; //
    public static String BASE_API_URL= "http://192.168.2.20:19090/"; //
//    public static String BASE_API_URL= "http://192.168.9.128:19094/"; //
    //促销服务器
    public static String BASE_API_URLA = "http://192.168.9.66:19094/"; //
//    public static String BASE_API_URLA = "http://192.168.3.33:51693/";

    //certutil -hashfile md5test.txt MD5
    public static String SHOP_API_URL= "";
    /**index
     * 获取API地址
     */
    public static final String VERSION_ACTION = "handler/posInfoHandler.ashx?op=version";
    public final static String FINDITEMINFO = "handler/itemHandler.ashx?op=findItemInfo";

    public final static String GET_SHOPPINGBAG_ITEM = "handler/shopcartHandler.ashx?op=getShoppingBag";
    public final static String ADD_TO_CART = "handler/shopcartHandler.ashx?op=add";
    public final static String CLEAR_CART = "handler/shopcartHandler.ashx?op=clear";
    public final static String QUERY_CART = "handler/shopcartHandler.ashx?op=query";
    public final static String UPDATE_CART = "handler/shopcartHandler.ashx?op=update";
    public final static String REMOVE_CART = "handler/shopcartHandler.ashx?op=remove";
    public final static String QUERY_VIP = "handler/shopcartHandler.ashx?op=vip";
    public final static String VIP_UPDATE_FLOW = "handler/shopcartHandler.ashx?op=vipUpdateFlow";

    public final static String PRE_FOR_ALL_DISCOUNT = "handler/shopcartHandler.ashx?op=preForAllDis";
    public final static String ACTION_PAY_PAY = "handler/payHandler.ashx?op=pay";
    public final static String ACTION_PAY_QUERY = "handler/payHandler.ashx?op=query";
    public final static String ACTION_PAY_CANCEL = "lxPay/cancelFlow";

    public final static String ACTION_PAY_XG = "handler/payXgHandler.ashx?op=pay";
    public static String GET_API_URL = "";

    public static String LOCAL_API_URL = "";

    public final static String ACTION_POSINFO_SELECTBYSN = "handler/posInfoHandler.ashx?op=selectBySn";
    public final static String LOGIN_ACTION = "handler/posInfoHandler.ashx?op=login";
    public final static String OPERATOR_MODIFYPASS_ACTION = "SysOper/modifyPass";

    public final static String ACTION_CONSUMER = "api-vip-client/vip-info/doConsumer";
    public final static String ACTION_UNCONSUMER = "api-vip-client/vip-info/doUnConsumer";
    public final static String ACTION_HISTORY = "api-vip-client/vip-info/getVipRechangeAndConsumerRpt";

    public final static String ACTION_MERCHANT_INFO_SELECTBYNO = "MerchantInfo/selectByNo";
    public final static String ACTION_DOWNLOAD_MERCHANT_LIST = "MerchantShop/selectByMerchantId";
    public final static String ACTION_MERCHANTUSER_CANLOGINSHOP = "MerchantUser/canLoginShop";
    public final static String ACTION_MERCHANTUSER_MODIFYPASS = "MerchantUser/modifyPass";
    public final static String ACTION_MERCHANTUSER_GETGRANTS = "MerchantUser/getGrants";

    public final static String ACTION_MERCHANTPHONE_SELECTBYSN = "MerchantPhone/selectByPhoneSn";


    public final static String ACTION_DOWNLOAD_SYS_SYSTEM = "SystemInfo/selectByMerchantId";

    public final static String ACTION_DOWNLOAD_PRINT_LABEL = "PrintDesign/selectByMerchantId";

    public final static String ACTION_LOCAL_QUERY_SQL = "Sql/execSql";
    public final static String ACTION_LOCAL_QUERY_SINGLE = "Sql/execSingle";

    public final static String ACTION_LOCAL_QUERY_SQL_JDBC = "Sql/jdbcExecSql";
    public final static String ACTION_LOCAL_QUERY_SINGLE_JDBC = "Sql/jdbcExecSingle";

    public final static String ACTION_LOCAL_QUERY_SQL_JDBC_SYBASE = "Sql/jdbcExecSqlSybase";
    public final static String ACTION_LOCAL_QUERY_SINGLE_JDBC_SYBASE = "Sql/jdbcExecSingleSybase";


    //public final static String ACTION_LOCAL_DOWN_ITEM_SQL = "Sql/downItemSql";
    //public final static String ACTION_LOCAL_DOWN_BARCODE_SQL = "Sql/downBarcodeSql";
    //public final static String ACTION_LOCAL_DOWN_COUNT_SQL = "Sql/downCountSql";

    //盘点
    public final static String ACTION_CHECK_NEWCHECKNO = "Check/newCheckNo";
    public final static String ACTION_CHECK_UPLOADSTOCKTAKEDETAIL = "Check/uploadStocktakeDetail";
    public final static String ACTION_CHECK_FINISHCHECKNO= "Check/finishCheckNo";
    public final static String ACTION_CHECK_SELECTBYID = "Check/selectById";
    public final static String ACTION_CHECK_SELECTBYMERCHANTID = "Check/selectByMerchantId";
    public final static String ACTION_CHECK_GETSERVERQTY = "Check/getServerQty";
    public final static String ACTION_RECEIVE_UPLOADRECEIVEDETAIL = "Receive/uploadReceiveDetail";





    public final static String ACTION_ITEMINFO_SELECTBIGCLS = "ItemInfo/selectBigCls";
    public final static String ACTION_ITEMINFO_SELECTSMALLCLS = "ItemInfo/selectSmallCls";
    public final static String ACTION_ITEMINFO_SELECTBYITEMCLSNO = "ItemInfo/selectByItemClsno";




    public final static String ACTION_SALERINFO_SELECTBYID = "SalerInfo/getSalerByBranchNo";

    public final static String ACTION_JXYH_PAY = "lxPay/pay";
    public final static String ACTION_JXYH_QUERY = "lxPay/query";
    public final static String ACTION_JXYH_CANCEL = "lxPay/cancel";


    public final static String ACTION_GETMAXTODAYFLOWNO = "Common/getMaxTodayFlowNo";

    public final static String ACTION_DOWNLOAD_ITEM_INFO = "Sync/downloadItemInfo";
    //下载最新商品类别信息
    public final static String ACTION_ITEMINFO_DOWNLIADITEMCLS = "Sync/downloadItemCls";
    //根据本店id查询营业员
    public final static String ACTION_ITEMINFO_DOWNLOADSALEMAN = "Sync/downloadSaleman";
    //商品价格下载
    public final static String ACTION_ITEMINFO_DOWNLOADPRICE = "Sync/downloadprice";

    public final static String ACTION_ITEMINFO_DOWNLOADPAYMENT = "Sync/downloadPayment";

    public final static String ACTION_UPLOADFLOW = "Upload/uploadFlow";

    public final static String ACTION_FLOW_SELECTBYFLOWBRANCH = "Flow/selectByFlowBranch";


    public final static String ACTION_JXYH_QUERYBYPHONEORDERNO = "TlzfPay/queryByPhoneOrderNo";
    public final static String ACTION_FLOW_ISNOTEXISTS = "Flow/isNotExists";

    public final static String HANDLER_PROMOTE = "handler/promote.ashx?op=promote";

    public final static String ACTION_UREA_SELECTLIST = "ureaService/selectList";

    public final static String ACTION_UREA_SELECTLIST_FINISH = "ureaService/selectFinishList";

    public final static String ACTION_UREA_SELECTBYID = "ureaService/selectById";


    /**
     * 检查版本
     */


    /**
     * BANNER
     */
    public static final String BANNER_ACTION = "Banner";
    /**
     * 日志上送
     */
    public static final String LOG_RECORD_ACTION = "LogRecord/upload";
}
