package com.senierr.permissionmanager;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.senierr.lib.CheckCallback;
import com.senierr.lib.PermissionManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button checkBtn = (Button) findViewById(R.id.btn_check);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionManager.build(MainActivity.this)
                        .permissions(Manifest.permission.CAMERA)
                        .check(new CheckCallback() {
                            @Override
                            public void onAllGranted(List<String> permissions) {
                                for (String permission : permissions) {
                                    Log.e("MainActivity", "onAllGranted: " + permission);
                                }
                            }

                            @Override
                            public void onDenied(List<String> deniedByUserList, List<String> deniedNoRequestList) {
                                for (String s : deniedByUserList) {
                                    Log.e("MainActivity", "deniedByUserList: " + s);
                                }
                                for (String s : deniedNoRequestList) {
                                    Log.e("MainActivity", "deniedNoRequestList: " + s);
                                }
                            }
                        });
            }
        });
    }
}
