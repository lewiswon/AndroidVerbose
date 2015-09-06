package com.lewis.downloaddemo;

import java.text.DecimalFormat;

import android.support.v7.app.ActionBarActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener, DownLoadListener {
	private Button downloadBtn, pauseBtn, continueBtn;
	private EditText urlEt;
	private ProgressBar pbMain;
	private double progress;
	private Notification notification;
	private RemoteViews remoteViews;
	private NotificationManager notificationManager;
	private DownloadService downloadService;
	private TextView downloadTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		downloadBtn = (Button) findViewById(R.id.btn_download);
		pauseBtn = (Button) findViewById(R.id.btn_pause);
		continueBtn = (Button) findViewById(R.id.btn_continue);
		urlEt = (EditText) findViewById(R.id.et_url);
		pbMain = (ProgressBar) findViewById(R.id.pb_main);
		downloadTv = (TextView) findViewById(R.id.tv_downloading);
		downloadBtn.setOnClickListener(this);
		pauseBtn.setOnClickListener(this);
		continueBtn.setOnClickListener(this);
		Intent intent = new Intent(this, DownloadService.class);
		intent.putExtra("down_url", urlEt.getText().toString().trim());
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			downloadService = ((DownloadService.DownloadBinder) service).getService();
			downloadService.setDownloadListener(MainActivity.this);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_download:
			downloadService.startDownload();
			break;
		case R.id.btn_pause:

			continueBtn.setEnabled(true);
			break;
		case R.id.btn_continue:
			continueBtn.setEnabled(false);
			break;

		default:
			break;
		}
	}

	@Override
	public void onDownload(double progresss) {
		this.progress = progresss;
		pbMain.setProgress((int) progress);
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				DecimalFormat format = new DecimalFormat("0.00");
				downloadTv.setText("正在下载..." + format.format(progress) + "%");
			}
		});
		//				Log.i("progress", progress + "");

		//		notification.contentView.setProgressBar(R.id.pb_notify, 100, (int) progress, false);
		//		notificationManager.notify(1005, notification);

	}

	private void showDownloadNotification() {
		Notification.Builder builder = new Notification.Builder(this);
		builder.setTicker("开始下载");
		builder.setSmallIcon(R.drawable.download_icon);
		builder.setAutoCancel(true);
		builder.setWhen(System.currentTimeMillis());
		builder.setContentIntent(PendingIntent.getActivity(MainActivity.this, 1001, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
		notification = builder.build();
		remoteViews = new RemoteViews(this.getPackageName(), R.layout.notification_download);
		notification.contentView = remoteViews;
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(1005, notification);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(serviceConnection);
	}
}
