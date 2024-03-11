package cn.th.phonerf.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.th.phonerf.R;
import cn.th.phonerf.application.MyApplication;

;


/**
 * @Info 进度条方法
 * @Auth bell
 * @Time 16-7-31 上午11:44
 * @Ver
 */
public class PayProgressUtils {

    /**
     * 页面资源加载圆圈
     *
     * @param context
     * @param msg
     * @param isClose
     * @return
     */
    public static Dialog loadCircleProgress(Context context, String msg, boolean isClose){
        if (null == context){
            context = MyApplication.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.lt_page_load_form, null);
        ProgressBar bar = (ProgressBar) view.findViewById(R.id.progress_circle_bar);
        TextView text = (TextView) view.findViewById(R.id.progress_circle_text);
        text.setText(msg);

        int width = (context.getResources().getDrawable(
                R.drawable.ic_loading1).getIntrinsicWidth());
        bar.getLayoutParams().height = width;
        bar.getLayoutParams().width = width;
        bar.invalidate();

        Dialog dialog = new Dialog(context, R.style.progressCircleDialog);
        isClose = false;
        dialog.setCancelable(isClose);
        dialog.getWindow().setContentView(view);//,  new LinearLayout.LayoutParams(width*5, width*5));
        return dialog;
    }

    public static Dialog loadCircleProgress(Context context, String msg, boolean isClose, int color){
        if (null == context){
            context = MyApplication.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.lt_page_load_circle, null);
        ProgressBar bar = (ProgressBar) view.findViewById(R.id.progress_circle_bar);
        TextView text = (TextView) view.findViewById(R.id.progress_circle_text);
        text.setText(msg);
        text.setTextColor(color);

        int width = (context.getResources().getDrawable(
                R.drawable.ic_loading1).getIntrinsicWidth());
        bar.getLayoutParams().height = width;
        bar.getLayoutParams().width = width;
        bar.invalidate();

        Dialog dialog = new Dialog(context, R.style.progressCircleDialog2);
        isClose = false;
        dialog.setCancelable(isClose);
        dialog.getWindow().setContentView(view,  new LinearLayout.LayoutParams(width*5, width*5));
        return dialog;
    }


}
