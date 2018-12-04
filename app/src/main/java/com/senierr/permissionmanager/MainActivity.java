package com.senierr.permissionmanager;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.senierr.permission.PermissionManager;
import com.senierr.permission.RequestCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, new TestFragment())
                .commitAllowingStateLoss();

        Button btnRequest = findViewById(R.id.btn_request);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("MainActivity", "Check permissions: " +
                        PermissionManager.with(MainActivity.this)
                                .permissions(
                                        Manifest.permission.READ_PHONE_STATE,
                                        Manifest.permission.CAMERA)
                                .check());

                PermissionManager.with(MainActivity.this)
                        .permissions(
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.CAMERA)
                        .request(new RequestCallback() {
                            @Override
                            public void onAllGranted() {
                                Log.e("MainActivity", "onAllGranted");
                            }

                            @Override
                            public void onDenied(List<String> deniedAndNextAskList, List<String> deniedAndNeverAskList) {
                                for (String s : deniedAndNextAskList) {
                                    Log.e("MainActivity", "deniedAndNextAskList: " + s);
                                }
                                for (String s : deniedAndNeverAskList) {
                                    Log.e("MainActivity", "deniedAndNeverAskList: " + s);
                                }
                            }
                        });
            }
        });
    }
}
