package com.lewis.downloaddemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.os.Environment;
import android.util.Log;
import android.view.ViewDebug.FlagToString;

public class SimpleDownloadThread extends Thread {
	private String urlStr;
	private DownLoadListener downLoadListener;
	private int currentLength;
	private long startPosition;

	public SimpleDownloadThread(String url, DownLoadListener listener) {
		this.urlStr = url;
		this.downLoadListener = listener;
	}

	@Override
	public void run() {
		super.run();
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			InputStream inputStream = conn.getInputStream();
			Log.i("total length", conn.getContentLength() + "");
			int allLenth = conn.getContentLength();
			String filepath = Environment.getExternalStorageDirectory() + "/downloaddemo";
			File filePath = new File(filepath);
			if (!filePath.exists()) {
				filePath.mkdir();
			}
			File file = new File(filepath, "instruct.zip");
			if (file.exists()) {
				startPosition = file.length();
				file.delete();
			}
			file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);
			byte[] buffr = new byte[10 * 1024];
			int len;
			while ((len = inputStream.read(buffr)) != -1) {
				outputStream.write(buffr, 0, len);
				currentLength += len;
				Log.i("downloadlength", currentLength + "===" + allLenth + "==" + ((float) currentLength / (float) allLenth));
				double progress = (float) currentLength / (float) allLenth;
				downLoadListener.onDownload(progress * 100);

			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			String filepath = Environment.getExternalStorageDirectory() + "/downloaddemo";
			ZipInputStream inputStream = new ZipInputStream(new FileInputStream(new File(filepath, "instruct.zip")));
			BufferedInputStream bin = new BufferedInputStream(inputStream);
			String outPath = filepath;
			File out;
			ZipEntry entry;
			try {
				while ((entry = inputStream.getNextEntry()) != null && !entry.isDirectory()) {
					out = new File(outPath, entry.getName());
					if (!out.exists()) {
					}
					FileOutputStream fileOutputStream = new FileOutputStream(out);
					BufferedOutputStream bout = new BufferedOutputStream(fileOutputStream);
					int b;
					byte[] buffer=new byte[1024];
					while ((b = bin.read(buffer)) != -1) {
						bout.write(buffer,0,b);
					}
					bout.close();
					fileOutputStream.close();
				}
				bin.close();
				inputStream.close();
			} catch (Exception e) {
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
