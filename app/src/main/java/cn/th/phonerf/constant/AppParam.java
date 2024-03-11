package cn.th.phonerf.constant;

import java.util.ArrayList;

import cn.th.phonerf.model.MerchantShop;
import cn.th.phonerf.model.UserGrant;

public class AppParam {
    public static String appId = "";
    public static String appKey = "";
    public static Integer MerchantId = 0;
    public static String MerchantNo = "";


    public static MerchantShop MShop;
    public static String BtAddr = "";
    public static String QuerySql = "";
    //public static String PosId = "";
    public static String PhoneSn = "";
    public static String phone = "";


    public static Integer UId = 0;

    /*public static String BranchNo = "";



    public static String OperId = "";
    public static String OperName = "";*/

    public static String BranchName = "";

    public static ArrayList<UserGrant> Grants = new ArrayList<UserGrant>();
    //public static int[] Grants = new int[] { 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127 };
    public static Boolean checkPower(int userPurview, int optPurview)
    {
        int purviewValue = (int)Math.pow(2, optPurview);
        Boolean result = ((userPurview & purviewValue) == purviewValue);


        //MsgBox.Show("没有权限！请向管理员申请。");
        return result;
    }


}
