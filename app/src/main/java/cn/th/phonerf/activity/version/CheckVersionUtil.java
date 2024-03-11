package cn.th.phonerf.activity.version;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.th.phonerf.BuildConfig;
import cn.th.phonerf.R;
import cn.th.phonerf.utils.encrypt.Md5Util;
import cn.th.phonerf.view.MyToast;

import static cn.th.phonerf.application.MyApplication.getContext;


/**
 * 检查APP版本
 *
 * @author Bello
 * @version Time：2014-12-2 下午5:23:43
 *
 */
public class CheckVersionUtil {
	private static Thread downLoadThread;
	private static String savePath = "";

	private static NotificationManager updateNotificationManager = null;
	private static Notification updateNotification = null;

	public static String app_Version = "";
	public static String app_Url = "";
	public static String app_Type = "";
	public static String app_Hash = "";
	public static String currentTime = "";

	public static Context mContext;
	private Handler mHandler;
	private static int downloadCount = 0;


	/**
	 * 版本更新弹框提示
	 */
	public static void getDialog(String msg) {
		dialogVersion(mContext, msg, "取消", "确定");
	}

	/**
	 * 强制版本更新弹框提示
	 */
	public static void getDialogMust(String msg) {
		dialogVersion(mContext, msg, null, "下载");
	}

	/**
	 * 升级弹框样式
	 *
	 * @param context
	 *            弹框标题
	 * @param msg
	 *            弹框提示信息
	 * @param cancelStr
	 *            取消按钮（强制时为null）
	 * @param okStr
	 *            确定按钮（强制时为下载）
	 */
	public static void dialogVersion(final Context context, String msg, String cancelStr, String okStr) {

		savePath = Environment.getExternalStorageDirectory().toString() + "/updateTmp";

		Builder builder = new Builder(context);
		final AlertDialog dialog = builder.create();
		// 布局
		LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_version, null);
		// 标题
		TextView titleText = (TextView) view.findViewById(R.id.dialog_title_text);

		// 描述
		TextView descText = (TextView) view.findViewById(R.id.dialog_desc_text);
		descText.setText(Html.fromHtml(msg));
		// 按钮
		LinearLayout selectLayout = (LinearLayout) view.findViewById(R.id.dialog_select_layout);
		Button cancelBtn = (Button) view.findViewById(R.id.dialog_cancel_btn);
		Button okBtn = (Button) view.findViewById(R.id.dialog_ok_btn);
		Button downBtn = (Button) view.findViewById(R.id.dialog_down_btn);

		// 强制升级
		if (cancelStr == null || cancelStr.equals("")) {
			selectLayout.setVisibility(View.GONE);
			downBtn.setText(okStr);
			// 普通升级
		} else {
			selectLayout.setVisibility(View.VISIBLE);
			cancelBtn.setText(cancelStr);
			okBtn.setText(okStr);
			downBtn.setVisibility(View.GONE);
		}

