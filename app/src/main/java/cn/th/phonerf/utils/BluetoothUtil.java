package cn.th.phonerf.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import cn.th.phonerf.model.BaseEntity;

public class BluetoothUtil {

    private static final UUID PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static String Innerprinter_Address = "";//"8C:DE:52:9A:AA:58";

    private static BluetoothSocket bluetoothSocket;

    public static String RetMsg = "";

    public static ArrayList<String> getBtDeviceList(){
        ArrayList<String> list = new ArrayList<String>();

        BluetoothAdapter bluetoothAdapter = getBTAdapter();

        BluetoothDevice innerprinter_device = null;
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : devices) {
            list.add(device.getAddress());
        }
        return list;
        //return innerprinter_device;
    }

    public static ArrayList<BaseEntity> getApapterList(){
        ArrayList<BaseEntity> list = new ArrayList<>();

        BluetoothAdapter bluetoothAdapter = getBTAdapter();

        BluetoothDevice innerprinter_device = null;
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : devices) {
            BaseEntity entity = new BaseEntity();
            entity.setKey(device.getAddress());
            entity.setValue(device.getName());
            list.add(entity);
        }
        return list;
        //return innerprinter_device;
    }

    private static BluetoothAdapter getBTAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    private static BluetoothDevice getDevice(BluetoothAdapter bluetoothAdapter) {
        BluetoothDevice innerprinter_device = null;
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : devices) {
            if (device.getAddress().equals(Innerprinter_Address))
            {
                innerprinter_device = device;
                break;
            }
        }
        return innerprinter_device;
    }

    private static BluetoothSocket getSocket(BluetoothDevice device) throws IOException {
        BluetoothSocket socket = null;
        socket = device.createRfcommSocketToServiceRecord(PRINTER_UUID);
        //socket.connect();
        try {
            socket.connect();
            Log.e("","Connected");
        } catch (IOException e) {
            Log.e("",e.getMessage());
            try {
                Log.e("","trying fallback...");

                socket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
                socket.connect();

                Log.e("","Connected");
            }
            catch (Exception e2) {
                //Log.e("", "Couldn't establish Bluetooth connection!");
                throw e;
            }
        }

        return  socket;
    }


    /**
     * 连接蓝牙
     *
     * @param context
     * @return
     */
    public static boolean connectBlueTooth(Context context) throws IOException {
        if (bluetoothSocket == null) {
            if (getBTAdapter() == null) {
                //Toast.makeText(context, "蓝牙设备不可用", Toast.LENGTH_SHORT).show();
                RetMsg = "蓝牙设备不可用";
                return false;
            }
            if (!getBTAdapter().isEnabled()) {
                //Toast.makeText(context, "未检测到蓝牙设备，请打开蓝牙", Toast.LENGTH_SHORT).show();
                RetMsg = "未检测到蓝牙设备，请打开蓝牙";
                return false;
            }
            BluetoothDevice device;
            if ((device = getDevice(getBTAdapter())) == null) {
                //Toast.makeText(context, "未发现InnterPrinter!", Toast.LENGTH_SHORT).show();
                //IOException e = new IOException("未发现InnterPrinter!");
                //throw e;
                RetMsg = "未发现设备";
                return false;
            }

            try {
                bluetoothSocket = getSocket(device);
            } catch (IOException e) {
                throw e;
                //Toast.makeText(context, "蓝牙连接失败!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                //return false;
            }
        }
        return true;
    }

    /**
     * 断开蓝牙
     */
    public static void disconnectBlueTooth(Context context) {
        if (bluetoothSocket != null) {
            try {
                OutputStream out = bluetoothSocket.getOutputStream();
                out.close();
                InputStream in = bluetoothSocket.getInputStream();
                in.close();
                bluetoothSocket.close();
                bluetoothSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean getStatus(){
        try {
            return bluetoothSocket.isConnected();
        }
        catch (Exception e){
            return false;
        }
    }

    public static OutputStream getOutputStream() throws IOException {
        return bluetoothSocket.getOutputStream();
    }

    public static InputStream getInputStream() throws IOException {
        return bluetoothSocket.getInputStream();
    }
    /**
     * 蓝牙方式打印均使用epson指令
     *
     * @param bytes
     * @throws IOException
     */
    public static void sendData(byte[] bytes) {
        if (bluetoothSocket != null) {
            OutputStream out = null;
            try {
                out = bluetoothSocket.getOutputStream();
                out.write(bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("kaltin", "bluetoothSocketttt null");
        }
    }

    public static byte[] getData(){
        byte[] bytes = new byte[1024];
        if(bluetoothSocket != null){
            InputStream in = null;
            try {
                in = bluetoothSocket.getInputStream();
                int r = 0;
                r = in.available();
                if(r>0)
                {
                    in.read(bytes);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.i("kaltin", "bluetoothSocketttt null");
        }
        return bytes;
    }
}
