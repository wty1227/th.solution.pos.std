package cn.th.phonerf.dal;

import android.app.Application;

import cn.th.phonerf.utils.DBItemHelper;
import cn.th.phonerf.utils.DBSaleHelper;
import cn.th.phonerf.utils.DBUtil;

public class BaseDal extends Application {

    public void BaseDal(){
        if(DBUtil.dbItemHelper == null) {
            DBUtil.dbItemHelper = new DBItemHelper(this);
            DBUtil.dbItemExec = DBUtil.dbItemHelper.getWritableDatabase();
        }
        /*if(DBUtil.dbSaleHelper == null) {
            DBUtil.dbSaleHelper = new DBSaleHelper(this);
            DBUtil.dbSaleExec = DBUtil.dbSaleHelper.getWritableDatabase();
        }*/
    }



}