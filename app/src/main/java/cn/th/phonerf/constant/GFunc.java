package cn.th.phonerf.constant;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.th.phonerf.utils.DateUtil;
import cn.th.phonerf.utils.SharePreferenceUtils;
import cn.th.phonerf.utils.TextUtil;
import cn.th.phonerf.utils.print.PrintString;

public class GFunc {

    public static String GetFlowNo(Context context) throws Exception {
        Boolean flag = false;
        String str4 = "";
        int num = 1;
        try
        {
            String str = SharePreferenceUtils.getValue(context, SharePreferenceUtils.FLOW_NO, "");
            if (str != null)
            {
                String str3 =  str.substring(0, 10);
                num = Integer.valueOf (str.substring(12));
                if ((num > 0) && (num <= 0x270f))
                {
                    if (!str3.equals(DateUtil.getCurrentDate()))
                    {
                        flag = true;
                    }
                    if (num != GSale.FlowNo)
                    {
                        str4 = String.format("获得流水号。从文件得到的流水号不匹配系统FlowNo变量！\n 文件值：%d\n系统值：%d", num,  GSale.FlowNo);
                    }
                }
                else
                {
                    str4 = "从文件得到的流水号不在[0-9999]！";
                }
            }
            else
            {
                str4 = "获取流水号失败，没有读取到数据。";
            }

        }
        catch (Exception exception)
        {
            str4 = "打开流水号文件失败。" + exception.getMessage();
        }
        finally
        {
        }
        if (str4.length() > 0)
        {
            throw new Exception("获得流水号失败！详细信息如下:\r\n" + str4);
        }
        if (flag)
        {
            setFlowNo(context);
        }
        return (GSale.PosId + DateUtil.getCurrentDateTime("yyyyMMdd") + TextUtil.padRight(String.valueOf(GSale.FlowNo), 4, '0'));
    }

    public static boolean setFlowNo(Context context) throws Exception {
        int num = GSale.FlowNo + 1;
        try
        {
            if (num > 0x270f)
            {
                num = 1;
            }
            String str = SharePreferenceUtils.getValue(context, SharePreferenceUtils.FLOW_NO, "");
            if (!str.isEmpty())
            {

                Date time = DateUtil.stringToDate(str.substring(0,10));

                if (DateUtil.getDayByMinusDate(time, new Date()) > 0)
                {
                    num = 1;
                }
            }
        }
        catch (Exception exception)
        {
            throw new Exception("处理流水号文件失败！\r\n" + exception.getMessage());
        }
        finally
        {

        }
        GSale.FlowNo = num;
        try
        {
            SharePreferenceUtils.setValue(context, SharePreferenceUtils.FLOW_NO, DateUtil.getCurrentDate() + "  " + num);
        }
        catch (Exception exception2)
        {
            throw new Exception("处理流水号文件失败！\r\n" + exception2.getMessage());
        }
        finally
        {
        }
        return true;
    }

    public static List<PrintString> AddListPrt(List<PrintString> listPrtStr, List<PrintString> listPrtAdd)
    {
        if (listPrtStr == null)
        {
            listPrtStr = new ArrayList<PrintString>();
        }
        if (listPrtAdd != null)
        {
            for (PrintString str : listPrtAdd)
            {
                listPrtStr.add(str);
            }
        }
        return listPrtStr;
    }
}
