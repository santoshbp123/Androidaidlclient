package com.example.androidaidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.alpharaysaidlserver.IAIDLColorInterface;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    IAIDLColorInterface iaidlColorInterface;



    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iaidlColorInterface=IAIDLColorInterface.Stub.asInterface(iBinder);
            Log.d("iaidlColorInterface->",iaidlColorInterface+"");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= (TextView)findViewById(R.id.text);
        Intent intent = new Intent("AIDLColorService");
        intent.setPackage("com.example.alpharaysaidlserver");
        bindService(intent,mConnection,BIND_AUTO_CREATE);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int color = iaidlColorInterface.getCustomColor();
                    v.setBackgroundColor(color);
                } catch (RemoteException e) {
                }
            }
        });
    }
}