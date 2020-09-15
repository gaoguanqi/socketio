package com.maple.socketio;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.DeviceUtils;
import com.maple.socketio.app.MyApplication;
import com.maple.socketio.utils.LogUtils;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {


    private Button btnSend;
    private Socket mSocket;

    private final String IO_SERVER_URL = "http://103.107.190.144:3120";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = DeviceUtils.getAndroidID();
                mSocket.send(uid);
            }
        });

        try {
            //1.初始化socket.io，设置链接
            IO.Options opts = new IO.Options();
            mSocket = IO.socket(IO_SERVER_URL,opts);
            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    boolean isConnected = mSocket.connected();
                    LogUtils.logGGQ("EVENT_CONNECT-->>>" + isConnected);
                    String uid = DeviceUtils.getAndroidID();
                    mSocket.send(uid);
                }
            }).on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    LogUtils.logGGQ("EVENT_CONNECTING");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    LogUtils.logGGQ("EVENT_DISCONNECT");
                }
            }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    LogUtils.logGGQ("EVENT_ERROR");
                }
            }).on("msg", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Object data = args[0];
                            LogUtils.logGGQ("msg:===" + data.toString());
                        }
                    });
                }
            });
            mSocket.connect();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }
}