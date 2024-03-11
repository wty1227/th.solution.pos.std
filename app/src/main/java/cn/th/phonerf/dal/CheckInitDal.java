package cn.th.phonerf.dal;

import android.database.Cursor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.model.CheckDetail;
import cn.th.phonerf.model.CheckHistoryItem;
import cn.th.phonerf.model.t_bd_item_info;
import cn.th.phonerf.utils.DBUtil;

/**
 * Created by Administrator on 2017-04-14.
 */

public class CheckInitDal {


    /*
    public ArrayList<CheckInit> DownBatchNo() throws Exception {
        ArrayList<CheckInit> lists = new ArrayList<CheckInit>();
        final String nodeName = "XQueryTallBatch";
        final String[] paras = {GParam.BranchNo};


        BaseEntity transEntity = new BaseEntity();
        GConn.proxy.QueryFrmXml(GDevice.DeviceSn, nodeName, paras, transEntity);
        if(!transEntity.getResultCode().equals("ok")){
            throw new Exception(transEntity.getResultCode());
        }
        String ds = transEntity.getKey();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodeCollect = null;
        JsonNode node = null;
        nodeCollect = mapper.readTree(ds);
        int row = nodeCollect.size();
        if(row == 0)
        {
            //Exception e = new Exception("点货单不存在");
            //throw e;
            return lists;
        }
        //CheckInit[] lists
        for (int i = 0; i < row; i++) {
            node = nodeCollect.get(i);
            CheckInit entity = new CheckInit();
            entity.setSheet_no(node.get("SHEETID").asText().trim());
            entity.setBranch_no(node.get("SHOPID").asText().trim());
            entity.setOper_date(node.get("BUILDDATE").asText().trim());
            entity.setApprove_flag("0");
            entity.setOper_range("0");
            Insert(entity);
            lists.add(entity);
        }
        return lists;
        //CheckInit[] lists =  mapper.readValue(ds, CheckInit[].class);
        //Insert(lists);
    }

    public void DelBatchNo(String sheetNo){

        try {
            String str = "update check_master set com_flag = '2', user_id = ?, oper_date = datetime(CURRENT_TIMESTAMP,'localtime') where sheet_no = ?";
            DBUtil.dbItemExec.execSQL(str, new Object[]{GSale.CashierNo, sheetNo});
        }catch (Exception e){
            throw e;
        }
    }


    //获取盘点批次
    public ArrayList<CheckInit> scanQuery(String sheet_no, String branch_no){
        try {
            ArrayList<CheckInit> lists = new ArrayList<CheckInit>();
            String str = "select * from CheckInit where   date(oper_date) >= date('now', '-3 day') and sheet_no like ? and branch_no like ? order by sheet_no DESC limit 1";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{sheet_no, branch_no});
            if(cursor.moveToFirst()){
                do{
                    CheckInit entity = new CheckInit();
                    entity.setSheet_no(cursor.getString(cursor.getColumnIndex("sheet_no")));
                    entity.setOper_date(cursor.getString(cursor.getColumnIndex("oper_date")));
                    entity.setOper_range(cursor.getString(cursor.getColumnIndex("oper_range")));
                    entity.setBranch_no(cursor.getString(cursor.getColumnIndex("branch_no")));
                    entity.setCheck_cls(cursor.getString(cursor.getColumnIndex("check_cls")));
                    entity.setApprove_flag(cursor.getString(cursor.getColumnIndex("approve_flag")));
                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception ex){
            throw ex;
        }
    }

    /**
     * 获取历史盘点数据
     * @param
     * @return
     *//*


    public CheckInit SelectById(String sheet_no){
        CheckInit entity = null;
        try {
            String str = "select * from CheckInit where sheet_no = ?";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{sheet_no});
            if(cursor.moveToFirst()){
                do{
                    entity = new CheckInit();
                    entity.setSheet_no(cursor.getString(cursor.getColumnIndex("sheet_no")));
                    entity.setOper_date(cursor.getString(cursor.getColumnIndex("oper_date")));
                    entity.setOper_range(cursor.getString(cursor.getColumnIndex("oper_range")));
                    entity.setBranch_no(cursor.getString(cursor.getColumnIndex("branch_no")));
                    entity.setCheck_cls(cursor.getString(cursor.getColumnIndex("check_cls")));
                    entity.setApprove_flag(cursor.getString(cursor.getColumnIndex("approve_flag")));
                }while(cursor.moveToNext());
            }
            cursor.close();
            return entity;
        }catch (Exception ex){
            throw ex;
        }
    }
    //写入盘点批次
    public void Insert(CheckInit entity){
        String str;
        str = "replace into CheckInit(sheet_no, oper_date, oper_range,branch_no,check_cls,approve_flag)" +
                "values(?, ?, ?, ?, ?, ?)";
        try {
        DBUtil.dbItemExec.execSQL(str,
                new Object[]{
                        entity.getSheet_no(),
                        entity.getOper_date(),
                        entity.getOper_range(),
                        entity.getBranch_no(),
                        entity.getCheck_cls(),
                        entity.getApprove_flag()
                });
        }catch (Exception e){
            throw e;
        }
    }
    //批量写入盘点批次
    public void Insert(CheckInit[] lists){
       try {
           for(CheckInit item : lists){
               this.Insert(item);
           }
       }catch (Exception e){
           throw e;
       }
    }
*/
    //获取盘点单据
    public ArrayList<String> GetSheetNoByBatchNo(String checkNo){
        ArrayList<String> lists = new ArrayList<>();
        try {
            String str = "Select sheet_no from check_master where check_no = ? order by oper_date desc";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{checkNo});
            if(cursor.moveToFirst()){
                do{

                    lists.add(cursor.getString(0));
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception e){
            throw e;
        }
    }

    public float GetPreQty(String sheet_no, String item_no){
        float qty = 0;
        try {
            String str = "Select real_qty from check_detail where rtrim(sheet_no) = ? and rtrim(item_no) = ?";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{sheet_no, item_no});
            if(cursor.moveToFirst()){
                do{

                    qty = cursor.getFloat(0);
                    break;
                }while(cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            throw e;
        }
        finally {
            return qty;
        }
    }
/*
CREATE TABLE check_detail(
		check_no varchar(30) NOT NULL comment '批次',
		sheet_no CHAR(16) NOT NULL comment '单据号\货架号',
		phone_id int NULL comment '机器号',
		user_id varchar(20) null comment '登录账户',
		item_no varchar(15) NOT NULL comment '商品编码',
		item_subno varchar(15)  NULL comment '商品编码',
		item_name varchar(200)  NULL comment '商品名称',
		real_qty NUMERIC(9,3) not null comment '盘点数量',
		oper_date datetime null default CURRENT_TIMESTAMP comment '操作日期',
		-- placeno varchar(20) null,
		PRIMARY KEY(check_no, sheet_no, item_no)
)

 */
    public String SaveQty(String shopId, String checkNo, String sheetNo, String itemNo, float realQty, t_bd_item_info info){
        String str = "";
        try {
            if(sheetNo.equals("")){
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");//可以方便地修改日期格式
                String date = dateFormat.format( now );
                sheetNo = /*"CR" +*/ shopId + date;
                str = "replace into check_master(sheet_no,check_no,shop_id,user_id,oper_date,com_flag)values(?,?,?,?,datetime(CURRENT_TIMESTAMP,'localtime'),'1')";
                DBUtil.dbItemExec.execSQL(str, new Object[]{sheetNo,checkNo,shopId,GSale.CashierNo});
            }
            else{
                str = "update check_master set com_flag = '1', user_id = ?, oper_date = datetime(CURRENT_TIMESTAMP,'localtime') where sheet_no = ?";
                DBUtil.dbItemExec.execSQL(str, new Object[]{GSale.CashierNo, sheetNo});
            }

            str = "replace into check_detail(sheet_no,item_no,real_qty,oper_date,com_flag, phone_id, user_id, item_subno, item_name)values(?,?,?,datetime(CURRENT_TIMESTAMP,'localtime'),1,   ?,?,?,?)";
            DBUtil.dbItemExec.execSQL(str, new Object[]{sheetNo,itemNo,realQty, GSale.PosId, GSale.CashierNo, info.getItem_subno(), info.getItem_name()});
            return sheetNo;
            /*
            if(!sheet_no.equals("")){
                str = "update check_master set user_id = ?, oper_date = datetime(CURRENT_TIMESTAMP,'localtime') where sheet_no = ?";
                DBUtil.dbItemExec.execSQL(str, new Object[]{GSale.CashierNo, sheet_no});
            }
            else{
                str = "replace into check_master(sheet_no,check_no,shop_id,user_id,oper_date)value(?,?,'CR',?,?,datetime(CURRENT_TIMESTAMP,'localtime'))";
                DBUtil.dbItemExec.execSQL(str, new Object[]{sheet_no,batch_no,shop_id,GSale.CashierNo});
            }*/
        }catch (Exception e){
            throw e;
        }
    }

    public ArrayList<CheckHistoryItem> selectHistoryData()  throws Exception {
        ArrayList<CheckHistoryItem> lists = new ArrayList<>();
        try {
            String str = "select m.oper_date, m.sheet_no, m.check_no, count(1) count, sum(real_qty) qty " +
                    "from check_master m,check_detail d " +
                    "where m.sheet_no = d.sheet_no " +
                    "group by m.oper_date, m.sheet_no, m.check_no " +
                    "order by m.check_no desc";

            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, null);
            if(cursor.moveToFirst()){
                do{
                    CheckHistoryItem entity = new CheckHistoryItem();
                    entity.setCheck_no(cursor.getString(cursor.getColumnIndex("check_no")));
                    entity.setSheet_no(cursor.getString(cursor.getColumnIndex("sheet_no")));
                    entity.setCount(cursor.getFloat(cursor.getColumnIndex("count")));
                    entity.setQty(cursor.getFloat(cursor.getColumnIndex("qty")));
                    entity.setOper_date(cursor.getString(cursor.getColumnIndex("oper_date")));
                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();

            return lists;
        }catch (Exception ex){
            throw ex;
        }
    }
    /*
    public void GetServerQty(String sheetId, String posId) throws Exception {
        ArrayList<CheckInit> lists = new ArrayList<CheckInit>();
        final String nodeName = "QueryBatchDetail";
        final String[] paras = {sheetId, posId};


        BaseEntity transEntity = new BaseEntity();
        GConn.proxy.QueryFrmXml(GDevice.DeviceSn, nodeName, paras, transEntity);
        if(!transEntity.getResultCode().equals("ok")){
            throw new Exception(transEntity.getResultCode());
        }
        String ds = transEntity.getKey();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodeCollect = null;
        JsonNode node = null;
        nodeCollect = mapper.readTree(ds);
        int row = nodeCollect.size();
        if(row == 0)
        {
            Exception e = new Exception("服务器上不存在数据");
            throw e;
        }
        for (int i = 0; i < row; i++) {
            node = nodeCollect.get(i);
            String itemNo =  node.get("GOODSID").asText().trim();
            String placeNo = node.get("PLACENO").asText().trim();
            String serverQty = node.get("QTY").asText().trim();

            String str = "update check_detail set server_qty = ? where sheet_no = ? and item_no = ? ";
            DBUtil.dbItemExec.execSQL(str, new Object[]{serverQty,placeNo,itemNo});
        }

    }
*/
    public ArrayList<CheckDetail> GetHistoryData(String sheet_no){
        ArrayList<CheckDetail> lists = new ArrayList<>();
        try {
            String str = "select m.check_no, m.sheet_no, m.user_id, d.item_no, d.item_subno,d.item_name, d.oper_date, d.real_qty, d.com_flag, d.server_qty from check_master m, check_detail d where m.sheet_no = d.sheet_no and d.sheet_no = ?";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{sheet_no});
            if(cursor.moveToFirst()){
                do{
                    CheckDetail entity = new CheckDetail();
                    entity.setCheckNo(cursor.getString(cursor.getColumnIndex("check_no")));
                    entity.setSheetNo(cursor.getString(cursor.getColumnIndex("sheet_no")));
                    entity.setItemNo(cursor.getString(cursor.getColumnIndex("item_no")));
                    entity.setItemSubno(cursor.getString(cursor.getColumnIndex("item_subno")));
                    entity.setItemName(cursor.getString(cursor.getColumnIndex("item_name")));


                    entity.setRealQty(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("real_qty"))));
                    entity.setOperDate(cursor.getString(cursor.getColumnIndex("oper_date")));
                    entity.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
                    entity.setPhoneId(cursor.getInt(cursor.getColumnIndex("oper_date")));

                    entity.setServerQty(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("server_qty"))));
                    entity.setComFlag(cursor.getString(cursor.getColumnIndex("com_flag")));
                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception ex){
            throw ex;
        }
    }

