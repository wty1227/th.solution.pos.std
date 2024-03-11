package cn.th.phonerf.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * @Info 带clean图标的EditText
 * @Auth Bello
 * @Time 16-6-27 下午4:17
 * @Ver
 */
public class CleanableEditText extends EditText {

    Drawable[] drawables;
    private Drawable rightDrawable;
    private boolean isOnFocus;

    public CleanableEditText(Context context) {
        super(context);
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        //获取EditText右侧Drawable(通过android:drawableRight设置)
        drawables = getCompoundDrawables();
        rightDrawable = drawables[2];

        //初始化clean图标隐藏
        setClearDrawableVisible(false);

        // 设置hint字体变小
        setHint(Html.fromHtml("<small>" + getHint() + "</small>"));

        //设置焦点监听
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isOnFocus = hasFocus;
                if (isOnFocus) {
                    boolean isVisible = getText().toString().length()>=1;
                    setClearDrawableVisible(isVisible);
                } else {
                    setClearDrawableVisible(false);
                }
            }
        });

        /**
         * 设置文字变化监听
         */
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0 && isOnFocus){
                    setClearDrawableVisible(true);
                } else {
                    setClearDrawableVisible(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 显示或隐藏clean图标
     *
     * @param isVisible
     */
    private void setClearDrawableVisible(boolean isVisible){
        if (isVisible){
            rightDrawable.setBounds(0, 0, 32, 32);
            //setCompoundDrawables
            setCompoundDrawables(drawables[0], null, rightDrawable, null);
        } else {
            setCompoundDrawables(drawables[0], null, null, null);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                float left = getWidth() - getTotalPaddingRight();
                float right = getWidth();
                boolean isClean = event.getX() > left && event.getX()<= right;
                if (isClean){
                    setText("");
                }

                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
