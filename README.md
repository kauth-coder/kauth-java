# KAuth Java SDK

本SDK由 Java8 开发的网络验证客户端 SDK，用于 Java、Android 构建您的收费软件。
SDK 提供了丰富的功能模块，通过 `KAuthApi.getKauthApiService()` 获取服务实例后即可调用：

- **用户认证模块**
    - `getCaptcha`: 获取图形验证码
    - `pwdLogin`: 用户密码登录
    - `kaLogin`: 通过卡密进行登录
    - `loginOut`: 退出登录
    - `trialLogin`: 试用登录
    - `register`: 用户注册
    - `recharge`: 账号充值
    - `rechargeKa`: 以卡充卡
    - `changePassword`: 修改密码
    - `unbindDevice`: 未登录状态下，账密解绑设备
    - `unbindDeviceKaPwd`: 未登录状态下，卡密解绑设备
    - `userInfo`: 获取用户信息
    - `pong`: 心跳
- **配置管理模块**
    - `updateUserConfig`: 更新用户配置
    - `getUserConfig`: 获取用户配置
    - `updateKaConfig`: 更新卡密配置
    - `getKaConfig`: 获取卡密配置
- **程序管理模块**
    - `getProgramDetail`: 获取程序详情
    - `getServerTime`: 获取服务器时间
- **脚本错误模块**
    - `reportScriptError`: 报告脚本错误
- **设备管理模块**
    - `cardUnBindDevice`: 卡密解绑设备
- **远程控制模块**
    - `getRemoteVar`: 获取远程变量
    - `getRemoteData`: 获取远程数据
    - `addRemoteData`: 添加远程数据
    - `updateRemoteData`: 更新远程数据
    - `deleteRemoteData`: 删除远程数据
    - `callFunction`: 调用函数
    - `getNewestScript`: 获取最新脚本
    - `scriptDownloadV2`: 脚本下载V2

## 加载依赖

您可以在 [Maven Central](https://central.sonatype.com/artifact/cn.kauth/kauth-java/) 查看最新版本。

### Maven

在 `pom.xml` 中添加：

```xml

<dependency>
    <groupId>cn.kauth</groupId>
    <artifactId>kauth-java</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Gradle

在 `build.gradle` 中添加：

```gradle
implementation 'cn.kauth:kauth-java:1.0.1'
```

## 初始化

在使用 SDK 之前，需要先进行初始化配置。请在程序启动时调用 `KauthCoreCore.init` 方法。

```java

import cn.kauth.sdk.info.request.InitConfigReq;

public class Main {
    public static void main(String[] args) {
        // 创建初始化配置对象
        InitConfigReq initConfigReq = new InitConfigReq();
        // 设置 API 域名 (例如: https://api.kauth.cn)
        initConfigReq.setApiDomain("https://api.kauth.cn");
        // 设置程序 ID (从后台获取: 程序管理 -> 程序列表)
        initConfigReq.setProgramId(1959821336266936321L);
        // 设置程序密钥 (从后台获取: 程序管理 -> 程序列表)
        initConfigReq.setProgramSecret("F77VzI7UWAElpWrz");
        // 设置商户公钥 (从后台获取：系统设置 -> 密钥配置 -> RSA公钥 -> PKCS#8格式 )
        initConfigReq.setMerchantPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIpy3ae27yDJOUd5rW/S6tUbAmt/AqJm+VPonT9WJn5VME4FkYJUwdBmIWpzANVQmU+7CA3wv5eVFIOW0xMv9EyoFWDRR24Jt/hgDsZQtUPMaZPivKWxx2S4n4SJWWrGdIRkdC3+fmxrEri1qYicq8PO7mDIrwPR2I0USoKKOFMwIDAQAB");
        // 设置签名方式 (RSA)
        initConfigReq.setSignType(KauthSignEnums.SIGN_TYPE_RSA);
        // 初始化 SDK
        KauthApi.init(initConfigReq);
    }
}
```

## 快速开始

```java
 public static void main(String[] args) throws IOException {
    // 创建初始化配置对象
    InitConfigReq initConfigReq = new InitConfigReq();
    // 设置 API 域名 (例如: https://api.kauth.cn)
    initConfigReq.setApiDomain("https://api.kauth.cn");
    // 设置程序 ID (从后台获取: 程序管理 -> 程序列表)
    initConfigReq.setProgramId(1959821336266936321L);
    // 设置程序密钥 (从后台获取: 程序管理 -> 程序列表)
    initConfigReq.setProgramSecret("F77VzI7UWAElpWrz");
    // 设置商户公钥 (从后台获取：系统设置 -> 密钥配置 -> RSA公钥 -> PKCS#8格式 )
    initConfigReq.setMerchantPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIpy3ae27yDJOUd5rW/S6tUbAmt/AqJm+VPonT9WJn5VME4FkYJUwdBmIWpzANVQmU+7CA3wv5eVFIOW0xMv9EyoFWDRR24Jt/hgDsZQtUPMaZPivKWxx2S4n4SJWWrGdIRkdC3+fmxrEri1qYicq8PO7mDIrwPR2I0USoKKOFMwIDAQAB");
    // 设置签名方式 (RSA)
    initConfigReq.setSignType(KauthSignEnums.SIGN_TYPE_RSA);
    // 初始化 SDK
    KauthApi.init(initConfigReq);
    KauthApiService kauthApiService = KauthApi.getKauthApiService();
    KaLoginRequest kaLoginRequest = new KaLoginRequest();
    kaLoginRequest.setKaPwd("bch1n8vj"); //卡密
    kaLoginRequest.setDeviceId("123456"); //设备 ID，请自行依据运行的环境（mac、Window、Linux 等等生成设备 Id）
    kaLoginRequest.setPlatformType("java"); //当前平台，默认Java，可自行定义
    Call<ApiResult<LoginResponse>> apiResultCall = kauthApiService.kaLogin(kaLoginRequest);
    Response<ApiResult<LoginResponse>> execute = apiResultCall.execute();
    ApiResult<LoginResponse> body = execute.body();
    if (Objects.isNull(body)) {
        System.out.println("和服务器通讯失败");
        return;
    }
    if (Objects.equals(body.getSuccess(), true)) {
        System.out.println("卡密登录成功:" + new Gson().toJson(body));
        //保存 token 到内存、保存登录信息到内存
        SessionManager.setAccessToken(body.getData().getToken());
        SessionManager.setUserInfo(body.getData());
    }
    if (Objects.equals(body.getSuccess(), false)) {
        System.out.println("卡密登录失败:" + body.getMsg());
    }
}
```

## 全局异常上报

```java

String appName = "Java-APP1"; //当前应用名称，自定义
String deviceId = "123456"; //设备 ID，请自行依据运行的环境（mac、Window、Linux 等等生成设备 Id）
GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler(appName, deviceId);
Thread.

setDefaultUncaughtExceptionHandler(globalExceptionHandler);


//当有线程产生 Exception 异常的时候，将会上报给服务器
```

## 自动心跳

```java
        int maxConnFail = 3; //允许最大失败次数（比如网络抖动等可能会发生心跳失败，你可以输入一个你能接受的最大失败次数，比如 10次）
        KauthApi.

startAutoPong(maxConnFail, new PongCallback() {
    @Override
    public void callback (PongFailEnums pongFailEnums, String rason){
        System.out.println("心跳失败:" + pongFailEnums.getValue() + "," + rason);
    }
});
```
