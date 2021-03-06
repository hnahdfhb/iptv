package com.yht.iptv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.yht.iptv.view.main.TexureViewActivity;

import java.lang.ref.WeakReference;

/**
 * Created by admin on 2017/11/21.
 */

public class WelcomeActivity extends AppCompatActivity {

    private MyHandler handler;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new MyHandler(this);

        handler.sendEmptyMessage(0);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler = null;
    }

    private static class MyHandler extends Handler {

        private WeakReference<WelcomeActivity> activity;

        public MyHandler(WelcomeActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WelcomeActivity welcomeFragment = this.activity.get();
            switch (msg.what) {
                case 0:
                    welcomeFragment.startActivity();
            }
        }
    }

    private void startActivity() {
        Intent intent;
        intent = new Intent(this, TexureViewActivity.class);
        startActivity(intent);
        finish();
    }

}
