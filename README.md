# 云片 行为验证 Android SDK 接入指南

## SDK 集成

### 远程依赖集成
需要确保主项目 build.gradle 文件中声明了 jcenter() 配置

```
implementation 'com.yunpian:captcha:1.0.4'
```

### 手动导入SDK
将获取的 sdk 中的 aar 文件放到工程中的libs文件夹下，然后在 app 的 build.gradle 文件中增加如下代码

```
repositories {
    flatDir {
        dirs 'libs'
    }
}
```

在 dependencies 依赖中增加对 aar 包的引用

```
implementation(name: 'qipeng-captcha-v1.0.4', ext: 'aar') // aar 名称和版本号以下载下来的最新版为准
```

## 开始使用

### 初始化
使用开发者自己的 captchaId 进行初始化 SDK
```
QPCaptcha.getInstance().init(context, "captchaId");
```

### 参数配置（可选）
```
QPCaptchaConfig config = new QPCaptchaConfig.Builder(contxt)
                .setAlpha(0.7f) // 视图透明度
                .setWidth() // 视图宽度
                .setLangPackModel() // 界面语言配置
                .showLoadingView() // 是否显示加载
                .setLang(langEnCb.isChecked() ? QPCaptchaConfig.LANG_EN : QPCaptchaConfig.LANG_ZH) // 语言设置中文或者英文，默认中文
                .setCallback() // 设置回调接口
                .build();
```

### 开始验证
```
QPCaptchaConfig config = new QPCaptchaConfig.Builder(contxt)
                ... // 开发者根据需要自行配置参数
                .build();
QPCaptcha.getInstance().verify(config);
```

### 自定义语言显示（可选）
SDK 默认支持中文及英文，如果需要支持其它语言，需要自定义语言显示
```
JSONObject  langPackModel = new JSONObject();
                        try {
                            langPackModel.put("YPcaptcha_02", "请按顺序点击：");
                            langPackModel.put("YPcaptcha_03", "向右拖动滑块填充拼图");
                            langPackModel.put("YPcaptcha_04", "验证失败，请重试");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
QPCaptchaConfig config = new QPCaptchaConfig.Builder(contxt)
					...
                .setLangPackModel(langPackModel) // 界面语言配置
	             ...
                .build();
QPCaptcha.getInstance().verify(config);
```

## 效果演示
### 滑动拼图
![](https://github.com/yunpian/yunpian-captcha-demo-android/blob/master/image/1.png)
### 图标点选
![](https://github.com/yunpian/yunpian-captcha-demo-android/blob/master/image/2.png)
### 文字点选
![](https://github.com/yunpian/yunpian-captcha-demo-android/blob/master/image/3.png)
