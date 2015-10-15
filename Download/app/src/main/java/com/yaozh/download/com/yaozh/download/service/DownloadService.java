package com.yaozh.download.com.yaozh.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class DownloadService extends Service {
    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(DownloadService.this, "服务启动", Toast.LENGTH_SHORT).show();
        new Thread(){
            @Override
            public void run() {
                getFileInfo();
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }
    public void getFileInfo(){
        InputStream  is=null;
        String result=null;
        try {
            URL  url=new URL("http://static.yaozh.com/app.zip");
            HttpURLConnection  conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(300);
            conn.setReadTimeout(300);
            int statusCode=conn.getResponseCode();
            Log.i("contentType", conn.getContentType());
            if (statusCode==200){
                    is=conn.getInputStream();
                BufferedReader  reader=new BufferedReader(new InputStreamReader(is));
                StringBuffer  temp=new StringBuffer();
                String  line=reader.readLine();
                while (line!=null){
                    temp.append(line);
                    line=reader.readLine();
                }
                result=new String(temp.toString().getBytes(),"utf-8");
                reader.close();
            }
            conn.disconnect();
            String msg="{\"code\":"+statusCode+",\"content\":"+result+"}";
            Log.i("msg",msg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
