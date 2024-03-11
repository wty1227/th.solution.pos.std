package cn.th.phonerf.activity.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import cn.th.phonerf.R;
import cn.th.phonerf.utils.PayProgressUtils;
import cn.th.phonerf.utils.ProgressUtils;
import cn.th.phonerf.view.MyToast;

public class BaseFragment extends Fragment implements IBaseView {
    public Context mContext;
    public Dialog loadingDlg;
    public Dialog loadingPayDlg;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);
        loadingPayDlg = PayProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);
    }

    @Override
    public void setToast(String msg) {
        MyToast.toast(mContext, msg);
    }

    @Override
    public void setProgressDlg(boolean isShow) {
        if (loadingDlg != null) {
            if(isShow)
                loadingDlg.show();
            else
                loadingDlg.dismiss();
        }
    }

    @Override
    public void setPayProgressDlg(boolean isShow) {
        if (loadingPayDlg != null) {
            if(isShow)
                loadingPayDlg.show();
            else
                loadingPayDlg.dismiss();
        }
    }

    @Override
    public void setClickable(View view, boolean clickable) {
        if(view != null)
            view.setClickable(clickable);
    }

    @Override
    public void go() {
        //finish();
    }
}
