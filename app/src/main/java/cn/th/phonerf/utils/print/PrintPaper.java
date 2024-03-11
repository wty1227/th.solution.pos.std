package cn.th.phonerf.utils.print;


import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.SaleBillDal;
import cn.th.phonerf.model.AccountCashierInfo;
import cn.th.phonerf.model.TRmPayflow;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.model.TRmSaleman;
import cn.th.phonerf.model.t_bd_payment_info;
import cn.th.phonerf.model.t_sys_printer_head;
import cn.th.phonerf.model.t_sys_printer_pay;
import cn.th.phonerf.utils.TextUtil;

public class PrintPaper {
    

    
    public String GetRealString(String strPrint) {
        if (TextUtil.isEmpty(strPrint)) {
            return "";
        }
        strPrint = strPrint.trim();
        int index = strPrint.indexOf("/1");
        if ((index >= 0) && ((index + 2) == strPrint.length())) {
            return strPrint.substring(0, index);
        }
        index = strPrint.indexOf("/2");
        if ((index >= 0) && ((index + 2) == strPrint.length())) {
            return strPrint.substring(0, index);
        }
        index = strPrint.indexOf("/3");
        if ((index >= 0) && ((index + 2) == strPrint.length())) {
            return strPrint.substring(0, index);
        }
        return strPrint;
    }

    public String GetStringFormat(String curString, String addStr, List<PrintString> listStr) {
        
        if (TextUtil.isEmpty(addStr)) {
            return curString;
        }
        if (TextUtil.isEmpty(curString)) {
            return addStr;
        }
        if (PrintInfo.prtLen < TextUtil.getByteCount(curString.trim() + " " + addStr))
        {
            listStr.add(new PrintString(curString));
            return addStr;
        }
        listStr.add(new PrintString(curString + TextUtil.padRight(" ",PrintInfo.prtLen - TextUtil.getByteCount(curString + " " + addStr), ' ') + addStr));
        return "";
    }

    public ContentAlignment GetTextAlign(String strPrint) {
        if (!TextUtil.isEmpty(strPrint)) {
            strPrint = strPrint.trim();
            int index = strPrint.indexOf("/1");
            if ((index >= 0) && ((index + 2) == strPrint.length())) {
                return ContentAlignment.MiddleLeft;
            }
            index = strPrint.indexOf("/2");
            if ((index >= 0) && ((index + 2) == strPrint.length())) {
                return ContentAlignment.MiddleCenter;
            }
            index = strPrint.indexOf("/3");
            if ((index >= 0) && ((index + 2) == strPrint.length())) {
                return ContentAlignment.MiddleRight;
            }
        }
        return ContentAlignment.MiddleLeft;
    }

