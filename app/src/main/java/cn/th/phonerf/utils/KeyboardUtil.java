package cn.th.phonerf.utils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import cn.th.phonerf.R;

public class KeyboardUtil {

    private KeyboardView keyboardView;
    private Keyboard k1;
    public boolean isupper = false;// 是否大写
    boolean isShow = false;

    private EditText inputEditText;

    public KeyboardUtil( EditText edit) {
        this.inputEditText = edit;

        Activity act = (Activity) this.inputEditText.getContext();

        k1 = new Keyboard(act, R.xml.custom_softkeyboard_v1);
        keyboardView = (KeyboardView) act.findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);


        keyboardView.setOnKeyboardActionListener(listener);

        setSoftKeyboradHide(act, edit);
    }

    private void setSoftKeyboradHide(Activity ctx,EditText edit)
    {
        ctx.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus = null;
            String  method_setSoftInputOnFocus = "setShowSoftInputOnFocus";
            if(android.os.Build.VERSION.SDK_INT>16)
            {
                method_setSoftInputOnFocus = "setSoftInputOnFocus";
            }
            setShowSoftInputOnFocus = cls.getMethod(method_setSoftInputOnFocus, boolean.class);
            setShowSoftInputOnFocus.setAccessible(false);
            setShowSoftInputOnFocus.invoke(edit, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
            checkIShowPrewiew(primaryCode);
        }

        private void checkIShowPrewiew(int primaryCode)
        {
            List<Integer> list = Arrays.asList(Keyboard.KEYCODE_CANCEL,Keyboard.KEYCODE_DELETE,Keyboard.KEYCODE_SHIFT,46,32,44);
            if(list.contains(primaryCode))
            {
                keyboardView.setPreviewEnabled(false);
            }else{
                keyboardView.setPreviewEnabled(true);
            }
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes)
        {
            checkIShowPrewiew(primaryCode);
            Log.i("KeyBoard", "primaryCode="+primaryCode);

            Editable editable = inputEditText.getText();
            int start = inputEditText.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                hideKeyboard();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
                changeKey();
                keyboardView.setKeyboard(k1);
            } else if (primaryCode == 57419) { // go left
                if (start > 0) {
                    inputEditText.setSelection(start - 1);
                }
            }
            else if (primaryCode == 57421)
            { // go right
                if (start < inputEditText.length()) {
                    inputEditText.setSelection(start + 1);
                }
            }else{
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey()
    {
        List<Key> keylist = k1.getKeys();
        if (isupper) {// 大写切换小写
            isupper = false;
            for (Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                } else if (key.label.toString().equals("小写")) {
                    key.label = "大写";
                }

            }
        } else {// 小写切换大写
            isupper = true;
            for (Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                } else if (key.label.toString().equals("大写")) {
                    key.label = "小写";
                }
            }
        }
    }

    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
            isShow = true;
        }
    }

    public void hideKeyboard()
    {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
            isShow = false;
        }
    }

    private boolean isword(String str) {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }
}