package com.example.user.stuqexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadPicThread loadPicThread = new LoadPicThread();
        loadPicThread.start();
    }

    private class LoadPicThread extends Thread {
        //private boolean needStop = false;

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 100; i++) {//100个5分钟
                try {
                    Thread.sleep(1000 * 60 *5);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