		// 取消按钮
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (!isSameHash()) {
//					downloadApk();
//				}
				dialog.cancel();
			}
		});
		// 确定按钮
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSameHash()) {
					installApk();
				} else {
					CheckVersionUtil.notifyDown(false);
				}

				dialog.dismiss();

			}
		});
		// 强制下载按钮
		downBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSameHash()) {
					installApk();
				} else {
					CheckVersionUtil.notifyDown(true);
				}
				dialog.dismiss();

			}
		});

		dialog.setCancelable(false);
		dialog.show();
		dialog.getWindow().setContentView(view); // show()之后重新布局解决黑边
	}

	/**
	 * 比对本地和服务器文件HASH是否相同
	 *
	 * @return
	 */
	protected static boolean isSameHash() {
		try {

			// 获取本地Hash
			File apkfile = new File(savePath + "/tmp.apk");
			if (!apkfile.exists()) {
				return false;
			}

			String localHash = Md5Util.md5File(apkfile.getPath());
			if (localHash.equalsIgnoreCase(app_Hash)) {
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;

	}

	/**
	 * 通知显示并下载
	 *
	 * @param isMustDown
	 *            是否强制更新
	 */
	public static void notifyDown(boolean isMustDown) {
		// 判断SD卡是否存在
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			MyToast.toast(mContext, "检测不到SD卡，无法完成更新");
		} else {


			downFile(  app_Url );     //在下面的代码段

/*
			updateNotificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
			updateNotification = new Notification();

			// 设置通知栏显示内容
			updateNotification.contentView = new RemoteViews(mContext.getPackageName(), R.layout.notify_layout);
			updateNotification.contentView.setProgressBar(R.id.notify_progress, 100, 0, false);
			updateNotification.contentView.setTextViewText(R.id.notify_title, "通恒:正在下载：0%");
			updateNotification.icon = R.drawable.ic_logo;
			updateNotification.tickerText = "开始下载 通恒手持终端系统";
			PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0, new Intent(),
					PendingIntent.FLAG_CANCEL_CURRENT);



			//updateNotification.setLatestEventInfo(mContext, "路港通", "0%", pendingintent);

//			 发出通知
			updateNotificationManager.notify(0, updateNotification);

			// 下载
			downloadApk();
			// 判断是强制下载的，关闭当前APP
			if (isMustDown) {
				((Activity) mContext).finish();
			}*/
		}
	}

	private static ProgressDialog pBar;
	private static void  downFile(final String addr) {
		pBar = new ProgressDialog(mContext);    //进度条，在下载的时候实时更新进度，提高用户友好度
		pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pBar.setTitle("正在下载");
		pBar.setMessage("请稍候...");
		pBar.setProgress(0);
		pBar.show();

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try
				{
					URL url = new URL(addr);
					connection = (HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(20000);
					connection.setReadTimeout(20000);
					InputStream in = connection.getInputStream();
					FileOutputStream fileOutputStream = null;

					if (in != null) {
						File file = new File(savePath);
						if (!file.exists()) {
							file.mkdir();
						}
						String apkFile = savePath + "/tmp.apk";

						File ApkFile = new File(apkFile);
						fileOutputStream = new FileOutputStream(ApkFile);
						byte[] buf = new byte[20480];   //这个是缓冲区，即一次读取10个比特，我弄的小了点，因为在本地，所以数值太大一 下就下载完了，看不出progressbar的效果。
						int ch = -1;
						long process = 0;
						int rate = 0;
						long fileLength = 0;
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
							fileLength = connection.getContentLengthLong();
						}

						while ((ch = in.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							//process += ch;
							process += ch;
							if(fileLength != 0)

								rate = (int) Math.round( (double)process  / (double)fileLength * 100 );
							else
								rate++;
							pBar.setProgress(rate);       //这里就是关键的实时更新进度了！
						}
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					installApk();

				} catch (IOException e) {
					e.printStackTrace();
				}
				catch(Exception e)
				{
					e.printStackTrace();

				}
				finally
				{
					if(connection != null)
						connection.disconnect();
				}
			}
		}).start();


	}


	private static Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {

				URL url = new URL(app_Url);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(20000);
				conn.setReadTimeout(20000);
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				String apkFile = savePath + "/tmp.apk";
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[2048];

				do {
					int numread = is.read(buf);
					count += numread;

					if ((downloadCount == 0) || (int) (count * 100 / length) - 2 > downloadCount) {
						downloadCount += 2;
						updateNotification.contentView = new RemoteViews(mContext.getPackageName(),
								R.layout.notify_layout);
						updateNotification.contentView.setProgressBar(R.id.notify_progress, 100, downloadCount, false);
						updateNotification.contentView.setTextViewText(R.id.notify_title, "通恒:正在下载：" + downloadCount
								+ "%");
						updateNotificationManager.notify(0, updateNotification);
					}
					// // 开始下载
					if (numread <= 0) {
						// // 下载完成
						updateNotificationManager.cancel(0);
						installApk();
						break;
					}
					fos.write(buf, 0, numread);
				} while (true);// 读取完毕

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

	/**
	 * 下载apk
	 *
	 */
	public static void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();

	}

	/**
	 * 安装װapk
	 *
	 */
	private static void installApk() {
		File apkfile = new File(savePath + "/tmp.apk");
		if (!apkfile.exists()) {
			return;
		}
	StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			builder.detectFileUriExposure();
		}
/*
			Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(
					mContext
					, "cn.th.solution.ureapos.fileprovider"
					, apkfile);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		}else
			intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(intent);
*/

		//File apkFile = new File(filePath);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			//Log.w(TAG, "版本大于 N ，开始使用 fileProvider 进行安装");
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(
					mContext
					, "cn.th.solution.selfpos.fileprovider"
					, apkfile);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			//Log.w(TAG, "正常进行安装");
			intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
		}
		mContext.startActivity(intent);
	}

	/**
	 * 删除下载的apk
	 */
	public static void deleteApk() {
		try {
			File apkfile = new File(Environment.getExternalStorageDirectory().toString() + "/updateTmp" + "/tmp.apk");
			if (apkfile.exists()) {
				apkfile.delete();
				String savePath = Environment.getExternalStorageDirectory().toString() + "updateTmp";
				File apkFolder = new File(savePath);
				apkFolder.delete();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