    public List<PrintString> PrintAtOnceSaleRow(TRmSaleflow item, String strSaleMan, boolean isHzPrinter) {
        String str6;
        String str9;
        String str10;
        String str = "";
        String str3 = "";
        List<PrintString> list = new ArrayList<PrintString>();
        if (PrintInfo.IsPrtSalesMore && (strSaleMan != item.getSale_man().trim())) {
            strSaleMan = item.getSale_man().trim();
            str3 = item.getSale_name().trim();
            str = "营业员:";
            if (!isHzPrinter) {
                str = "Clerk:";
            }
            String str4 = str;
            if (PrintInfo.printSaleManNo) {
                str4 = str4 + strSaleMan + " ";
            }
            if (PrintInfo.printSaleManName) {
                str4 = str4 + str3;
            }
            if (str4 != "") {
                
                if (PrintInfo.prtLen < TextUtil.getByteCount(str4)) {
                    list.add(new PrintString(str4));
                } else {
                    
                    str4 = str4 + TextUtil.padRight(" ",PrintInfo.prtLen - TextUtil.getByteCount(str4), ' ');
                    list.add(new PrintString(str4));
                }
            }
        }
        String stringEx = "";
        String strReplace = "[赠]";
        if (!isHzPrinter) {
            strReplace = "[G]";
        }
        String s = PrintString.PrintStrAlign(item.getSale_qnty().toString(), PrintInfo.qtyLen, ContentAlignment.MiddleRight);
        String str5 = item.getSale_qnty().toString();
        if (item.getSell_way() == "B") {
            str5 = String.valueOf (-Math.abs(item.getSale_qnty()));
        }
        str5 = PrintString.PrintStrAlign(str5, PrintInfo.qtyLen, ContentAlignment.MiddleRight);
        if ((item.getSale_price() != item.getSource_price()) && PrintInfo.isPromotionFlag) {
            str6 = PrintString.PrintStrAlign("*" + item.getSale_price().toString().trim(), PrintInfo.prcLen, ContentAlignment.MiddleRight);
        } else {
            str6 = PrintString.PrintStrAlign(item.getSale_price().toString(), PrintInfo.prcLen, ContentAlignment.MiddleRight);
        }
        str = "原价:";
        if (!isHzPrinter) {
            str = "Cost:";
        }
        String str7 = str + item.getSource_price().toString();
        if (PrintInfo.prtRowDicount) {
            str = " 折率:";
            if (!isHzPrinter) {
                str = "  Agio:";
            }
            if (item.getSale_price() != item.getSource_price()) {
                str7 = str7 + str + (((item.getSale_price() * 100 ) /item.getSource_price()))  + "%";
            }
        }
        String str8 = item.getSale_money().toString();
        if (item.getSell_way() == "B") {
            str8 = (-Math.abs(item.getSale_money())) + "";
        }
        str8 = PrintString.PrintStrAlign(str8, PrintInfo.cntLen, ContentAlignment.MiddleRight);
        if (PrintInfo.prtItemSubNo) {
            str9 = item.getItem_subno();
        } else {
            str9 = item.getItem_no();
        }
        str9 = PrintString.PrintStrAlign(str9, PrintInfo.itemLen, ContentAlignment.MiddleLeft);
        if (PrintInfo.prtItemSubName) {
            str10 = item.getItem_name();
        } else {
            str10 = item.getItem_name();
        }
        str10 = PrintString.PrintStrAlign(str10, PrintInfo.itemLen, ContentAlignment.MiddleLeft);
        switch (PrintInfo.prtItemType) {
            case "1": {
                if (PrintInfo.prtCnLineFlag) {
                    s = str10.trim();
                    if (PrintInfo.prtLen < TextUtil.getByteCount(s)) {
                        s = TextUtil.GetString(s, PrintInfo.prtLen);
                    }
                    if (PrintInfo.prtGifItemFlag && (item.getSale_price() == 0d))
                    {
                        if ((TextUtil.getByteCount(s + strReplace) + 4) > PrintInfo.prtLen) {
                            s = TextUtil.ReplaceEndChar(s, strReplace);
                        } else {
                            s = s + strReplace;
                        }
                    }
                    list.add(new PrintString(s));
                    if ((item.getSource_price() != item.getSale_price()) && PrintInfo.prtSource) {
                        if (str7.length() <= PrintInfo.itemLen) {
                            s = PrintString.PrintStrAlign(str7, PrintInfo.itemLen, ContentAlignment.MiddleLeft) + str6 + str5 + str8;
                        } else {
                            list.add(new PrintString(str7));
                            s = TextUtil.padRight("", PrintInfo.itemLen, ' ') + str6 + str5 + str8;
                        }
                    } else {
                        s = TextUtil.padRight("", PrintInfo.itemLen, ' ') + str6 + str5 + str8;
                    }
                    list.add(new PrintString(s));
                    return list;
                }
                int num = TextUtil.getByteCount(str10);
                String str13 = str10;
                stringEx = TextUtil.GetStringEx(str13, num - PrintInfo.itemLen);
                str10 = TextUtil.GetString(str10, PrintInfo.itemLen);
                if (PrintInfo.prtGifItemFlag && (item.getSale_price() == 0d))
                {
                    str10 = TextUtil.ReplaceEndChar(str10, strReplace);
                }
                if (PrintInfo.prtLen >= (((PrintInfo.itemLen + PrintInfo.qtyLen) + PrintInfo.prcLen) + PrintInfo.cntLen)) {
                    s = PrintString.PrintStrAlign(str10, PrintInfo.itemLen, ContentAlignment.MiddleLeft) + str6 + str5 + str8;
                    if (!TextUtil.isEmpty(stringEx) && PrintInfo.autoNewLine) {
                        list.add(new PrintString(s));
                        s = stringEx;
                    }
                } else {
                    s = str6 + str5 + str8;
                    list.add(new PrintString(s));
                    s = TextUtil.GetString(str13, PrintInfo.prtLen);
                }
                list.add(new PrintString(s));
                if ((item.getSource_price() != item.getSale_price()) && PrintInfo.prtSource) {
                    if (PrintInfo.prtLen >= (((PrintInfo.itemLen + PrintInfo.qtyLen) + PrintInfo.prcLen) + PrintInfo.cntLen)) {
                        s = PrintString.PrintStrAlign(str7, (PrintInfo.itemLen + PrintInfo.qtyLen) + PrintInfo.prcLen, ContentAlignment.MiddleRight);
                    } else {
                        s = PrintString.PrintStrAlign(str7, PrintInfo.qtyLen + PrintInfo.prcLen, ContentAlignment.MiddleRight);
                    }
                    list.add(new PrintString(s));
                }
                return list;
            }
            case "2":
                if (PrintInfo.prtGifItemFlag) {
                    if (item.getSale_price() == 0d)
                    {
                        if ((TextUtil.getByteCount(str9 + strReplace) + 4) > PrintInfo.prtLen) {
                            str9 = TextUtil.ReplaceEndChar(str9, strReplace);
                        } else {
                            str9 = str9 + strReplace;
                        }
                    }
                    if (PrintInfo.itemLen < TextUtil.getByteCount(s)) {
                        str9 = PrintString.PrintStrAlign(str9, PrintInfo.itemLen, ContentAlignment.MiddleLeft);
                    }
                }
                if (PrintInfo.prtLen >= (((PrintInfo.itemLen + PrintInfo.prcLen) + PrintInfo.qtyLen) + PrintInfo.cntLen)) {
                    s = str9 + str6 + str5 + str8;
                } else {
                    s = str6 + str5 + str8;
                    list.add(new PrintString(s));
                    s = str9;
                }
                list.add(new PrintString(s));
                if ((item.getSale_price() != item.getSource_price()) && PrintInfo.prtSource) {
                    if (PrintInfo.prtLen >= (((PrintInfo.itemLen + PrintInfo.prcLen) + PrintInfo.qtyLen) + PrintInfo.cntLen)) {
                        s = PrintString.PrintStrAlign(str7, (PrintInfo.itemLen + PrintInfo.prcLen) + PrintInfo.qtyLen, ContentAlignment.MiddleRight);
                    } else {
                        s = PrintString.PrintStrAlign(str7, PrintInfo.prcLen + PrintInfo.qtyLen, ContentAlignment.MiddleRight);
                    }
                    list.add(new PrintString(s));
                }
                return list;
        }
        if (PrintInfo.prtCnLineFlag) {
            s = PrintString.PrintStrAlign(str9.trim() + "/" + str10.trim(), PrintInfo.itemLen, ContentAlignment.MiddleLeft);
            if (PrintInfo.prtLen < TextUtil.getByteCount(s)) {
                s = TextUtil.GetString(s, PrintInfo.prtLen);
            }
            if (PrintInfo.prtGifItemFlag && (item.getSale_price() == 0d))
            {
                if ((TextUtil.getByteCount(s + strReplace) + 4) > PrintInfo.prtLen) {
                    s = TextUtil.ReplaceEndChar(s, strReplace);
                } else {
                    s = s + strReplace;
                }
            }
            list.add(new PrintString(s));
            if ((item.getSource_price() != item.getSale_price()) && PrintInfo.prtSource) {
                if (str7.length() <= PrintInfo.itemLen) {
                    s = PrintString.PrintStrAlign(str7, PrintInfo.itemLen, ContentAlignment.MiddleLeft) + str6 + str5 + str8;
                } else {
                    list.add(new PrintString(str7));
                    s = TextUtil.padRight("", PrintInfo.itemLen, ' ') + str6 + str5 + str8;
                }
            } else {
                s = TextUtil.padRight("", PrintInfo.itemLen, ' ') + str6 + str5 + str8;
            }
            list.add(new PrintString(s));
            return list;
        }
        if (PrintInfo.prtLen >= (((PrintInfo.itemLen + PrintInfo.qtyLen) + PrintInfo.prcLen) + PrintInfo.cntLen)) {
            s = str9 + str6 + str5 + str8;
        } else {
            s = str6 + str5 + str8;
            list.add(new PrintString(s));
            s = str9;
        }
        list.add(new PrintString(s));
        int byteCount = TextUtil.getByteCount(str10);
        String str14 = str10;
        if (PrintInfo.prtLen < byteCount) {
            str10 = TextUtil.GetString(str10, PrintInfo.prtLen);
            stringEx = TextUtil.GetStringEx(str14, byteCount - PrintInfo.prtLen);
        }
        if (PrintInfo.prtGifItemFlag && (item.getSale_price() == 0d))
        {
            str10 = TextUtil.ReplaceEndChar(str10, strReplace);
        }
        if ((item.getSource_price() != item.getSale_price()) && PrintInfo.prtSource) {
            if (((str10.length() + str7.length()) + 1) > PrintInfo.prtLen) {
                list.add(new PrintString(str10));
                s = str7;
            } else {
                s = str10 + " " + str7;
            }
        } else {
            s = str10;
        }
        s = TextUtil.GetString(s, PrintInfo.prtLen);
        list.add(new PrintString(s));
        if (!TextUtil.isEmpty(stringEx) && PrintInfo.autoNewLine) {
            list.add(new PrintString(stringEx));
        }
        return list;
    }

