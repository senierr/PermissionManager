package com.senierr.permissionmanager;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.senierr.permission.PermissionManager;
import com.senierr.permission.RequestCallback;

import org.jetbrains.annotations.NotNull;

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
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA)
                                .check());

                PermissionManager.with(MainActivity.this)
                        .permissions(
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA)
                        .request(new RequestCallback() {
                            @Override
                            public void onAllGranted() {
                                Log.e("MainActivity", "onAllGranted");
                            }

                            @Override
                            public void onDenied(@NotNull List<String> nextAskList, @NotNull List<String> neverAskList) {
                                for (String s : nextAskList) {
                                    Log.e("MainActivity", "nextAskList: " + s);
                                }
                                for (String s : neverAskList) {
                                    Log.e("MainActivity", "neverAskList: " + s);
                                }
                            }
                        });
            }
        });
    }
}
