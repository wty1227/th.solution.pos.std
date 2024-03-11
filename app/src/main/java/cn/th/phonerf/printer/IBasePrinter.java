package cn.th.phonerf.printer;

import android.os.RemoteException;

import java.util.List;

import cn.th.phonerf.utils.print.PrintString;

public interface IBasePrinter {
    public static String printName = "none";

    public void initPrinter() throws Exception;

   /* public void beginTranscation();

    public void setTextBold();

    public void addTextSizeDouble();

    public void addText();

    public void cancelTextBold;

    */
    public boolean getPrinterReady();
    public void printContext(List<PrintString> listPrtStr) throws RemoteException;
}