    public List<PrintString> PrintDetail(List<TRmSaleflow> listDetail, boolean isHzPrinter) {
        List<PrintString> list = new ArrayList<PrintString>();
        if (listDetail.size() > 0) {
            String strSaleMan = "";
            for(TRmSaleflow sale : listDetail)
            {
                for(PrintString str2 : this.PrintAtOnceSaleRow(sale, strSaleMan, isHzPrinter))
                {
                    list.add(str2);
                }
                strSaleMan = sale.getSale_man().trim();
            }


            list.add( new PrintString(TextUtil.padRight("",PrintInfo.prtLen < 0 ? 0 : PrintInfo.prtLen, '-'))  );
        }
        return list;
    }

    public List<PrintString> PrintDetailTitle(boolean isHzPrinter) {
        String str = "";
        String str2 = "";
        List<PrintString> list = new ArrayList<>();
        list.add(new PrintString(TextUtil.padRight("", PrintInfo.prtLen, '-')));
        String prtItemType = PrintInfo.prtItemType;
        if (prtItemType != null) {
            if (!(prtItemType == "1")) {
                if (prtItemType == "2") {
                    str2 = "货号";
                    if (!isHzPrinter) {
                        str2 = "ItemNo";
                    }

                }
                else
                if (prtItemType == "3") {
                    PrintInfo.prtItemSubName = true;
                    str2 = "货号/品名";
                    if (!isHzPrinter) {
                        str2 = "ItemNo/Name";
                    }
                }
            } else {
                str2 = "品名";
                if (!isHzPrinter) {
                    str2 = "Name";
                }
                PrintInfo.prtItemSubName = true;
            }
        }
        /*
        PrintInfo.prtItemSubName = false;
        str2 = "货号/品名";
        if (!isHzPrinter) {
            str2 = "ItemNo/Name";
        }*/
        str2 = PrintString.PrintStrAlign(str2, PrintInfo.itemLen + 4, ContentAlignment.MiddleLeft);
        if (PrintInfo.prtLen < (((PrintInfo.qtyLen + PrintInfo.prcLen) + PrintInfo.cntLen) + PrintInfo.itemLen)) {
            list.add(new PrintString(str2));
            str2 = "";
        }
        str = "单价";
        if (!isHzPrinter) {
            str = "Price";
        }
        str2 = str2 + "  " +PrintString.PrintStrAlign(str, PrintInfo.prcLen, ContentAlignment.MiddleRight);
        str = "数量";
        if (!isHzPrinter) {
            str = "Qty";
        }
        str2 = str2 + "  " +PrintString.PrintStrAlign(str, PrintInfo.qtyLen, ContentAlignment.MiddleCenter);
        str = "小计";
        if (!isHzPrinter) {
            str = "Total";
        }
        str2 = str2 + "  " +PrintString.PrintStrAlign(str, PrintInfo.cntLen, ContentAlignment.MiddleRight);
        list.add(new PrintString(str2));
        return list;
    }

