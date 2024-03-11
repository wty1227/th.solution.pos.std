package cn.th.phonerf.activity.base;

import android.view.View;

public interface IBaseView {

    /**
     * 展示Toast提示
     * @param msg
     */
    void setToast(String msg);

    /**
     * 显示加载框
     * @param isShow
     */
    void setProgressDlg(boolean isShow);

    void setPayProgressDlg(boolean isShow);
    /**
     *控件是否可点击
     * @param view
     * @param clickable
     */
    void setClickable(View view, boolean clickable);

    /**
     * 关闭当前页面
     */
    void go();
}
