package com.ifenduo.coach.qr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.activity.BaseActivity;
import com.ifenduo.coach.activity.StudentRecordActivity;
import com.ifenduo.coach.bean.Student;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.http.Request.OnStateListener;

public class CaptureActivity extends BaseActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private TextView backTv;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qr_main);
		CameraManager.init(getApplication());
		backTv = (TextView) findViewById(R.id.tv_qr_back);
		backTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		/**
		 * 这里就是一系列初始化相机view的过程
		 */
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		/**
		 * 这里就是看看是否是正常声音播放模式，如果是就播放声音，如果不是，则不播放
		 */
		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		/**
		 * 初始化声音
		 */
		initBeepSound();
		/**
		 * 是否震动
		 */
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();

		/**
		 * 关掉相机，关掉解码线程，清空looper队列中message
		 */

		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();// 关掉相机
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * 初始化相机
	 */
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (RuntimeException e) {
			// Toast.makeText(CaptureActivity.this,"抛异常" , 2000).show();
			e.printStackTrace();

			return;
		}
		if (handler == null) {
			/**
			 * 新建解码结果handler
			 */
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		/**
		 * 初始化相机
		 */
		if (!hasSurface)

		{
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	/**
	 * 返回显示的view
	 */
	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	/**
	 * 返回处理解码结果的handler
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * 清空view中先前扫描成功的图片
	 */
	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		// TODO 解码后处理结果
		dialog.setMessage("扫码成功,正在匹配...");
		dialog.show();
		/**
		 * 重新计时
		 */
		inactivityTimer.onActivity();
		/**
		 * 将结果绘制到view中
		 */
		viewfinderView.drawResultBitmap(barcode);
		/**
		 * 播放jeep声音
		 */
		playBeepSoundAndVibrate();
		/**
		 * 显示解码字符串
		 */
		Map<String, String> params = new HashMap<String, String>();
		params.put("traineeId", obj.getText());
		params.put("trainerId", app.getCurrentUser().getId());
		new Request<String>(this).stateRequest(API.User.urlQr, params, false, new OnStateListener() {

			@Override
			public void onSuccess(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					String data = jsonObject.getString("data");
					if (data != null) {
						Intent intent = new Intent(getApplicationContext(), StudentRecordActivity.class);
						Student student = new Gson().fromJson(data, Student.class);
						String[] studentInfos = new String[]{student.getTraineeId(), student.getName(), student.getHeadpic(), student.getNum()};
						intent.putExtra("student_infos", studentInfos);
						intent.putExtra("vip", student.isVIP());
						startActivity(intent);
						dialog.dismiss();
						finish();
					}
				} catch (JSONException e) {
				}
			}

			@Override
			public void onError(String error) {
				Toast.makeText(getApplicationContext(), "处理失败,请稍后再试", Toast.LENGTH_SHORT).show();
				finish();
			}
		});

		// txtResult.setText(obj.getBarcodeFormat().toString() + ":" +
		// obj.getText());

		// IndexActivity.RESULT_MESSAGE = obj.getBarcodeFormat().toString() +
		// ":"
		// + obj.getText();
		// IndexActivity.RESULT_BITMAP = barcode;

		// Intent intent = new Intent(CaptureActivity.this,
		// IndexActivity.class);
		// this.startActivity(intent);
		// this.finish();
	}
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	/**
	 * 震动时间
	 */
	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		/**
		 * 播放声音
		 */
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		/**
		 * 震动
		 */
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}