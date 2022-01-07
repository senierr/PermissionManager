# PermissionManager

[![](https://img.shields.io/badge/release-v2.1.0-blue.svg)](https://github.com/senierr/PermissionManager)
[![](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/senierr/PermissionManager)

Android 6.0+ 动态权限申请

## 基本用法

**注意**：使用此库，`minSdkVersion` 必须 `>= 11`

### 导入仓库

```
implementation 'io.github.senierr:permission:2.1.0'
```

### 检查权限

##### 在Java中：
```
// 权限检测
PermissionManager.with(activity)
        .permissions(String... permissions)
        .check();

// 申请权限
PermissionManager.with(activity)
        .permissions(String... permissions)
        .request(new RequestCallback() {
            ...
        });
```

##### 在Kotlin中，可使用**FragmentActivity**和**Fragment**的扩展函数：
```
// 检查权限
checkPermissions(permissions)

// 申请权限       
requestPermissions(permissions) {
    // onAllGranted
    ...
}

或者
requestPermissions(permissions,
    onGrantedCallback = {
        ...
    },
    onDeniedCallback = { nextAskList, neverAskList ->
        ...
    })
    
协程中使用
lifecycleScope.launch {
    val result = requestPermissions(permissions)
    when (result) {
        is RequestResult.Granted -> { }
        is RequestResult.Denied -> { }
    }
}
```

## 申请回调

```
/**
 * 所有请求权限通过
 */
public abstract void onAllGranted();

/**
 * 权限未通过
 *
 * @param deniedAndNextAskList 拒绝，下次询问的权限列表
 * @param deniedAndNeverAskList 拒绝，不再询问的权限列表
 */
public void onDenied(List<String> deniedAndNextAskList, List<String> deniedAndNeverAskList) {}
```

## 危险权限表

```
group:android.permission-group.CONTACTS
    permission:android.permission.WRITE_CONTACTS
    permission:android.permission.GET_ACCOUNTS
    permission:android.permission.READ_CONTACTS

group:android.permission-group.PHONE
    permission:android.permission.READ_CALL_LOG
    permission:android.permission.READ_PHONE_STATE
    permission:android.permission.CALL_PHONE
    permission:android.permission.WRITE_CALL_LOG
    permission:android.permission.USE_SIP
    permission:android.permission.PROCESS_OUTGOING_CALLS
    permission:com.android.voicemail.permission.ADD_VOICEMAIL

group:android.permission-group.CALENDAR
    permission:android.permission.READ_CALENDAR
    permission:android.permission.WRITE_CALENDAR

group:android.permission-group.CAMERA
    permission:android.permission.CAMERA

group:android.permission-group.SENSORS
    permission:android.permission.BODY_SENSORS

group:android.permission-group.LOCATION
    permission:android.permission.ACCESS_FINE_LOCATION
    permission:android.permission.ACCESS_COARSE_LOCATION

group:android.permission-group.STORAGE
    permission:android.permission.READ_EXTERNAL_STORAGE
    permission:android.permission.WRITE_EXTERNAL_STORAGE

group:android.permission-group.MICROPHONE
    permission:android.permission.RECORD_AUDIO

group:android.permission-group.SMS
    permission:android.permission.READ_SMS
    permission:android.permission.RECEIVE_WAP_PUSH
    permission:android.permission.RECEIVE_MMS
    permission:android.permission.RECEIVE_SMS
    permission:android.permission.SEND_SMS
    permission:android.permission.READ_CELL_BROADCASTS
```