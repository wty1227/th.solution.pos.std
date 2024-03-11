package cn.th.phonerf.activity.pos.other;

import android.app.DialogFragment;

public interface NoticeDialogListener {
    public void onDialogPositiveClick(DialogFragment dialog, Object object, int inputFlag);
    public void onDialogNegativeClick(DialogFragment dialog);
}