    public ArrayList<CheckDetail> getUploadData(){
        ArrayList<CheckDetail> lists = new ArrayList<>();
        try{
            String str = "select m.check_no, m.sheet_no, m.user_id, d.item_no, d.item_subno,d.item_name, d.oper_date, d.real_qty, d.com_flag from check_master m, check_detail d where m.sheet_no = d.sheet_no and d.com_flag = '1'  ";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, null);
            if(cursor.moveToFirst()){
                do{
                    CheckDetail entity = new CheckDetail();
                    entity.setCheckNo(cursor.getString(cursor.getColumnIndex("check_no")));
                    entity.setSheetNo(cursor.getString(cursor.getColumnIndex("sheet_no")));
                    entity.setItemNo(cursor.getString(cursor.getColumnIndex("item_no")));
                    entity.setItemSubno(cursor.getString(cursor.getColumnIndex("item_subno")));
                    entity.setItemName(cursor.getString(cursor.getColumnIndex("item_name")));


                    entity.setRealQty(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("real_qty"))));
                    entity.setOperDate(cursor.getString(cursor.getColumnIndex("oper_date")));
                    entity.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
                    entity.setPhoneId(cursor.getInt(cursor.getColumnIndex("oper_date")));

                    //entity.setPlaceid(cursor.getString(cursor.getColumnIndex("placeid")));
                    //entity.(cursor.getString(cursor.getColumnIndex("flag")));
                    //entity.setPosid(GParam.PosID);
                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception ex){
            throw ex;
        }
    }

    public void updateLocalData(ArrayList<CheckDetail> lists){
        try {
            //DBUtil.dbItemExec.beginTransaction();
            for(CheckDetail item : lists){
                String str2 = "update check_detail set com_flag = '0' where sheet_no = ? and item_no = ? ";
                DBUtil.dbItemExec.execSQL(str2, new String[]{item.getSheetNo(), item.getItemNo()} );
            }
            // DBUtil.dbItemExec.setTransactionSuccessful();
        }catch (Exception ex){
            // DBUtil.dbItemExec.endTransaction();
            throw ex;
        }
    }

    public void updateLocalServerQty(ArrayList<CheckDetail> lists){
        try {
            //DBUtil.dbItemExec.beginTransaction();
            for(CheckDetail item : lists){
                String str2 = "update check_detail set server_qty = ? where sheet_no = ? and item_no = ? ";
                DBUtil.dbItemExec.execSQL(str2, new String[]{item.getServerQty().toString(), item.getSheetNo(), item.getItemNo()} );
            }
            // DBUtil.dbItemExec.setTransactionSuccessful();
        }catch (Exception ex){
            // DBUtil.dbItemExec.endTransaction();
            throw ex;
        }
    }

    public ArrayList<CheckDetail> getUploadOne(String itemNo, String placeNo)  throws Exception {
        ArrayList<CheckDetail> lists = new ArrayList<>();
        try {
            String str = "select m.check_no, m.sheet_no, m.user_id, d.item_no, d.item_subno,d.item_name, d.oper_date, d.real_qty, d.com_flag, d.server_qty from check_master m, check_detail d where m.sheet_no = d.sheet_no and d.sheet_no = ? " +
                    "and d.item_no = ? and d.sheet_no = ?";

            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{itemNo, placeNo});
            if(cursor.moveToFirst()){
                do{
                    CheckDetail entity = new CheckDetail();
                    entity.setCheckNo(cursor.getString(cursor.getColumnIndex("check_no")));
                    entity.setSheetNo(cursor.getString(cursor.getColumnIndex("sheet_no")));
                    entity.setItemNo(cursor.getString(cursor.getColumnIndex("item_no")));
                    entity.setItemSubno(cursor.getString(cursor.getColumnIndex("item_subno")));
                    entity.setItemName(cursor.getString(cursor.getColumnIndex("item_name")));


                    entity.setRealQty(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("real_qty"))));
                    entity.setOperDate(cursor.getString(cursor.getColumnIndex("oper_date")));
                    entity.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
                    entity.setPhoneId(cursor.getInt(cursor.getColumnIndex("oper_date")));
                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception ex){
            throw ex;
        }
    }

    /*
    public int Upload() throws Exception {
        ArrayList<t_upload_stock> lists = new ArrayList<>();
        try {
            String str = "select m.sheet_no placeno, m.check_no checkno,item_no goodsid,'' barcodeid,'' name,real_qty qty,'0' placeid,m.user_id operid,m.oper_date builddate,d.com_flag flag,'60' posid from check_master m,check_detail d where m.sheet_no = d.sheet_no and d.com_flag = '1'";

            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, null);
            if(cursor.moveToFirst()){
                do{
                    t_upload_stock entity = new t_upload_stock();
                    entity.setCheckno(cursor.getString(cursor.getColumnIndex("checkno")));

                    entity.setPlaceno(cursor.getString(cursor.getColumnIndex("placeno")));

                    entity.setGoodsid(cursor.getString(cursor.getColumnIndex("goodsid")));
                    entity.setBarcodeid(cursor.getString(cursor.getColumnIndex("barcodeid")));
                    entity.setName(cursor.getString(cursor.getColumnIndex("name")));
                    entity.setOrderQty(cursor.getString(cursor.getColumnIndex("qty")));

                    entity.setPlaceid(cursor.getString(cursor.getColumnIndex("placeid")));
                    entity.setOperid(cursor.getString(cursor.getColumnIndex("operid")));
                    entity.setBuilddate(cursor.getString(cursor.getColumnIndex("builddate")));
                    entity.setFlag(cursor.getString(cursor.getColumnIndex("flag")));
                    entity.setPosid(GParam.PosID);
                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            if(lists.size() == 0)
                return lists.size();
            str = objectToJson(lists);

            final String[] paras = {GSale.CashierNo, str};

            BaseEntity transEntity = new BaseEntity();
            //GConn.proxy.UploadStock(GDevice.DeviceSn,  paras, transEntity);
            GConn.proxy.StockFunc(GDevice.DeviceSn, "TallyUploadStocktakeInfo2", paras, transEntity);
            if(!transEntity.getResultCode().equals("ok")){
                throw new Exception(transEntity.getResultCode());
            }
            for(t_upload_stock item : lists){
                String str2 = "update check_detail set com_flag = '0' where sheet_no = ? and item_no = ? ";
                DBUtil.dbItemExec.execSQL(str2, new String[]{item.getPlaceno(), item.getGoodsid()} );
            }
            return lists.size();
        }catch (Exception ex){
            throw ex;
        }
    }*/
 /*

    public void NewBatch() throws Exception {


        final String[] paras = {GParam.BranchNo};
        BaseEntity transEntity = new BaseEntity();
        //GConn.proxy.NewBatch(GDevice.DeviceSn,paras, transEntity);
        GConn.proxy.StockFunc(GDevice.DeviceSn, "TallyNewBatch", paras, transEntity);
        if(!transEntity.getResultCode().equals("ok")){
            throw new Exception(transEntity.getResultCode());
        }
    }

    public void FinishBatch(String sheetNo) throws Exception {


        final String[] paras = {sheetNo};
        BaseEntity transEntity = new BaseEntity();
        //GConn.proxy.TallyFinishBatch(GDevice.DeviceSn,paras, transEntity);
        GConn.proxy.StockFunc(GDevice.DeviceSn, "TallyFinishBatch", paras, transEntity);
        if(!transEntity.getResultCode().equals("ok")){
            throw new Exception(transEntity.getResultCode());
        }
    }

    public String objectToJson(ArrayList<t_upload_stock> obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert object to JSON string
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(obj);


            return jsonStr;
        } catch (IOException e) {
            throw e;
        }
        //return JSONObject.fromObject(obj).toString();
    }*/
}
