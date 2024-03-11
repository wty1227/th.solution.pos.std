package cn.th.phonerf.constant;

import cn.th.phonerf.printer.IBasePrinter;

public class GPrinter {

    public static String printName = "none";

    public static IBasePrinter basePrinter = null;

    /*
    public static IPrinterService printer = null;

    public static void initPrinter() throws Exception {
        try{
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
        }
    }

    private static IPrinterService getPrinter() {
        try {
            if(GlobalDefines.sm != null)
                return IPrinterService.Stub.asInterface(GlobalDefines.sm.getService(Const.PRINTER));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
