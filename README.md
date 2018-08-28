# PermissionManager

[![](https://jitpack.io/v/senierr/PermissionManager.svg)](https://jitpack.io/#senierr/PermissionManager)

Android 6.0+ 动态权限申请

## 基本用法

**注意**：使用此库，`minSdkVersion` 必须 `>= 11`

### 1. 导入仓库

```java
maven { url 'https://jitpack.io' }
```

### 2. 添加依赖

```java
compile 'com.github.senierr:PermissionManager:<release_version>'
```

### 3. 检查权限

```java
// 权限同步检测
PermissionManager
        .with(activity)
        .permissions(String... permissions)
        .check();

// 权限请求
PermissionManager
        .with(activity)
        .permissions(String... permissions)
        .request(new CheckCallback() {
            ...
        });
```

## 申请回调

```java
/**
 * 所有请求权限通过
 */
public abstract void onAllGranted();

/**
 * 权限未通过
 *
 * @param deniedWithNextAskList 拒绝，下次询问的权限列表
 * @param deniedWithNoAskList 拒绝，不再询问的权限列表
 */
public void onDenied(List<String> deniedWithNextAskList, List<String> deniedWithNoAskList) {}
```

## 危险权限表

```java
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