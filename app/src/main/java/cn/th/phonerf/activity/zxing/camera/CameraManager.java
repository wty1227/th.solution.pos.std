/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.th.phonerf.activity.zxing.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;


import java.io.IOException;
import java.util.List;

import cn.th.phonerf.activity.zxing.camera.open.OpenCameraInterface;


/**
 * This object wraps the Camera service object and expects to be the only one
 * talking to it. The implementation encapsulates the steps needed to take
 * preview-sized images, which are used for both preview and decoding.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class CameraManager{
//	public class CameraManager implements Camera.PreviewCallback{
	private static final String TAG = CameraManager.class.getName();
	private Camera camera;
	private Camera.Parameters parameters;
	private AutoFocusManager autoFocusManager;
	private int requestedCameraId = -1;
	private Context mContext;

	private boolean initialized;
	private boolean previewing;








	final Context context;
	final CameraConfigurationManager configManager;


	/**
	 * Preview frames are delivered here, which we pass on to the registered
	 * handler. Make sure to clear the handler so it will only receive one
	 * message.
	 */
	final PreviewCallback previewCallback;

	public CameraManager(Context context) {
		this.context = context;
		this.configManager = new CameraConfigurationManager(context);
		previewCallback = new PreviewCallback(configManager);
	}

	/**
	 * Opens the camera driver and initializes the hardware parameters.
	 *
	 * @param holder
	 *            The surface object which the camera will draw preview frames
	 *            into.
	 * @throws IOException
	 *             Indicates the camera driver failed to open.
	 */
	public synchronized void openDriver(SurfaceHolder holder) throws IOException {
		Camera theCamera = camera;
		if (theCamera == null) {

			if (requestedCameraId >= 0) {
				theCamera = OpenCameraInterface.open(requestedCameraId);
			} else {
				theCamera = OpenCameraInterface.open();
			}

			if (theCamera == null) {
				throw new IOException();
			}
			camera = theCamera;
		}
		theCamera.setPreviewDisplay(holder);

		if (!initialized) {
			initialized = true;
			configManager.initFromCameraParameters(theCamera);
		}

		Camera.Parameters parameters = theCamera.getParameters();
		String parametersFlattened = parameters == null ? null : parameters.flatten(); // Save
																						// these,
																						// temporarily
		try {
			configManager.setDesiredCameraParameters(theCamera, false);
		} catch (RuntimeException re) {
			// Driver failed
			Log.w(TAG, "Camera rejected parameters. Setting only minimal safe-mode parameters");
			Log.i(TAG, "Resetting to saved camera params: " + parametersFlattened);
			// Reset:
			if (parametersFlattened != null) {
				parameters = theCamera.getParameters();
				parameters.unflatten(parametersFlattened);
				try {
					theCamera.setParameters(parameters);
					configManager.setDesiredCameraParameters(theCamera, true);
				} catch (RuntimeException re2) {
					// Well, darn. Give up
					Log.w(TAG, "Camera rejected even safe-mode parameters! No configuration");
				}
			}
		}

	}

	public synchronized boolean isOpen() {
		return camera != null;
	}

	/**
	 * Closes the camera driver if still in use.
	 */
	public synchronized void closeDriver() {
		if (camera != null) {
			camera.release();
			camera = null;
			// Make sure to clear these each time we close the camera, so that
			// any scanning rect
			// requested by intent is forgotten.
		}
	}

	/**
	 * Asks the camera hardware to begin drawing preview frames to the screen.
	 */
	public synchronized void startPreview() {
		Camera theCamera = camera;
		if (theCamera != null && !previewing) {
			theCamera.startPreview();
			previewing = true;
			autoFocusManager = new AutoFocusManager(context, camera);
		}
	}

	/**
	 * Tells the camera to stop drawing preview frames.
	 */
	public synchronized void stopPreview() {
		if (autoFocusManager != null) {
			autoFocusManager.stop();
			autoFocusManager = null;
		}
		if (camera != null && previewing) {
			camera.stopPreview();
			previewCallback.setHandler(null, 0);
			previewing = false;
		}
	}

	/**
	 * A single preview frame will be returned to the handler supplied. The data
	 * will arrive as byte[] in the message.obj field, with width and height
	 * encoded as message.arg1 and message.arg2, respectively.
	 *
	 * @param handler
	 *            The handler to send the message to.
	 * @param message
	 *            The what field of the message to be sent.
	 */
	public synchronized void requestPreviewFrame(Handler handler, int message) {
		Camera theCamera = camera;
		if (theCamera != null && previewing) {
			previewCallback.setHandler(handler, message);
			theCamera.setOneShotPreviewCallback(previewCallback);
		}
	}

	/**
	 * Allows third party apps to specify the camera ID, rather than determine
	 * it automatically based on available cameras and their orientation.
	 *
	 * @param cameraId
	 *            camera ID of the camera to use. A negative value means
	 *            "no preference".
	 */
	public synchronized void setManualCameraId(int cameraId) {
		requestedCameraId = cameraId;
	}

	/**
	 * 获取相机分辨率
	 *
	 * @return
	 */
	public Point getCameraResolution() {
		return configManager.getCameraResolution();
	}