    public List<PrintString> PrintFooter(boolean isHzPrinter) {
        List<PrintString> list = new ArrayList<PrintString>();
        String realString = this.GetRealString(PrintInfo.footer);
        if (!TextUtil.isEmpty(realString)) {
            list.add(new PrintString(realString, PrintInfo.prtLen, this.GetTextAlign(PrintInfo.footer)));
        }
        String str2 = this.GetRealString(PrintInfo.footer2);
        if (!TextUtil.isEmpty(str2)) {
            list.add(new PrintString(str2, PrintInfo.prtLen, this.GetTextAlign(PrintInfo.footer2)));
        }
        String str3 = this.GetRealString(PrintInfo.footer3);
        if (!TextUtil.isEmpty(str3)) {
            list.add(new PrintString(str3, PrintInfo.prtLen, this.GetTextAlign(PrintInfo.footer3)));
        }
        String str4 = this.GetRealString(PrintInfo.footer4);
        if (!TextUtil.isEmpty(str4)) {
            list.add(new PrintString(str4, PrintInfo.prtLen, this.GetTextAlign(PrintInfo.footer4)));
        }
        String str5 = this.GetRealString(PrintInfo.footer5);
        if (!TextUtil.isEmpty(str5)) {
            list.add(new PrintString(str5, PrintInfo.prtLen, this.GetTextAlign(PrintInfo.footer5)));
        }
        String str6 = this.GetRealString(PrintInfo.footer6);
        if (!TextUtil.isEmpty(str6)) {
            list.add(new PrintString(str6, PrintInfo.prtLen, this.GetTextAlign(PrintInfo.footer6)));
        }
        if (PrintInfo.prtSISS) {
            if (!isHzPrinter) {
                list.add(new PrintString("商航MyShop商业管理系统", PrintInfo.prtLen, ContentAlignment.MiddleCenter));
            } else {
                list.add(new PrintString("商航MyShop商业管理系统", PrintInfo.prtLen, ContentAlignment.MiddleCenter));
            }
        }
        if (!TextUtil.isEmpty(PrintInfo.prtSoftTel)) {
            if (!isHzPrinter) {
                list.add(new PrintString("service call: " + PrintInfo.prtSoftTel, PrintInfo.prtLen, ContentAlignment.MiddleCenter));
                return list;
            }
            list.add(new PrintString("本地软件服务商电话: " + PrintInfo.prtSoftTel, PrintInfo.prtLen, ContentAlignment.MiddleCenter));
        }
        return list;
    }

    public List<PrintString> PrintFooterEmptyLine() {
        List<PrintString> list = new ArrayList<>();
        for (int i = 0; i < PrintInfo.footEmptyLine; i++) {
            list.add(new PrintString("\r\n"));
        }
        /*if ((this._myPrter.IsWindowPrinter && (PrintInfo.footEmptyLine > 0)) && (list.Count > 0)) {
            list[list.Count - 1].PrtSrt = ".";
        }*/
        return list;
    }

    public List<PrintString> PrintHeadEmptyLine() {
        List<PrintString> list = new ArrayList<>();
        for (int i = 0; i < PrintInfo.headEmptyLine; i++) {
            list.add(new PrintString("\r\n"));
        }
        return list;
    }

