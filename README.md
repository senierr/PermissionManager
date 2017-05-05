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
compile 'com.github.senierr:PermissionManager:1.0.2'
```

### 3. 检查权限

```java
PermissionManager
        .build(activity)
        .permissions(String... permissions)
        .check(new CheckCallback() {
            ...
        });
```

**没了？对，没了！就是这么简单！**

## 回调说明

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

## 其他说明

关于6.0动态权限申请相关的文章很多，这里就不多介绍了。

这里简单介绍下使用中的具体问题：

### 1. 何时申请

* 具体使用某功能时；
* 引导页开始时；
* 具体页面开始时；
* APP切换至前台时；
* ......

根据功能权限的重要度，选择合适的时机进行申请。

### 2. 应用运行中，权限被改变

这一情景不多，但也会出现：
>应用运行过程中，用户Home键，切换至设置或其他，禁止了某项权限。用户又切回了应用，发现一些功能无法运行。

这是一件比较尴尬的事...

还好，Google考虑到了这一情景，并对其进行了处理：

>当某个应用运行中被改变了权限，此应用进程会被杀除，并保存Task。当用户重新切换至此应用时，会恢复Task列表。

简而言之，当你的应用权限改变，并重新切回后，所有Task页面生命周期会重新执行一遍。

所以，咳咳咳...少年，回到问题1，选择合适的时机进行申请吧！