//
	public Size getPreviewSize() {
		if (null != camera) {
			return camera.getParameters().getPreviewSize();
		}
		return null;
	}

	/**
	 * 打开闪光灯
	 */
	public void openLight() {
		if (camera != null) {
			Camera.Parameters parameter = camera.getParameters();
			parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			camera.setParameters(parameter);
		}
	}
	/**
	 * 关闭闪光灯
	 */
	public void offLight() {
		if (camera != null) {
			Camera.Parameters parameter = camera.getParameters();
			parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			camera.setParameters(parameter);
		}
	}



















//	public Camera open(int cameraId) {
//		int numCameras = Camera.getNumberOfCameras();
//		if (numCameras == 0) {
//			Log.e(TAG, "No cameras!");
//			return null;
//		}
//		boolean explicitRequest = cameraId >= 0;
//		if (!explicitRequest) {
//			// Select a camera if no explicit camera requested
//			int index = 0;
//			while (index < numCameras) {
//				Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//				Camera.getCameraInfo(index, cameraInfo);
//				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//					break;
//				}
//				index++;
//			}
//			cameraId = index;
//		}
//		Camera camera;
//		if (cameraId < numCameras) {
//			Log.e(TAG, "Opening camera #" + cameraId);
//			camera = Camera.open(cameraId);
//		} else {
//			if (explicitRequest) {
//				Log.e(TAG, "Requested camera does not exist: " + cameraId);
//				camera = null;
//			} else {
//				Log.e(TAG, "No camera facing back; returning camera #0");
//				camera = Camera.open(0);
//			}
//		}
//		int rotation = getDisplayOrientation();
//		camera.setDisplayOrientation(rotation);
//		camera.setPreviewCallback(this);
//		return camera;
//	}
//
//	public int getDisplayOrientation() {
//		Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//		int rotation = display.getRotation();
//		int degrees = 0;
//		switch (rotation) {
//			case Surface.ROTATION_0:
//				degrees = 0;
//				break;
//			case Surface.ROTATION_90:
//				degrees = 90;
//				break;
//			case Surface.ROTATION_180:
//				degrees = 180;
//				break;
//			case Surface.ROTATION_270:
//				degrees = 270;
//				break;
//		}
//
//		Camera.CameraInfo camInfo =
//				new Camera.CameraInfo();
//		Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, camInfo);
//
//		int result = (camInfo.orientation - degrees + 360) % 360;
//		return result;
//	}
//
//	public CameraManager(Context context, PreviewCallback previewCallback) {
//		mContext = context;
//		this.previewCallback = previewCallback;
//		this.context = context;
//		this.configManager = new CameraConfigurationManager(context);
//	}
//	/**
//    * 打开camera
//    *
//    * @param holder SurfaceHolder
//    * @throws IOException IOException
//    */
//	public synchronized void openDriver(SurfaceHolder holder) throws IOException {
////		Log.e(TAG, "openDriver");
//		Camera theCamera = camera;
//		if (theCamera == null) {
//			theCamera = open(requestedCameraId);
//			if (theCamera == null) {
//				throw new IOException();
//			}
//			camera = theCamera;
//		}
//		theCamera.setPreviewDisplay(holder);
//		if (!initialized) {
//			initialized = true;
//			parameters = camera.getParameters();
//			List<Size> previewSizes = parameters.getSupportedPreviewSizes();
//			int w = 800;
//			int h = 600;
//			for (Camera.Size size : previewSizes) {
//				Log.e("TAG", "previewSizes width:" + size.width);
//				Log.e("TAG", "previewSizes height:" + size.height);
//				if (size.width - w <= 100 & size.width >= w) {
//					w = size.width;
//					h = size.height;
//					break;
//				}
//			}
//			parameters.setPreviewSize(w, h);
//			parameters.setPictureFormat(ImageFormat.JPEG);
//			parameters.setJpegQuality(100);
//			parameters.setPictureSize(800, 600);
//			theCamera.setParameters(parameters);
//		}
//	}
//	/**
//    * camera是否打开
//    *
//    * @return camera是否打开
//    */
//	public synchronized boolean isOpen() {
//		return camera != null;
//	}
//	/**
//    * 关闭camera
//    */
//	public synchronized void closeDriver() {
//		Log.e(TAG, "closeDriver");
//		if (camera != null) {
//			camera.release();
//			camera = null;
//		}
//	}
//	/**
//    * 开始预览
//    */
//	public synchronized void startPreview() {
//		Log.e(TAG, "startPreview");
//		Camera theCamera = camera;
//		if (theCamera != null && !previewing) {
//			theCamera.startPreview();
//			previewing = true;
//			autoFocusManager = new AutoFocusManager(camera);
//		}
//	}
//	/**
//    * 关闭预览
//    */
//	public synchronized void stopPreview() {
//		Log.e(TAG, "stopPreview");
//		if (autoFocusManager != null) {
//			autoFocusManager.stop();
//			autoFocusManager = null;
//		}
//		if (camera != null && previewing) {
//			camera.stopPreview();
//			camera.setPreviewCallback(null); // Camera is being used after Camera.release() was called
//			previewing = false;
//		}
//	}
//	public void setPreviewCallback(Camera.PreviewCallback cb) {
//		camera.setOneShotPreviewCallback(this);
//	}
//	/**
//    * 打开闪光灯
//    */
//	public synchronized void openLight() {
//		Log.e(TAG, "openLight");
//		if (camera != null) {
//			parameters = camera.getParameters();
//			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//			camera.setParameters(parameters);
//		}
//	}
//	/**
//    * 关闭闪光灯
//    */
//	public synchronized void offLight() {
//		Log.e(TAG, "offLight");
//		if (camera != null) {
//			parameters = camera.getParameters();
//			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//			camera.setParameters(parameters);
//		}
//	}
//	/**
//    * 拍照
//    *
//    * @param shutter ShutterCallback
//    * @param raw   PictureCallback
//    * @param jpeg  PictureCallback
//    */
//	public synchronized void takePicture(final Camera.ShutterCallback shutter, final Camera.PictureCallback raw,
//										 final Camera.PictureCallback jpeg) {
//		camera.takePicture(shutter, raw, jpeg);
//	}
//	@Override
//	public void onPreviewFrame(byte[] bytes, Camera camera) {}
//
//	private Bitmap reSize(byte[] data) {
//		Log.i(TAG, "myJpegCallback:onPictureTaken...");
//		Bitmap cutMap = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图
//
//		//设置FOCUS_MODE_CONTINUOUS_VIDEO)之后，myParam.set("rotation", 90)失效。图片竟然不能旋转了，故这里要旋转下
//		Matrix matrix = new Matrix();
//		matrix.postRotate((float) 90.0);
//		Bitmap rotaBitmap = Bitmap.createBitmap(cutMap, 0, 0, cutMap.getWidth(), cutMap.getHeight(), matrix, false);
//
//		//旋转后rotaBitmap是960×1280.预览surfaview的大小是540×800
//		//将960×1280缩放到540×800
//		Bitmap sizeBitmap = Bitmap.createScaledBitmap(rotaBitmap, 540, 800, true);
//		Bitmap rectBitmap = Bitmap.createBitmap(sizeBitmap, 100, 200, 300, 300);//截取
//		 return rectBitmap;
//	}
}
