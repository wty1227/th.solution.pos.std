package cn.th.phonerf.utils.device;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class HmUtil {
    private OutputStream OutStream = null;
    private InputStream InStream = null;


    public HmUtil(OutputStream os, InputStream is){
        this.OutStream = os;
        this.InStream = is;
    }

    public String getSN(){
        byte[] msgBuffer = {29, 73, 68};
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);


                byte[] buf = new byte[100];
                this.InStream.read(buf);
                String var0 = new String(buf, 0, getValidLength(buf));
                return !var0.contains("_") ? "" : var0.substring(var0.lastIndexOf("_") + 1, var0.length() - 1);

            } catch (IOException var15) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public int getValidLength(byte[] bytes){
        int i = 0;
        if (null == bytes || 0 == bytes.length)
            return i ;
        for (; i < bytes.length; i++) {
            if (bytes[i] == '\0')
                break;
        }
        return i + 1;
    }

    public String setup(int width, int height, int speed, int density, int sensor, int sensor_distance, int sensor_offset) throws UnsupportedEncodingException{
        String message = "";
        String size = 200 + " " + 200 + " " + (height + 10) + " 1";
        /*String speed_value = "SPEED " + speed;
        String density_value = "DENSITY " + density;
        String sensor_value = "";
        if (sensor == 0) {
            sensor_value = "GAP " + sensor_distance + " mm" + ", " + sensor_offset + " mm";
        } else if (sensor == 1) {
            sensor_value = "BLINE " + sensor_distance + " mm" + ", " + sensor_offset + " mm";
        }*/

        message = "! 0 " + size + "\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var15) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String clearbuffer() throws UnsupportedEncodingException {
        String message = "CLS\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var4) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String sendcommand(String message) throws UnsupportedEncodingException {
        byte[] msgBuffer = message.getBytes("GB2312");

        try {
            this.OutStream.write(msgBuffer);
            return "1";
        } catch (IOException var4) {
            return "-1";
        }
    }

    public String barcodeText(int font, int size, int offset) throws UnsupportedEncodingException {
        String message = "BARCODE-TEXT " + font + " " + size + " " + offset + "\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var22) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String barcodeTextOff() throws UnsupportedEncodingException {
        String message = "BARCODE-TEXT OFF" + "\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var22) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String barcode(int x, int y, String type, int height, int rotation, int wide, String string) throws UnsupportedEncodingException {
        String message = "";
        String barcode = "BARCODE ";
        String mode = type + " ";
        String wide_value = wide + " ";
        String rota = rotation + " ";
        String height_value = height + " ";
        String position = x + " " + y + " ";
        //String human_value = human_readable;
        //String narrow_value = narrow;

        String string_value = string ;
        message = barcode + mode +  wide_value + rota + height_value +position + string_value + "\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");

        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var22) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String printerfont(int font , int size,  int x, int y, String rotation, int x_multiplication, int y_multiplication, String string) throws UnsupportedEncodingException {

        String message = "";
        String text = "TEXT" + rotation + " ";
        String font_value = font + " ";
        String size_value = size + " ";
        String position = x + " " + y + " ";
        String string_value =  string ;
        message = text + font_value + size_value + position +  string_value + "\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");

        //String msg = new String(msgBuffer);
        //Log.d(TAG, "printerfont: ");
        //Log.i(TAG, "msg:" + msg);

        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                //Thread.sleep((long)1000);
                return "1";
            } catch (IOException var18) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String box(int x, int y, int x1, int y1, int width) throws UnsupportedEncodingException {
        String message = "BOX " + x + " " + y + " " + x1 + " " + y1 + " " + width + "\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var6) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String line(int x, int y, int x1, int y1, int width) throws UnsupportedEncodingException {
        String message = "LINE " + x + " " + y + " " + x1 + " " + y1 + " " + width + "\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var6) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String align(String align) throws UnsupportedEncodingException{
        String message = "";
        message = align + "\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var6) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String setBold(String bold) throws UnsupportedEncodingException{
        String message = "";
        message = "!U1 SETBOLD " +  bold + "\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var6) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String printForm() throws UnsupportedEncodingException{
        String message = "";
        message = "FORM\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var6) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public String printlabel() throws UnsupportedEncodingException{
        String message = "";
        message = "PRINT" + "\r\n";
        byte[] msgBuffer = message.getBytes("GB2312");
        if (this.OutStream != null && this.InStream != null) {
            try {
                this.OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var6) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }
}
