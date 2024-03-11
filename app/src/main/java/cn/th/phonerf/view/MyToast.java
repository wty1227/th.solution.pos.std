package cn.th.phonerf.view;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.th.phonerf.R;;

/*
 * @info   
 * @author bell
 * @time 2016年7月32日 下午4:10:11
 * @version    
 */
public class MyToast {

	private static Toast mToast;
	private static TextView msgText;
	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	};

	public static void toast(Context mContext, String text) {

		mHandler.removeCallbacks(r);
		if (mToast != null) {
			msgText.setText(text);
			
		} else {
			View view = LayoutInflater.from(mContext).inflate(R.layout.lt_toast, null);
		    msgText = (TextView) view.findViewById(R.id.toast_context_text);
			msgText.setText(text);
			mToast = new Toast(mContext);
			mToast.setDuration(Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 120);
			mToast.setView(view);
					
		}
				
		mToast.show();

		// mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);

		// mHandler.postDelayed(r, duration);

		// mToast.show();
	}

	public static void toast(Context mContext, int resId) {
		toast(mContext, mContext.getResources().getString(resId));
	}

}
