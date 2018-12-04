package com.senierr.permissionmanager;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.senierr.permission.PermissionManager;
import com.senierr.permission.RequestCallback;

import java.util.List;

public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View rootView = getView();
        if (rootView == null) return;

        Button btnRequest = rootView.findViewById(R.id.btn_request);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TestFragment", "Check permissions: " +
                        PermissionManager.with(getActivity())
                        .permissions(
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.CAMERA)
                        .check());

                PermissionManager.with(getActivity())
                        .permissions(
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.CAMERA)
                        .request(new RequestCallback() {
                            @Override
                            public void onAllGranted() {
                                Log.e("TestFragment", "onAllGranted");
                            }

                            @Override
                            public void onDenied(List<String> deniedAndNextAskList, List<String> deniedAndNeverAskList) {
                                for (String s : deniedAndNextAskList) {
                                    Log.e("TestFragment", "deniedAndNextAskList: " + s);
                                }
                                for (String s : deniedAndNeverAskList) {
                                    Log.e("TestFragment", "deniedAndNeverAskList: " + s);
                                }
                            }
                        });
            }
        });
    }
}
