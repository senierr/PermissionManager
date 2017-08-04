package com.senierr.permissionmanager;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.senierr.permission.CheckCallback;
import com.senierr.permission.PermissionManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("MainActivity", "onCreate");

        Button checkBtn = (Button) findViewById(R.id.btn_check);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("MainActivity", "check" + PermissionManager.with(MainActivity.this)
                        .permissions(
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.CAMERA)
                .check());

                PermissionManager.with(MainActivity.this)
                        .permissions(
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.CAMERA)
                        .request(new CheckCallback() {
                            @Override
                            public void onAllGranted() {
                                Log.e("MainActivity", "onAllGranted");
                            }

                            @Override
                            public void onDenied(List<String> deniedWithNextAskList, List<String> deniedWithNoAskList) {
                                for (String s : deniedWithNextAskList) {
                                    Log.e("MainActivity", "deniedWithNextAskList: " + s);
                                }
                                for (String s : deniedWithNoAskList) {
                                    Log.e("MainActivity", "deniedWithNoAskList: " + s);
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MainActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity", "onDestroy");
    }
}
