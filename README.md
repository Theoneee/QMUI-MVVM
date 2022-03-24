# QMUI-MVVM

[![](https://jitpack.io/v/Theoneee/QMUI-MVVM.svg)](https://jitpack.io/#Theoneee/QMUI-MVVM)
[![](https://img.shields.io/badge/QQ群-761201022-red.svg)](https://jq.qq.com/?_wv=1027&k=r3OpQ2GX)

QMUI + Jetpack 组件封装的一个MVVM框架

## 必须

1、配置jitpack到项目的build.gradle文件中

```java
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
2、添加依赖

```java
dependencies {
    // 二选一
    // 1.core  包含了base + common
    implementation "com.github.Theoneee.QMUI-MVVM:core:1.0.0"
    // 2.单独使用
    // 只使用 base
    implementation "com.github.Theoneee.QMUI-MVVM:base:1.0.0"
    // 或者 common
    implementation "com.github.Theoneee.QMUI-MVVM:common:1.0.0"
}
```

# Demo演示

以`core`为基础，创建的一个 [玩Android](http://wanandroid.com/) App。

![APK下载](https://qr.api.cli.im/newqr/create?data=http%3A%2F%2Ffile.cudag.com%2F2022%2F03%2F24%2Fff9f53ed40de26de809f51f45f3ec478.apk&level=H&transparent=false&bgcolor=%23FFFFFF&forecolor=%23000&blockpixel=12&marginblock=2&logourl=&logoshape=no&size=300&bgimg=&text=&fontsize=30&fontcolor=&fontfamily=msyh.ttf&incolor=%231694e3&outcolor=&qrcode_eyes=pin-3.png&background=images%2Fbackground%2Fbg25.png&wper=0.84&hper=0.84&tper=0.08&lper=0.08&eye_use_fore=&qrpad=10&embed_text_fontfamily=simhei.ttc&body_type=0&qr_rotate=0&logo_pos=0&kid=cliim&key=18c7cb0b9b49f0123750f48a3c0478e6)

<img src="https://images.gitee.com/uploads/images/2021/0331/102531_dd1b7778_2286054.png" width="540" height="1116" alt="首页"/><br/>

![项目](https://images.gitee.com/uploads/images/2021/0331/102553_02e4fe39_2286054.png "S10331-10010060(1).png")

![广场](https://images.gitee.com/uploads/images/2021/0331/102613_f127785c_2286054.png "S10331-10010786(1).png")

![体系](https://images.gitee.com/uploads/images/2021/0331/102637_bc67da88_2286054.png "S10331-10011338(1).png")

![公众号](https://images.gitee.com/uploads/images/2021/0331/102652_f1253db5_2286054.png "S10331-10011856(1).png")

![我的](https://images.gitee.com/uploads/images/2021/0331/102714_bd4b64e2_2286054.png "S10331-10012315(1).png")

![积分排行](https://images.gitee.com/uploads/images/2021/0331/102733_4430f4b7_2286054.png "S10331-10013191(1).png")

![积分记录](https://images.gitee.com/uploads/images/2021/0331/102748_1e0b447a_2286054.png "S10331-10013685(1).png")

![搜索](https://images.gitee.com/uploads/images/2021/0331/102759_3025f5e8_2286054.png "S10331-10015798(1).png")
