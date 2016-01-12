#TIFA Android快速集成 
   
##一.准备:
 下载:<a href="http://tifa.mobi/downloads/TIFA.jar">Android SDK</a>

 创建工程项目:

 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/1.png)

 在TIFA 框架中已经集成了主界面Activity 

 所以新创建的MainActivity 将会被当初始化引导界面.一般用来做 Splash 启动界面

 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/2.png)

 创建完后:
 
 选择查看工程模式为 project
 
 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/3.png)

 项目目录结构基本如下

 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/4.png)

 
##  导入 SDK

 将下载好的 TIFA.jar 包,拷贝到 app/libs 路径下

 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/5.png)
 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/6.png)

 将jar包 添加到项目依赖

#两种方法 :

##1.单击右键项目中的 TIFA.jar 选择Add As Library

 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/7.png)

##2.单击右键项目名 选择 Open Module Setting

 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/8.png)

##2.1 选择项目所在的 Modules  (默认app) 选择Dependcies 选择 "+" 号 添加依赖--选择 File dependency

 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/9.png)

##2.2 打开libs 文件夹 选择TIFA.jar --OK  --OK

 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/10.png)

 添加TIFA.jar依赖后,等待 studio 自动编译完成
 在项目的类中输入 "TIFA" android studio 智能联想出来了TIFA框架,说明依赖成功,接下开就能使用 TIFA SDK 中的API
 ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/11.png)



## 注册 TIFA云平台 管理账号 获取  "应用key" :

  注册地址 : <a href="http://cloud.tifa.mobi/sign-up.html">进入注册</a>
  
  登录TIFA 云平台管理 创建新应用 

  ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/12.png)

  创建完成后 在"我的应用" 点击进入创建的应用 获取到APP创建信息 

  应用Key 就在这里

  ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/13.png)


# 资源打包
   
   下载html 测试示例 : <a href="http://tifa.mobi/downloads/TIFA_js_example.zip">资源下载</a>
   
   将资源解压之后, TIFA_js_example 目录下资源如下
   
   ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/17.png)

   在本路径下,将资源压缩--->将压缩后的资源直接拖入 TIFA 云平台当前项目的热加固中

   ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/18.png)
   
   下载加固后的资源 - app.hr --->
     ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/19.png)

##回到项目
  在项目main文件夹下创建资源目录 "assets"
  ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/20.png)

---------------------------创建 assets--------------------------------

  ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/21.png)

  将加固后下载好的app.hr放入assets 目录

---------------------------添加项目资源--------------------------------

  ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/22.png)



 
# 二 .开始愉快的 TIFA 之旅: 
  
##打开 SplashiActivity.java

# 1 初始化TIFA 框架
   在 onCreate(); 方法中 init(); 初始化 TIFA 框架
  <code>

     TIFA.init(Context context , String appKey);
     
     参数说明:
           context: 上下文;
           appkey : 在TIFA 平台创建应用后生成的应用Key 用于对资源的校验

  </code>   

#2 监听资源部署
  <code>

    TIFA.deployResource(OnResourceDeployListener listener);

    参数说明: 

      OnResourceDeployListener() : 资源部署的监听器

      如果需要在资源部署时做出相应的操作 则实现该监听
    
      如果在资源部署时不进行任何操作 资源部署完成后 直接进入 APP 主界面
    
      就只需要实现 AbsOnResourceDeployListener();

    参数说明:

      AbsOnResourceDeployListener() :
      
      对资源部署的监听,listener中只需实现资源部署完毕的方法

  </code>

# <a href="#asInit" name="init">初始化 COPY 代码</a>

  ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/14.png)

#配置清单文件 添加权限和Activity界面
 
##添加权限
   在 TAFA 中的一些操作会涉及到 uses-permission，
   为了保证 TIFA　的正常运行，需要在　AndroidManifest.xml　 清单文件中添加权限信息
# <a href="#asPermission" name="permission">权限 COPY 代码</a>

  ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/15.png)　　

##添加Activity
   在 TIFA 中有2个Activity 作为资源的载体,

   TIFAHoneActivity 

   和 

   TIFAWebViewActivity

   这都需要在清单文件中声明这两个Activity

   ![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/16.png)　


##啊哈 可以编译运行咯 赶快体验TIFA 给你带来不一样的APP体验吧
![Alt 创建项目](https://github.com/hezhihu89/TIFADemo/blob/master/image/23.png)　

#相关代码

 # init相关代码
##<a href="#init" name="asInit">回到 init 教程</a>

 <code >

    import android.support.v7.app.AppCompatActivity;
	import android.os.Bundle;
	
	import net.siysoft.tifa.app.AbsOnResourceDeployListener;
	import net.siysoft.tifa.app.TIFA;
	
	public class SplashActivity extends AppCompatActivity {

    //应用key:
    private final String APP_KEY = "00e6389bdafe76a66d37defbd14df997c672c646154013a843108140c01f88ee8e899c8c2f995a5b2332eeda9d1aa9b69fd2fa9d71877afe1b80b2b1b9982d20fcf6a1e35ee88fa8ad2ac5a45073eb192b23c1c211b0e0d444ebd43f8f97427f93477386fcb5a96afc2bea7b45e388bf045fc73d91ec350a54c0e01fcb5323c3c1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //初始化TIFA 框架
        TIFA.init(this, APP_KEY);

        //监听资源部署过程
        TIFA.deployResource(new AbsOnResourceDeployListener() {
            @Override
            public void onResourceDeployComplete() {
                TIFA.goMainPage(SplashActivity.this);
                finish();
            }
        });
      }
    }

 </code>

# permission 相关代码
##<a href="#permission" name="asPermission">回到 permission 教程</a>

<code>

    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="net.siysoft.tifa.tifademo">
    <!-- 网络权限 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <!-- 接收短信权限 -->
    <uses-permission android:name="android.permission.SEND_SMS" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".SplashActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name="net.siysoft.tifa.app.TIFAHomeActivity"
            android:screenOrientation="portrait" />
        <activity
            android:name="net.siysoft.tifa.app.TIFAWebViewActivity"
            android:screenOrientation="portrait" />
      </application>
    </manifest>

</code>