    public List<PrintString> PrintHeader(boolean isPrintTitle, t_sys_printer_head headModel, boolean isHzPrinter, int pcType) {
        String str9 = "";
        String leftStr = "";
        String rightStr = "";
        String str12 = "";
        String str13 = "";
        String str14 = "";
        String str15 = "";
        List<PrintString> listPrtStr = null;
//        String str5 = headModel.branch_no.trim();
//        String str6 = headModel.branch_name.trim();
        String str7 = headModel.store_no.trim();
        String str8 = headModel.store_name.trim();
        String str = headModel.cashier_no.trim();
        String str2 = headModel.cashier_name.trim();
        String str3 = headModel.sale_man.trim();
        String str4 = headModel.sale_name.trim();
        str9 = headModel.flow_no.trim();
        String str16 = headModel.rdmNo.trim();
        String time = headModel.oper_date;
        if (isPrintTitle) {
            listPrtStr = this.PrintTitle(pcType);
        } else {
            listPrtStr = new ArrayList<PrintString>();
        }



        if (headModel.headFlagStr != "") {
            listPrtStr.add(0, new PrintString(headModel.headFlagStr));
            this.PrintHeaderDetail(listPrtStr, leftStr, rightStr);
        }



        /*if (PrintInfo.printBranchNo || PrintInfo.printBranchName) {
            leftStr = "服务区:";
            if (!isHzPrinter) {
                leftStr = "Branch:";
            }
            if (PrintInfo.printBranchNo) {
                leftStr = leftStr + str5 + " ";
            }
            if (PrintInfo.printBranchName) {
                leftStr = leftStr + str6;
            }
        }*/
        if (PrintInfo.printStoreNo || PrintInfo.printStoreName) {
            rightStr = "仓库:";
            if (!isHzPrinter) {
                rightStr = "Warehouse:";
            }
            if (PrintInfo.printStoreNo) {
                rightStr = rightStr + str7 + " ";
            }
            if (PrintInfo.printStoreName) {
                rightStr = rightStr + str8;
            }
        }
        this.PrintHeaderDetail(listPrtStr, leftStr, rightStr);
        if (!PrintInfo.IsPrtSalesMore && (PrintInfo.printSaleManNo || PrintInfo.printSaleManName)) {
            str12 = "营业员:";
            if (!isHzPrinter) {
                str12 = "Clerk:";
            }
            if (PrintInfo.printSaleManNo) {
                str12 = str12 + str3 + " ";
            }
            if (PrintInfo.printSaleManName) {
                str12 = str12 + str4;
            }
        }
        if (PrintInfo.printOperNo || PrintInfo.printOperName) {
            str13 = "管理员:";
            if (!isHzPrinter) {
                str13 = "Cashier:";
            }
            if (PrintInfo.printOperNo) {
                str13 = str13 + str + " ";
            }
            if (PrintInfo.printOperName) {
                str13 = str13 + str2;
            }
        }
        //this.PrintHeaderDetail(listPrtStr, str12, str13);
        if (PrintInfo.printSheetNo) {
            if (!TextUtil.isEmpty(str16) && PrintInfo.PrtRandomNum) {
                str14 = "NO." + str16;
            } else {
                str14 = "NO." + str9;
            }
        }
        if (PrintInfo.printTime) {
            if (PrintInfo.printTimeSecond) {
                str15 = time;// DateUtil.dateToString(time, "yyyy.MM.dd HH:mm:ss");
            } else {
                str15 = time;//DateUtil.dateToString(time, "yyyy.MM.dd HH:mm");
            }
        }
        this.PrintHeaderDetail(listPrtStr, str14, str15);
        return listPrtStr;
    }

    public boolean PrintHeaderDetail(List<PrintString> listPrtStr, String leftStr, String rightStr) {
        try {
            leftStr = leftStr.trim();
            rightStr = rightStr.trim();
            if ((leftStr != "") && (rightStr != "")) {
                if (PrintInfo.prtLen > (leftStr + rightStr).getBytes("utf-8").length) {
                    listPrtStr.add(new PrintString(leftStr +
                            TextUtil.padRight("", PrintInfo.prtLen - TextUtil.getByteCount (leftStr + rightStr), ' ') + rightStr));
                } else {
                    listPrtStr.add(new PrintString(leftStr));
                    listPrtStr.add(new PrintString(rightStr));
                }
            } else if (leftStr != "") {
                listPrtStr.add(new PrintString(leftStr));
            } else if (rightStr != "") {
                listPrtStr.add(new PrintString(rightStr));
            }
            return true;
        }catch (Exception ex){
            return false;
        }

    }

