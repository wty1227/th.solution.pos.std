package cn.th.phonerf.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.EditText;

import cn.th.phonerf.utils.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Info 文本检测
 * @Auth Bello
 * @Time 16-6-24 上午10:17
 * @Ver
 */
public class TextUtil {

    /**
     * 判断字符是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(@NonNull CharSequence str) {

        if (null == str) {
            return true;
        }

        if ((str.toString().trim().length()) <= 0) {
            return true;
        }

        if (str.toString().trim().toLowerCase().equals("null")) {
            return true;
        }

        return false;
    }

    /**
     * 空值返回
     *
     * @param str
     * @return
     */
    public static String getStr(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

    public static Integer getByteCount(String str)  {
        try {
            return str.getBytes("utf-8").length;
        }catch (Exception ex){
            return 0;
        }

    }

    public static String GetString(String str, int len)
    {
        try {
            if (!(str.isEmpty()) && (len > 0)) {
                return GetString(toStringArray(str), len);
            }
            return "";
        }catch (Exception ex){
            //throw ex;
            return "";
        }
    }


    public static String GetString(String[] strArray, int len) throws UnsupportedEncodingException {
        int num = 0;
        StringBuilder builder = new StringBuilder();
        int byteCount = 1;
        for (int i = 0; i < strArray.length; i++)
        {
            byteCount = getByteCount(strArray[i].toString());
            num += byteCount;
            if (num > len)
            {
                break;
            }
            builder.append(strArray[i]);
        }
        if (builder.length() > 0)
        {
            return builder.toString();
        }
        return "";
    }
    public static String GetStringEx(String str, int len)
    {
        if (!str.isEmpty() && (len > 0))
        {
            return GetStringEx(toStringArray(str), len);
        }
        return "";
    }
    public static String GetStringEx(String[] strArray, int len)
    {
        int num = 0;
        StringBuilder builder = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        int byteCount = 1;
        for (int i = strArray.length - 1; i >= 0; i--)
        {
            byteCount = getByteCount(strArray[i].toString());
            num += byteCount;
            if (num > len)
            {
                break;
            }
            builder.append(strArray[i]);
        }
        if (builder.length() <= 0)
        {
            return "";
        }
        String[] strArray2 = toStringArray(builder.toString());
        for (int j = strArray2.length - 1; j >= 0; j--)
        {
            builder2.append(strArray2[j]);
        }
        return builder2.toString();
    }


    public static String[] toStringArray(String str)
    {
        String[] strArray = new String[str.length()];
        for (int i = 0; i < str.length(); i++)
        {
            strArray[i] = str.substring(i, 1);
        }
        return strArray;
    }

    public static String ReplaceEndChar(String str, String strReplace)
    {
        try
        {
            if (str == null)
            {
                return "";
            }
            if (strReplace.isEmpty())
            {
                return str;
            }
            int byteCount = getByteCount(strReplace);
            if (byteCount <= 0)
            {
                return str;
            }
            String[] strArray = toStringArray(str);
            if (strArray.length == 0)
            {
                return "";
            }
            if (getByteCount(str) > byteCount)
            {
                return (GetString(strArray, getByteCount(str) - byteCount) + strReplace);
            }
        }
        catch(Exception ex)
        {

        }
        return str;
    }

    /**
     * 删除字符串包含的空格
     *
     * @param str
     * @return
     */
    public static String removeSpace(String str) {
        return str.trim().replace(" ", "");
    }


    /**
     * 检查手机号码格式
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 校验密码强壮度： 8-11位
     *
     * @param pass
     * @return
     */
    public static boolean checkPwdStrong(String pass) {
        Pattern p;
        Matcher m;
        boolean b;
//        p = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$).{8,11}$"); // 验证规则
        p = Pattern.compile("^[a-zA-Z0-9_]{8,11}$"); // 验证规则
        m = p.matcher(pass);
        b = m.matches();
        return b;
    }

    /**
     * 隐藏手机号 130****0000
     *
     * @param mobile
     * @return
     */
    public static String hideMobile(String mobile) {
        if (mobile.length() != 11) {
            return "";
        } else {
            return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1 **** $2");
        }
    }

    /**
     * 隐藏手机号 130****0000
     *
     * @param id
     * @return
     */
    public static String hideID(String id) {
        if (id.length() != 18) {
            return "";
        } else {
            return id.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1 **** $2");
        }
    }

    /**
     * 取手机号后四位
     *
     * @param mobile
     * @return
     */
    public static String getMobileLastNum(String mobile) {
        String num = "";
        if (mobile.length() > 4) {
            num = mobile.substring(mobile.length() - 4, mobile.length());
        }

        return num;
    }

    /**
     * EditText 格式化手机号码显示 (130 0000 0000)
     *
     * @param editText
     * @param s
     */
    public static void formatMobile(EditText editText, CharSequence s, int start, int before) {
        if (s == null || s.length() == 0)
            return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if (!sb.toString().equals(s.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == ' ') {
                if (before == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (before == 1) {
                    index--;
                }
            }
            editText.setText(sb.toString());
            editText.setSelection(index);
        }
    }


    /**
     * 取不带区号的ETC卡号(16位)
     *
     * @param etcNo
     * @return
     */
    public static String getShortETCNo(String etcNo) {
        try {
            if (etcNo != null && etcNo.length() == 20) {
                String s = etcNo.substring(etcNo.length() - 16, etcNo.length());
                return s;
            } else {
                return etcNo;
            }
         } catch (Exception e) {
            e.printStackTrace();
            return etcNo;
        }

    }

    /**
     * 取ETC卡号后四位
     *
     * @param etcNo
     * @return
     */
    public static String getLastETCNo(String etcNo) {

        try {
            if (null != etcNo && etcNo.length()>4) {
                return etcNo.substring(etcNo.length() - 4, etcNo.length());
            } else {
                return etcNo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return etcNo;
    }

    /**
     * 格式化ETC卡号
     *
     * @param etcNo
     * @return
     */
    public static String formatEtcNo(String etcNo) {
        if (etcNo != null && etcNo.length() > 4) {
            StringBuilder sb = new StringBuilder(etcNo);
            int len = sb.length();
            int per = len / 4;
            int per2 = len % 4;
            if (per2 == 0) {
                for (int i = 1; i <= per; i++) {
                    sb.insert(len - 4 * i, " ");
                }
            } else {
                for (int i = 0; i < per; i++) {
                    sb.insert(len - per2 - 4 * i, " ");
                }
            }

            return sb.toString();
        } else {
            Logger.e("格式化ETC卡号异常");
            return etcNo;
        }
    }


    /**
     * 格式化余额(分转元)
     */
    public static String formatFenToYuan(String balance) {
        if (null == balance) {
            return null;
        }

        try {
            BigDecimal b = new BigDecimal(balance);
            BigDecimal c = b.divide(new BigDecimal(100));
            //保留2位小数
            return c.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 金额格式化保留后两位(四舍五入)
     */
    public static String formatMoney2Dot(String balance) {
        try {
            BigDecimal b = new BigDecimal(balance);
            //保留2位小数
            return b.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return balance;
        }
    }


    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }


    /**
     * 格式化银行卡号 （0000 0000 0000 0000 0000 000）
     *
     * @param bankCard
     * @return
     */
    public static String formatBankCard(String bankCard) {
        StringBuilder sb = new StringBuilder(bankCard);
        int length = sb.length() / 4 + sb.length();
        for (int i = 0; i < length; i++) {
            if (i % 5 == 0) {
                sb.insert(i, " ");
            }
        }

        return sb.toString().substring(1, sb.length());
    }

    /**
     * 隐藏银行卡号
     *
     * @param bankCard
     * @return
     */
    public static String hideBankCardNo(String bankCard){
        if (TextUtil.isEmpty(bankCard)){
            return null;
        }

        String num = formatBankCard(bankCard);
        String result = "";
        String[] numArray = num.split(" ");
        for (int i=0; i<numArray.length; i++){
            if (i == 0 || i == numArray.length-1){
                result += numArray[i]+" ";
            } else {
                result += "**** ";
            }
        }

        return result;
    }

    /**
     * 校验身份证合法性
     *
     * @param certificateNo
     */
    public static boolean CheckCertificateNo(String certificateNo) {
        if (certificateNo.length() == 15) {
            System.out.println("身份证号码无效，请使用第二代身份证！！！");
            return false;
        } else if (certificateNo.length() == 18) {
            String address = certificateNo.substring(0, 6);//6位，地区代码
            String birthday = certificateNo.substring(6, 14);//8位，出生日期
            String sequenceCode = certificateNo.substring(14, 17);//3位，顺序码：奇为男，偶为女
            String checkCode = certificateNo.substring(17);//1位，校验码：检验位

            System.out.println("身份证号码:" + certificateNo + "、地区代码:" + address + "、出生日期:" + birthday + "、顺序码:" + sequenceCode + "、校验码:" +
                    checkCode + "\n");
            String[] provinceArray = {"11:北京", "12:天津", "13:河北", "14:山西", "15:内蒙古", "21:辽宁", "22:吉林", "23:黑龙江", "31:上海", "32:江苏",
                    "33:浙江", "34:安徽", "35:福建", "36:江西", "37:山东", "41:河南", "42:湖北 ", "43:湖南", "44:广东", "45:广西", "46:海南", "50:重庆", "51:四川",
                    "52:贵州", "53:云南", "54:西藏 ", "61:陕西", "62:甘肃", "63:青海", "64:宁夏", "65:新疆", "71:台湾", "81:香港", "82:澳门", "91:国外"};
            boolean valideAddress = false;
            for (int i = 0; i < provinceArray.length; i++) {
                String provinceKey = provinceArray[i].split(":")[0];
                if (provinceKey.equals(address.substring(0, 2))) {
                    valideAddress = true;
                }
            }

            if (valideAddress) {
                String year = birthday.substring(0, 4);
                String month = birthday.substring(4, 6);
                String day = birthday.substring(6);
                Date tempDate = new Date(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                if ((tempDate.getYear() != Integer.parseInt(year) || tempDate.getMonth() != Integer.parseInt(month) - 1 || tempDate
                        .getDate() != Integer.parseInt(day))) {//避免千年虫问题
                    System.out.println("身份证号码无效，请重新输入！！！");
                    return false;
                } else {
                    int[] weightedFactors = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};//加权因子
                    int[] valideCode = {1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};//身份证验证位值，其中10代表X
                    int sum = 0;//声明加权求和变量
                    String[] certificateNoArray = new String[certificateNo.length()];
                    for (int i = 0; i < certificateNo.length(); i++) {
                        certificateNoArray[i] = String.valueOf(certificateNo.charAt(i));
                    }

                    if ("x".equals(certificateNoArray[17].toLowerCase())) {
                        certificateNoArray[17] = "10";//将最后位为x的验证码替换为10
                    }

                    for (int i = 0; i < 17; i++) {
                        sum += weightedFactors[i] * Integer.parseInt(certificateNoArray[i]);//加权求和
                    }

                    int valCodePosition = sum % 11;//得到验证码所在位置
                    if (Integer.parseInt(certificateNoArray[17]) == valideCode[valCodePosition]) {
                        String sex = "男";
                        if (Integer.parseInt(sequenceCode) % 2 == 0) {
                            sex = "女";
                        }

                        System.out.println("身份证号码有效，性别为：" + sex + "！");
                        return true;
                    } else {
                        System.out.println("身份证号码无效，请重新输入！！！");
                        return false;
                    }
                }
            } else {
                System.out.println("身份证号码无效，请重新输入！！！");
                return false;
            }
        } else {
            System.out.println("非身份证号码，请输入身份证号码！！！");
            return false;
        }
    }


    /**
     * 一个字符串含有多少字母
     *
     * @param str
     * @return
     */
    public static int abcCount(String str) {
        if (isEmpty(str)) {
            return 0;
        }

        int count = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] >= 65 && chars[i] <= 90) || (chars[i] >= 97 && chars[i] <= 122)) {
                count++;
            }
        }

        return count;

    }


    /**
     * 处理SDK返回的MSG信息
     * [xxx]aaa code: xx --> aaa
     *
     * @param msg
     * @return
     */
    public static String formatMsg(String msg) {
        try {
            if (isEmpty(msg))
                return msg;

            if (msg.contains("length=")) {
                msg = "卡感应异常，请拔卡重试";
            }

            if (msg.contains("]")) {
                if (!msg.contains("code:") && msg.indexOf("]") == msg.length() - 1) {
                    return null;
                }
                if (msg.contains("code:")) {
                    if (isEmpty(msg.split("]")[1].split("code:")[0])) {
                        return "读卡失败";
                    } else {
                        return msg.split("]")[1].split("code:")[0];
                    }
                }
                return msg.split("]")[1];
            }

            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 半角转全角
     *
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * 全角转半角
     *
     * @param input String.
     * @return 半角字符串
     */
    public static String ToDBC(String input) {

        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);

        return returnString;
    }



    /**
     * 获取拆分的时间数组
     * @return
     */
    public static String[] getTimeArray(Context mContext){
        String[] result = new String[6];

        try {
            String time = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.TIME, "");
            if (isEmpty(time)){
                //为空时取当前时间
                Calendar now = Calendar.getInstance();
                result[0] = now.get(Calendar.YEAR) + "";
                result[1] = now.get(Calendar.MONTH) + 1 + "";
                result[2] = now.get(Calendar.DAY_OF_MONTH) + "";
                result[3] = now.get(Calendar.HOUR_OF_DAY) + "";
                result[4] = now.get(Calendar.MINUTE) + "";
                result[5] = now.get(Calendar.SECOND) + "";

            } else {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(time);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                result[0] = calendar.get(Calendar.YEAR) + "";
                result[1] = calendar.get(Calendar.MONTH) + 1 + "";
                result[2] = calendar.get(Calendar.DAY_OF_MONTH) + "";
                result[3] = calendar.get(Calendar.HOUR_OF_DAY) + "";
                result[4] = calendar.get(Calendar.MINUTE) + "";
                result[5] = calendar.get(Calendar.SECOND) + "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            //为空时取当前时间
            Calendar now = Calendar.getInstance();
            result[0] = now.get(Calendar.YEAR) + "";
            result[1] = now.get(Calendar.MONTH) + 1 + "";
            result[2] = now.get(Calendar.DAY_OF_MONTH) + "";
            result[3] = now.get(Calendar.HOUR_OF_DAY) + "";
            result[4] = now.get(Calendar.MINUTE) + "";
            result[5] = now.get(Calendar.SECOND) + "";
        }

        return result;
    }

    public static String padLeft(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

    public static String padRight(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
        for (int i = 0; i < diff; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

/*
    public static void main(String[] args) {
        System.out.print(formatMsg("[读卡]length=4; regionStart=0; regionLength=16 code: 盒子校验BF00010000"));
    }*/

}
