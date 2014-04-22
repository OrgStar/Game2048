Game2048
===========

# **开源中国社区 Android 客户端项目简析** #

*注：本文假设你已经有Android开发环境*

启动Eclipse，点击菜单并导入Android客户端项目，请确保你当前的Android SDK是最新版。<br>
如果编译出错，请修改项目根目录下的 project.properties 文件。<br>
推荐使用Android 4.0 以上版本的SDK,请使用JDK1.6编译：

> target=android-15

**本项目采用 GPL 授权协议，欢迎大家在这个基础上进行改进，并与大家分享。**

下面将简单的解析下项目：

## **一、项目的目录结构** ##
> 根目录<br>
> ├ src<br>
> ├ libs<br>
> ├ res<br>
> ├ AndroidManifest.xml<br>
> ├ LICENSE.txt<br>
> ├ proguard.cfg<br>
> └ project.properties<br>

**1、src目录**<br>
src目录用于存放项目的包及java源码文件。

下面是src目录的子目录：
> src<br>
> ├ com.star.game2048.MainActivity<br>
> ├ com.star.game2048.CornerTextView<br>
> ├ com.star.game2048.GameView<br>
> └ com.star.game2048.Card<br>

- com.star.game2048.MainActivity   — 主程序
- com.star.game2048.CornerTextView — 圆角TextView类
- com.star.game2048.GameView	   — 游戏界面类扩展GridView
- com.star.game2048.Card		   — 卡牌类


**2、res目录**<br>
res目录用于存放项目的图片、布局、样式等资源文件。

下面是res目录的子目录：
> res<br>
> ├ color<br>
> ├ drawable<br>
> ├ drawable-hdpi<br>
> ├ drawable-ldpi<br>
> ├ drawable-mdpi<br>
> ├ layout<br>
> ├ menu<br>
> ├ values<br>
> └ xml<br>

- color — 颜色
- drawable/drawable-hdpi/drawable-ldpi/drawable-mdpi — 图标、图片
- layout — 界面布局
- menu — 菜单
- values — 字符串定义
- xml — 系统设置

**3、AndroidManifest.xml**<br>
AndroidManifest.xml用于设置应用程序的版本、主题、用户权限及注册Activity等。

## **二、项目的功能流程** ##

#### 1、APP启动流程 ####
AndroidManifest.xml注册的启动界面为"MainActivity"，具体文件为com/star/game2048/MainActivity.java文件。
从而activity_main.xml被加载。CornerTextView.java、GameView.java会相继被执行。GameView.java-->addCards-->
Card.java
