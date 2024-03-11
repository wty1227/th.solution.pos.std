package cn.th.phonerf.dal;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.model.PrintDesignDetail;
import cn.th.phonerf.model.PrintDesignMaster;
import cn.th.phonerf.utils.DBUtil;

public class PrintDesignDal {

    public void insertInfo(List<PrintDesignMaster> list){
        for (PrintDesignMaster master:list
             ) {
            String sql = "delete from print_design_master";
            DBUtil.dbItemExec.execSQL(sql);
            sql = "delete from print_design_detail";
            DBUtil.dbItemExec.execSQL(sql);
            //写入主表
            String sqlMasterInsert = "replace into print_design_master(print_id,print_name,merchant_id,build_date,oper_date)values(?,?,?,?,?)";
            DBUtil.dbItemExec.execSQL(sqlMasterInsert, new Object[]{master.getPrintId(), master.getPrintName(), master.getMerchantId(), master.getBuildDate(), master.getOperDate()});

            //写入子表
            for(PrintDesignDetail detail:master.getDetailList()){
                String sqlDetailInsert = "insert into print_design_detail(flow_id,print_id,title_id,title_name,print_title,x,y,font,xmulip,ymulip,rotation,bold_flag,display_flag)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                DBUtil.dbItemExec.execSQL(sqlDetailInsert,
                        new Object[]{detail.getFlowId(), detail.getPrintId(), detail.getTitleId(), detail.getTitleName(), detail.getPrintTitle(),
                        detail.getX(), detail.getY(), detail.getFont(), detail.getXmulip(), detail.getYmulip(), detail.getRotation(), detail.getBoldFlag(), detail.getDisplayFlag()
                    });
            }

        }
    }

    public ArrayList<PrintDesignMaster> selectMaster(Integer merchantId){
        ArrayList<PrintDesignMaster> list = new ArrayList<>();
        try {
            String str = "select * from print_design_master where merchant_id = ?";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{merchantId.toString()});
            if(cursor.moveToFirst()){
                do{
                    PrintDesignMaster entity = new PrintDesignMaster();
                    entity.setPrintId(cursor.getInt(cursor.getColumnIndex("print_id")));
                    entity.setPrintName(cursor.getString(cursor.getColumnIndex("print_name")));
                    entity.setMerchantId(cursor.getInt(cursor.getColumnIndex("merchant_id")));
                    list.add(entity);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return list;
        }catch (Exception ex){
            throw ex;
        }
    }

    public ArrayList<PrintDesignDetail> selectDetail(Integer printId){
        ArrayList<PrintDesignDetail> list = new ArrayList<>();
        try {
            String str = "select * from print_design_detail where print_id = ?";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{printId.toString()});
            if(cursor.moveToFirst()){
                do{
                    PrintDesignDetail entity = new PrintDesignDetail();
                    entity.setFlowId(cursor.getInt(cursor.getColumnIndex("flow_id")));
                    entity.setPrintId(cursor.getInt(cursor.getColumnIndex("print_id")));
                    entity.setTitleId(cursor.getInt(cursor.getColumnIndex("title_id")));
                    entity.setTitleName(cursor.getString(cursor.getColumnIndex("title_name")));
                    entity.setPrintTitle(cursor.getString(cursor.getColumnIndex("print_title")));
                    entity.setX(cursor.getInt(cursor.getColumnIndex("x")));
                    entity.setY(cursor.getInt(cursor.getColumnIndex("y")));
                    entity.setFont(cursor.getInt(cursor.getColumnIndex("font")));
                    entity.setSize(cursor.getInt(cursor.getColumnIndex("size")));
                    entity.setXmulip(cursor.getInt(cursor.getColumnIndex("xmulip")));
                    entity.setYmulip(cursor.getInt(cursor.getColumnIndex("xmulip")));
                    entity.setRotation(cursor.getString(cursor.getColumnIndex("rotation")));
                    entity.setBoldFlag(cursor.getString(cursor.getColumnIndex("bold_flag")));
                    entity.setDisplayFlag(cursor.getString(cursor.getColumnIndex("display_flag")));
                    list.add(entity);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return list;
        }catch (Exception ex){
            throw ex;
        }
    }
}
/*
flow_id int not null auto_increment primary key comment 'id',
	print_id int not null comment 'master_id',
	title_id int not null comment '标题类型', --  0. 品牌 1.
	title_name varchar(100) not null comment '',
	print_title char(1) not null default '1' comment '是否打印标题内容',
	x int not null comment '坐标',
	y int not null comment '坐标',
	font int not null comment '字体',
	xmulip int not null default 1 comment 'x倍宽',
	ymulip int not null default 1 comment 'y倍宽',
	rotation char(1) not null default '' comment '旋转角度',
	bold_flag char(1) not null default '0' comment '加粗',
	display_flag char(1) not null default '1' comment '是否打印'
 */