    public List<PrintString> PrintPayInfo(List<TRmPayflow> listPayFlow, t_sys_printer_pay chgItem, t_sys_printer_pay printPay,
                                          boolean isHzPrinter) {
        String str = "";
        List<PrintString> listPrtStr = new ArrayList<PrintString>();
        Double num = 0d;
        Double num2 = 0d;
        Double num3 = 0d;
        Double num4 = 0d;
        String leftStr = "";
        String rightStr = "";
        String str4 = "";
        String str5 = "";
        if (printPay == null) {
            return new ArrayList<PrintString>();
        }
        num = printPay.total_amt;
        num2 = printPay.total_qty;
        num3 = printPay.dis_amt;
        num4 = printPay.source_amt;

        str = "数量:";
        if (!isHzPrinter) {
            str = "Qty    :";
        }
        leftStr = str + num2;
        //ToDo 打印件数
        str = "件数: ";
        if (!isHzPrinter) {
            str = "Qty    :";
        }
        leftStr = str + printPay.total_packages;
        //End
        str = "合计: ";
        if (!isHzPrinter) {
            str = "Total    :";
        }
        rightStr = str + num;
        this.PrintHeaderDetail(listPrtStr, leftStr, rightStr);
        str = "优惠金额:";
        if (!isHzPrinter) {
            str = "Preferential:";
        }
        if (PrintInfo.prtDisAmt) {
            str4 = str + num3;
        }
        str = "原价金额:";
        if (!isHzPrinter) {
            str = "Original amount:";
        }
        if (PrintInfo.prtNoSpec) {
            str5 = str + num4;
        }
        this.PrintHeaderDetail(listPrtStr, str4, str5);
        String str6 = "";
        switch (printPay.pos_sale_way) {
            case 1:
                str6 = "退款:";
                if (!isHzPrinter) {
                    str6 = "Refund:";
                }
                break;

            default:
                str6 = "付款:";
                if (!isHzPrinter) {
                    str6 = "Pay:";
                }
                break;
        }
        str6 = PrintString.PrintStrAlign(str6, PrintInfo.infoLen, ContentAlignment.MiddleLeft);
        String str7 = "";
        String str8 = "";
        int byteCount = 0;
        String str9 = "";
        int num6 = 0;
        for (int i = 0; i < listPayFlow.size(); i++) {
            TRmPayflow pay = listPayFlow.get(i) ;
            if ((PrintInfo.posPrtChange || (pay.getPayWay() != "CHG")) && (pay.getPayWay() != "SHC")) {
                str7 = pay.getPayName().trim();
                if (!isHzPrinter) {
                    str7 = pay.getPayWay().trim();
                }
                str7 = PrintString.PrintStrAlign(str7, PrintInfo.payWayLen, ContentAlignment.MiddleLeft);
                if ((chgItem != null) && (pay.chg_amt != 0))
                {
                    if ((pay.getPayWay() == "SHP") && (pay.getPayAmount() < 0d))
                    {
                        str8 = String.valueOf (-(Math.abs(pay.getPayAmount()) + Math.abs(pay.chg_amt)));
                    }
                        else
                    {
                        str8 = (pay.getPayAmount() + chgItem.pay_amount) + "";
                    }
                }
                    else
                {
                    str8 = pay.getPayAmount() + "";
                }
                str8 = PrintString.PrintStrAlign(str8, PrintInfo.payAmtLen, ContentAlignment.MiddleRight);
                if (num6 != 0) {
                    str6 = "";
                    str6 = PrintString.PrintStrAlign(str6, PrintInfo.infoLen, ContentAlignment.MiddleLeft);
                }
                byteCount = TextUtil.getByteCount (str6 + str7 + str8);
                if (PrintInfo.prtLen > byteCount) {
                    str9 = str6 + str7 + TextUtil.padRight("", PrintInfo.prtLen + 2 - byteCount, ' ') + str8;
                } else if (PrintInfo.prtLen == byteCount) {
                    str9 = str6 + str7 + str8;
                } else {
                    str9 = str6 + str7 + str8;
                }
                if (str9.trim() != "") {
                    listPrtStr.add(new PrintString(str9));
                    listPrtStr.add(new PrintString("支付单号:" + pay.getRemark()));
                }
                /*
                if (pay.pay_way.ToUpper() == "SAV") {
                    if (PrintInfo.PrtSavCardNo) {
                        str = "卡  号:";
                        if (!isHzPrinter) {
                            str = "Card NO:";
                        }
                        str9 = str + pay.card_no.trim();
                        listPrtStr.Add(new PrintString(str9));
                    }
                    if (PrintInfo.PrtSavMan) {
                        str = "持卡人:";
                        if (!isHzPrinter) {
                            str = "Card holder:";
                        }
                        str9 = str + pay.vip_name.trim();
                        listPrtStr.Add(new PrintString(str9));
                    }
                    if (PrintInfo.PrtSavRemainAmt) {
                        str = "余  额:";
                        if (!isHzPrinter) {
                            str = "Balance:";
                        }
                        str9 = str + pay.remain_amt.ToString(PrintInfo.PrtTotalFormat);
                        listPrtStr.Add(new PrintString(str9));
                    }
                    if (PrintInfo.PrtVipSavBranchName) {
                        str = "服务区名称:";
                        if (!isHzPrinter) {
                            str = "Branch name:";
                        }
                        str9 = str + pay.branch_no);
                        listPrtStr.Add(new PrintString(str9));
                    }

                }*/
                num6++;
            }
        }

        if (printPay.vip_sale) {
            String curString = "";
            String strPrt = "";
            if (PrintInfo.prtVipNo) {
                str = "会员卡号:";
                if (!isHzPrinter) {
                    str = "Card NO:";
                }
                if (PrintInfo.isVipNoVisible) {
                    strPrt = str + printPay.vip_card_no;
                } else {
                    strPrt = TextUtil.padLeft(str, printPay.vip_card_no.length() + str.length(), '*');
                }
                if (printPay.vip_address.trim()  == "重打印") {
                    listPrtStr.add(new PrintString(strPrt));
                } else {
                    curString = this.GetStringFormat(curString, strPrt, listPrtStr);
                }
            }
            if (!(printPay.vip_address.trim() != "重打印")) {
                return listPrtStr;
            }
            if (PrintInfo.prtVipName) {
                str = "会员姓名:";
                if (!isHzPrinter) {
                    str = "VIP Name:";
                }
                strPrt = str + printPay.vip_name;
                curString = this.GetStringFormat(curString, strPrt, listPrtStr);
            }
            if (printPay.acc_flag == "1") {
                if (PrintInfo.prtVipCurPoints) {
                    str = "本次积分:";
                    if (!isHzPrinter) {
                        str = "The integral:";
                    }
                    strPrt = str + printPay.vip_cur_point;
                    curString = this.GetStringFormat(curString, strPrt, listPrtStr);
                }
                if (PrintInfo.prtVipConsumePoints) {
                    str = "剩余积分:";
                    if (!isHzPrinter) {
                        str = "Remaining points:";
                    }
                    strPrt = str + printPay.vip_remain_point;
                    curString = this.GetStringFormat(curString, strPrt, listPrtStr);
                }
            }


            if (PrintInfo.prtVipAddress) {
                str = "通讯地址:";
                if (!isHzPrinter) {
                    str = "Address:";
                }
                strPrt = str + printPay.vip_address;
                curString = this.GetStringFormat(curString, strPrt, listPrtStr);
            }
            if (PrintInfo.prtVipTel) {
                str = "会员电话:";
                if (!isHzPrinter) {
                    str = "TEL:";
                }
                strPrt = str + printPay.vip_mobile;
                curString = this.GetStringFormat(curString, strPrt, listPrtStr);
            }
            if (PrintInfo.prtVipMemo) {
                str = "会员备注:";
                if (!isHzPrinter) {
                    str = "Remarks:";
                }
                strPrt = str + printPay.vip_memo;
                curString = this.GetStringFormat(curString, strPrt, listPrtStr);
            }
            if ((strPrt != "") && (strPrt == curString)) {
                curString = this.GetStringFormat(curString, " ", listPrtStr);
            }
        }
        return listPrtStr;
    }

