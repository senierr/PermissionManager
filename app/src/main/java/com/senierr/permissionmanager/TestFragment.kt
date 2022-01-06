package com.senierr.permissionmanager

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.senierr.permission.*
import kotlinx.coroutines.launch

class TestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val btnRequest = view?.findViewById<Button>(R.id.btn_request)
        btnRequest?.setOnClickListener {
            Log.e("TestFragment",
                    "Check permissions: " +
                            checkPermissions(
                                    Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA
                            )
            )

//            requestPermissions(
//                    Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.CAMERA
//            ) {
//                Log.e("TestFragment", "onAllGranted")
//            }

            lifecycleScope.launch {
                val result = requestPermissions(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                when (result) {
                    is RequestResult.Granted -> {
                        Log.e("TestFragment", "Granted")
                    }
                    is RequestResult.Denied -> {
                        Log.e("TestFragment", "Denied")
                        for (s in result.nextAsk) {
                            Log.e("TestFragment", "nextAskList: $s")
                        }
                        for (s in result.neverAsk) {
                            Log.e("TestFragment", "neverAskList: $s")
                        }
                    }
                }
            }

//            requestPermissions(
//                    Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.CAMERA,
//                    onGrantedCallback = {
//                        Log.e("TestFragment", "onAllGranted")
//                    },
//                    onDeniedCallback = { nextAskList, neverAskList ->
//                        for (s in nextAskList) {
//                            Log.e("TestFragment", "nextAskList: $s")
//                        }
//                        for (s in neverAskList) {
//                            Log.e("TestFragment", "neverAskList: $s")
//                        }
//                    })
        }
    }
}
