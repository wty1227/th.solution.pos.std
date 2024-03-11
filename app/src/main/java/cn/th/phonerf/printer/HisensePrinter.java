package cn.th.phonerf.printer;

import android.os.Bundle;
import android.os.RemoteException;

import com.histonepos.npsdk.bind.Const;
import com.histonepos.npsdk.bind.PrinterConsts;
import com.histonepos.npsdk.printer.IPrinterService;

import java.util.List;

import cn.th.phonerf.constant.GPrinter;
import cn.th.phonerf.constant.GlobalDefines;
import cn.th.phonerf.utils.print.PrintString;

public class HisensePrinter implements IBasePrinter {



    public IPrinterService printer = null;
    @Override
    public void initPrinter() throws Exception {
        printer = getPrinter();
        if (printer == null) {
            //Toast.makeText(getActivity(), "get printer failed!", Toast.LENGTH_SHORT).show();
            throw new Exception("获取打印机失败!");
            //return;
        }
        // tell sdk the widthmode(58mm or 80mm) of printer in use.
        Bundle bundle = new Bundle();
        String widthType = PrinterConsts.PRT_CFG_VALUE_WIDTH_MODE_80;
        bundle.putString(PrinterConsts.PRT_CFG_KEY_WIDTH_MODE, widthType);

        printer.setConfig(bundle);

        /*try{
            printer = getPrinter();
            if (printer == null) {
                //Toast.makeText(getActivity(), "get printer failed!", Toast.LENGTH_SHORT).show();
                throw new Exception("获取打印机失败!");
                //return;
            }
            // tell sdk the widthmode(58mm or 80mm) of printer in use.
            Bundle bundle = new Bundle();
            String widthType = PrinterConsts.PRT_CFG_VALUE_WIDTH_MODE_80;
            bundle.putString(PrinterConsts.PRT_CFG_KEY_WIDTH_MODE, widthType);
            try {
                printer.setConfig(bundle);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch (Exception e){
            throw e;
        }*/
    }

    @Override
    public boolean getPrinterReady() {
        return printer != null;
    }


    private IPrinterService getPrinter() {
        try {
            if(GlobalDefines.sm != null)
                return IPrinterService.Stub.asInterface(GlobalDefines.sm.getService(Const.PRINTER));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void printContext(List<PrintString> listPrtStr) throws RemoteException {
        if(printer == null)
            return;
        //GPrinter.printer.openCashBox();
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
    }
}
