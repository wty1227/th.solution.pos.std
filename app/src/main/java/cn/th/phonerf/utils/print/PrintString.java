package cn.th.phonerf.utils.print;
import cn.th.phonerf.utils.TextUtil;

public class PrintString
{
    private boolean _isBigSize;
    private String _prtSrt;

    public PrintString()
    {
        this._prtSrt = "";
    }

    public PrintString(String strPrt)
    {
        this._prtSrt = "";
        this._prtSrt = strPrt;
    }

    public PrintString(String strPrt, int intLen)
    {
        this._prtSrt = "";
        this._prtSrt = PrintStrAlign(strPrt, intLen, ContentAlignment.TopLeft);
    }

    public PrintString(String strPrt, int intLen, ContentAlignment align)
    {
        this._prtSrt = "";
        this._prtSrt = PrintStrAlign(strPrt, intLen, align);
    }

    public PrintString(String strPrt, int intLen, boolean isWindowPrinter, boolean isHzPrinter, ContentAlignment align, boolean isBigSize, int pcType)
    {
        this._prtSrt = "";
        this._isBigSize = isBigSize;
        if (isBigSize)
        {
            if (pcType != 0x63)
            {
                this._prtSrt = PrintStrAlign(strPrt, (int) (((double) intLen) / 1.26), align);
            }
            else if (isWindowPrinter)
            {
                this._prtSrt = PrintStrAlign(strPrt, (int) (((double) intLen) / 1.23), align);
            }
            else if (isHzPrinter)
            {
                this._prtSrt = PrintStrAlign(strPrt, (int) (((double) intLen) / 1.8), align);
            }
        }
        else
        {
            this._prtSrt = PrintStrAlign(strPrt, intLen, align);
        }
    }

    public static String GetEmptyChar(int count)
    {
        String str = "";
        for (int i = 0; i < count; i++)
        {
            str = str + " ";
        }
        return str;
    }

    public static String PrintStrAlign(String str, int intLen, ContentAlignment align)  {
        try {
            int num = 0;
            int byteCount = str.getBytes("utf-8").length;// Encoding.Default.GetByteCount(str);
            int length = str.length();
            if (intLen == 0)
            {
                str = "";
                return str;
            }
            if (str.length() < intLen)
            {
                switch (align)
                {
                    case TopCenter:
                    case MiddleCenter:
                    case BottomCenter:
                        if ((byteCount + 1) < intLen)
                        {
                            num = ((intLen - byteCount) / 2) + length;
                            str = TextUtil.padRight(str, (num < 0) ? 0 : num, ' ');
                            byteCount = str.getBytes("utf-8").length;// Encoding.Default.GetByteCount(str);
                            if (byteCount < intLen)
                            {
                                str = str + GetEmptyChar(intLen - byteCount);
                            }
                        }
                        return str;

                    case TopRight:
                    case BottomRight:
                    case MiddleRight:
                        if (str.length() < intLen)
                        {
                            num = (intLen - byteCount) + length;
                            str = TextUtil.padRight(str, (num < 0) ? 0 : num, ' ');
                        }
                        return str;
                }
                if (str.length() < intLen)
                {
                    num = (intLen - byteCount) + length;
                    str = TextUtil.padLeft(str, (num < 0) ? 0 : num, ' '); //  str.PadRight((num < 0) ? 0 : num, ' ');
                }
            }
            return str;
        }catch (Exception ex){
            return "";
        }

    }

    /*
    public static String PrintStrAlignEx(String str, int intLen, ContentAlignment align) throws UnsupportedEncodingException {
        String s = PrintStrAlign(str, intLen, align);
        if ( s.getBytes("utf-8").length > intLen)
        {
            s = SIString.GetString(s, intLen);
            if (s.getBytes("utf-8").length < intLen)
            {
                return (s + " ");
            }
        }
        return s;
    }
*/


    public boolean getIsBigSize(){
        return this._isBigSize;
    }

    public void setIsBigSize(boolean value) {
        this._isBigSize = value;
    }

    public String getPrtStr(){
        return this._prtSrt;
    }

    public void setPrtStr(String value){
        this._prtSrt = value;
    }

}