    public List<PrintString> PrintTitle(int pcType) {
        List<PrintString> list = new ArrayList<PrintString>();
        if (!TextUtil.isEmpty(PrintInfo.title)) {
            list.add(new PrintString(PrintInfo.title, PrintInfo.prtLen, false, true, ContentAlignment.MiddleCenter, PrintInfo.PrtBigFont, pcType));
        }
        String realString = this.GetRealString(PrintInfo.titleAlpha1);
        if (!TextUtil.isEmpty(realString)) {
            list.add(new PrintString(realString, PrintInfo.prtLen, this.GetTextAlign(PrintInfo.titleAlpha1)));
        }
        String str2 = this.GetRealString(PrintInfo.titleAlpha2);
        if (!TextUtil.isEmpty(str2)) {
            list.add(new PrintString(str2, PrintInfo.prtLen, this.GetTextAlign(PrintInfo.titleAlpha2)));
        }
        String str3 = this.GetRealString(PrintInfo.titleAlpha3);
        if (!TextUtil.isEmpty(str3)) {
            list.add(new PrintString(str3, PrintInfo.prtLen, this.GetTextAlign(PrintInfo.titleAlpha3)));
        }
        String str4 = this.GetRealString(PrintInfo.titleAlpha4);
        if (!TextUtil.isEmpty(str4)) {
            list.add(new PrintString(str4, PrintInfo.prtLen, this.GetTextAlign(PrintInfo.titleAlpha4)));
        }
        if (PrintInfo.isStudyFlag && PrintInfo.isPrintStudy) {
            list.add(new PrintString("工作状态：练习！"));
        }
        return list;
    }

