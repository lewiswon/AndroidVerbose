package com.lewis.downloaddemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class DownloadService extends Service implements DownLoadListener {
	private String url;
	private Notification notification;
	private RemoteViews remoteViews;
	private NotificationManager notificationManager;
	private DownLoadListener listener;

	@Override
	public IBinder onBind(Intent intent) {
		Log.i("url", intent.getStringExtra("down_url"));
		url = intent.getStringExtra("down_url");
		return new DownloadBinder();
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	public void setDownloadListener(DownLoadListener listener) {
		this.listener = listener;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("url", intent.getStringExtra("down_url"));
		url = intent.getStringExtra("down_url");
		return super.onStartCommand(intent, flags, startId);
	}

	public void startDownload() {
		showDownloadNotification();
		new SimpleDownloadThread(url, this).start();
	}

	private void showDownloadNotification() {
		Notification.Builder builder = new Notification.Builder(this);
		builder.setTicker("¿ªÊ¼ÏÂÔØ");
		builder.setSmallIcon(R.drawable.download_icon);
		builder.setAutoCancel(true);
		builder.setWhen(System.currentTimeMillis());
		builder.setContentIntent(PendingIntent.getActivity(this, 1001, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
		notification = builder.build();
		remoteViews = new RemoteViews(this.getPackageName(), R.layout.notification_download);
		notification.contentView = remoteViews;
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(1005, notification);
	}

	@Override
	public void onDownload(double progress) {
		notification.contentView.setProgressBar(R.id.pb_notify, 100, (int) progress, false);
		notificationManager.notify(1005, notification);
		if (listener != null) {
			listener.onDownload(progress);
		}
	}

	class DownloadBinder extends Binder {
		public DownloadService getService() {
			return DownloadService.this;
		}
	}
}