    public List<String> PrintAccount(AccountCashierInfo entity, boolean isPrintTitle, t_sys_printer_head headModel, boolean isHzPrinter, int pcType) {
        String str3 = "";
        Integer num = 0;
        Double num5 = 0d;
        Double num3 = 0d;
        Double num4 = 0d;
        Double num6 = 0d;
        Double num7 = 0d;
        Integer result = 0;


        ArrayList<String> list = new ArrayList<>();
        list.add( PrintString.PrintStrAlign("   收银对帐单   ", PrintInfo.prtLen, ContentAlignment.MiddleCenter) );
        list.add( PrintString.PrintStrAlign("=================================", PrintInfo.prtLen, ContentAlignment.MiddleCenter) );

        //str = "";
        //list.add(str.PadLeft((intPrintLen < 0) ? 0 : intPrintLen, '-'));
        list.add("  机    构: " + headModel.branch_no.trim());
        list.add("  仓    库: " + headModel.branch_name.trim());
        list.add("  收银机号: " + GSale.PosId);
        list.add("  收 银 员: [" + headModel.cashier_no + "]" + headModel.cashier_name);
        list.add("  对账日期: " + headModel.oper_date);
        list.add("  首    笔: " + entity.getMinDate());
        list.add("  末    笔: " + entity.getMaxDate());
        list.add("  笔    数: " + entity.getMyCount().toString());


        list.add( PrintString.PrintStrAlign("-------------管理员汇总数据-----------------", PrintInfo.prtLen, ContentAlignment.MiddleCenter) );
        StringBuilder builder = new StringBuilder();
        //builder.appendFormat("'{0}'", "RMB");
        builder.append(String.format(",'%s'", "RMB"));
        SaleBillDal saleBillDal = new SaleBillDal();

        String sellWay = "";
        ArrayList<t_bd_payment_info> paymentLists = saleBillDal.getAllPayWayInfo();
        for (t_bd_payment_info item:paymentLists
             ) {
            for (int i = 1; i <= 5; i++)
            {
                switch (i)
                {
                    case 1:
                        sellWay = "A";
                        str3 = "销售";
                        num = 1;
                        break;

                    case 2:
                        sellWay = "B";
                        str3 = "退货";
                        num = -1;
                        break;

                    case 3:
                        sellWay = "C";
                        str3 = "赠送";
                        num = 0;
                        break;
                    case 4:
                        sellWay = "D";
                        str3 = "找零";
                        num = -1;
                        break;
                    case 5:
                        sellWay = "G";
                        str3 = "报损";
                        num = 0;
                        break;
                }

                AccountCashierInfo table5 = saleBillDal.getPayWayAmount2(headModel.branch_no.trim(), headModel.cashier_no, item.getPayWay(), sellWay, entity.getMinDate(), entity.getMaxDate(), "%");
                if (table5 != null)
                {

                    num5 = table5.getAmount();
                    result = table5.getMyCount();
                    if ((result > 0) || (num5 > 0d))
                    {
                        list.add("  " + item.getPayName() + "-" + str3);
                        list.add("  --笔数: " + PrintString.PrintStrAlign(String.valueOf(result), 6, ContentAlignment.MiddleLeft) + "--金额: " + num5.toString());
                        switch (num)
                        {
                            case 1:
                                num6 += num5;
                                num3 += result;
                                break;

                            case -1:
                                num7 += num5;
                                num4 += result;
                                break;
                        }
                    }
                }
            }
            if (item.getPayFlag().equals("1"))
            {
                builder.append(String.format(",'%s'", item.getPayWay()));
            }


        }
        if ((num3 > 0) || (num4 > 0))
        {
            list.add("");
            list.add("  收支合计  笔数: " + ((num3 + num4))  + "  **");
            if (num4 > 0)
            {
                list.add(PrintString.PrintStrAlign("  --支出笔数: " + num4, 0x10, ContentAlignment.MiddleLeft) + "  支出金额: " + num7);
            }
            if (num3 > 0)
            {
                list.add(PrintString.PrintStrAlign("  --收入笔数: " + num3, 0x10, ContentAlignment.MiddleLeft) + "  收入金额: " + num6);
            }
            list.add("  --合计金额: " +  ((num7 + num6)));
            list.add("");

            //decimal num9 = GConn.LocalDal.GetPayWayAmountForRMB(branchNo, cashierId, builder.ToString(), dtFrom, dtTo);
            //list.Add("  人民币金额: " + (isPrinAmt ? "***" : num9.ToString(formatMoney)));
        }

        //查询所有营业员的数据
        list.add( PrintString.PrintStrAlign("-------------营业员汇总数据-----------------", PrintInfo.prtLen, ContentAlignment.MiddleCenter) );

        str3 = "";
         num = 0;
         num5 = 0d;
         num3 = 0d;
         num4 = 0d;
         num6 = 0d;
         num7 = 0d;
         result = 0;
        builder = new StringBuilder();
        //builder.appendFormat("'{0}'", "RMB");
        builder.append(String.format(",'%s'", "RMB"));
        //SaleBillDal saleBillDal = new SaleBillDal();

        sellWay = "";
        //ArrayList<t_bd_payment_info> paymentLists = saleBillDal.getAllPayWayInfo();
        ArrayList<TRmSaleman> salemanArrayList = saleBillDal.selectSaleman(headModel.branch_no.trim(), headModel.cashier_no,  entity.getMinDate(), entity.getMaxDate());
        for (TRmSaleman saleman: salemanArrayList
             ) {

            list.add("营业员：" + saleman.getSaleId());
            for (t_bd_payment_info item:paymentLists
            ) {
                for (int i = 1; i <= 5; i++)
                {
                    switch (i)
                    {
                        case 1:
                            sellWay = "A";
                            str3 = "销售";
                            num = 1;
                            break;

                        case 2:
                            sellWay = "B";
                            str3 = "退货";
                            num = -1;
                            break;

                        case 3:
                            sellWay = "C";
                            str3 = "赠送";
                            num = 0;
                            break;
                        case 4:
                            sellWay = "D";
                            str3 = "找零";
                            num = -1;
                            break;
                        case 5:
                            sellWay = "G";
                            str3 = "报损";
                            num = 0;
                            break;
                    }

                    AccountCashierInfo table5 = saleBillDal.getPayWayAmount2(headModel.branch_no.trim(), headModel.cashier_no, item.getPayWay(), sellWay, entity.getMinDate(), entity.getMaxDate(), saleman.getSaleId());
                    if (table5 != null)
                    {

                        num5 = table5.getAmount();
                        result = table5.getMyCount();
                        if ((result > 0) || (num5 > 0d))
                        {
                            list.add("  " + item.getPayName() + "-" + str3);
                            list.add("  --笔数: " + PrintString.PrintStrAlign(String.valueOf(result), 6, ContentAlignment.MiddleLeft) + "--金额: " + num5.toString());
                            switch (num)
                            {
                                case 1:
                                    num6 += num5;
                                    num3 += result;
                                    break;

                                case -1:
                                    num7 += num5;
                                    num4 += result;
                                    break;
                            }
                        }
                    }
                }
                if (item.getPayFlag().equals("1"))
                {
                    builder.append(String.format(",'%s'", item.getPayWay()));
                }


            }
            if ((num3 > 0) || (num4 > 0))
            {
                list.add("");
                list.add("  收支合计  笔数: " + ((num3 + num4))  + "  **");
                if (num4 > 0)
                {
                    list.add(PrintString.PrintStrAlign("  --支出笔数: " + num4, 0x10, ContentAlignment.MiddleLeft) + "  支出金额: " + num7);
                }
                if (num3 > 0)
                {
                    list.add(PrintString.PrintStrAlign("  --收入笔数: " + num3, 0x10, ContentAlignment.MiddleLeft) + "  收入金额: " + num6);
                }
                list.add("  --合计金额: " +  ((num7 + num6)));
                list.add("");

                //decimal num9 = GConn.LocalDal.GetPayWayAmountForRMB(branchNo, cashierId, builder.ToString(), dtFrom, dtTo);
                //list.Add("  人民币金额: " + (isPrinAmt ? "***" : num9.ToString(formatMoney)));
            }
        }

        return list;
    }
}


