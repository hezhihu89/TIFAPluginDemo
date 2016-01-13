# TIFA Plugin 开发与集成

## GPS 定位插件开发

  在TIFA 框架中自带了一些常用的插件
 
  比如 : AlertDialog对话框弹窗...获取手机信息...获取地理位置  等等....
  
  这些在TIFA框架都是从html页面中产生交互事件, WebView 执行js代码,然后对本地方法的调用所实现的.

  webView 原生就是可以实现的js代码调用本地方法的,只需要将本地方法同过webView.addJavascriptInterface(Object object, String name);

  但是这样难免会产生一些漏洞,

##webView 通过注册方式,执行本地代码基本流程:
   
 在本地给 WebView 添加本地方法接口,提供接口名供js调用------>在html中触发事件------>事件通过js代码,执行添加接口时提供的接口名,即插件类的类名------>webView执行本地插件中的方法.



##

  TIFA 框架的设计就对这一些漏洞进行了封堵,然后封装了自己的方法,提升了webView的安全性

  接下来,就动手来设计一款 TIFA 的插件.

##一. 准备
  在这之前你的应用必须先集成 TIFA 框架,并能正常运行 : <a href="https://github.com/hezhihu89/TIFADemo
  ">TIFA 集成教程</a>  
  
  开发之前需要先明白 TIFA　插件的集成规则，即我们的插件类需要继承　WebViewJavaScriptPlugin 这样插件才能正常的被注册到安全的 TIFAWebView 中.

##二. 开始 
 
  在项目的包下面新建一个 plugin 文件夹.
  
  新建一个类,类名自定义(为了区分插件,我这里定义为 TIFALocationplugin) ,使该类继承自 WebViewJavaScriptPlugin
  ,实现 WebViewJavaScriptPlugin 的 onAttach() 抽象方法;
   
   ![Alt 插件开发](\image\24.png)

   OK 插件的类基本已经创建好了,接下来就是实现定位的功能.

##三. 插件功能实现
   插件功能基本就是安卓自己的本地功能 , 实现过程就是 webView通过webView.addJavascriptInterface(Object object, String name); 调用添加插件接口时的name,调用插件类.

   TIFA的webView实现过程也是一样,只不过 TIFA 框架对该实现过程进行了封装,变得更加安全.

##开始疯狂的代码
  定位功能 所需的类 :

         private LocationManager mLM;
         private Context mContext;
         private TIFAWebView webView;
         private String callBack;

         LocationManager  Location管理器
        
         Context :  上下文,用于获取LocationManager 管理器

         webView : TIFAWebView, 传递参数,Native 和 js 通讯

         callBack: 有回调方法的时候,作为key 返回给调用者
         

         
上下文的获取方式在 实现父类方法 onAttach(TIFAWebView tifaWebView),中 tifaWebView 提供了getContext() 方法获取

首先在onAttach()中 通过tifaWebView 获取上下文:

        mContext = tifaWebView.getContext();

创建getLocation(String string); 方法 申明 @JavascriptInterface 这样才能被webView调用执行.

    @JavascriptInterface
    public void getLocation(String string) {
        //获取LocationManager 对象
        if (mLM == null) {
            mLM = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        }

    }
     参数说明: 
          string : 因为获取到的数据需要返回给js,js再处理,所以js在调用这个方法之前会传一个String 参数过来,获取到数据之后同过TIFAWebView.
  


 使用单例模式,防止对象被多次创建
 ![Alt 插件开发](\image\25.png)

##开始定位
  因为获取地理位置是一个耗时的操作,所以必须在子线程中进行操作,不然会出现ANR crash问题
  
  获取地理位置 方法 requestLocationUpdates();

    mLM.requestLocationUpdates(String provider, long minTime, float minDistance,
            LocationListener listener);

     参数说明:
         provider: 使用什么方式获取位置
         minTime : 指定数据每次更新的最小时间
         minDistance: 两次定位之间的最小距离，超过最小距离放回一次数据
         LocationListtener: 监听器
  
在子线程中添加设置监听

(提示:::在android studio上面这里可能会报错!  添加权限即可---->添加完后依旧是报错的,可忽略)

 ![Alt 插件开发](\image\26.png)

 创建监听对象 LocationListener .

 ![Alt 插件开发](\image\27.png)
 
  实现回调方法 获取到了地理位置时,会通过 onLocationChanged(Location location) 中的location对象携带数据返回
  
![Alt 插件开发](\image\28.png)

## 添加回调 返回数据给html页面进行处理
   获取到了地理位置的经纬度之后,就可以同过TIFAWebView 封装的回调方法 tifaWebView.callJavaScriptCallback(), 将经纬度返回给html页面进行处理

    tifaWebView.callJavaScriptCallback(String callbackName,String...String);
    
    参数说明 :
      String callbackName : 调用 getLocation(String callbackName) 方法的时候传递过来的 callbackName , 作为key携带数据返回给html
     
      String ...String  : 可以是多个String 作为value值 返回给html调用者

![Alt 插件开发](\image\29.png)

这样只会 ,当android native获取到了地理位置数据之后,就会同过tifaWebView.callJavaScriptCallback(), 将数据返回给 webView中的 html 页面 通过 js 进行处理.

OK ---->TIFA的 plugun 就这么简单的做好了

##流程总结

#创建plugin 类  ------->  继承WebViewJavaScriptPlugin  ------>实现onAttach(TIFAWebView tifaWebView)方法------->暴露方法给外界调用,需要声明为 @JavascriptInterface 方法 才能被 js 调用------->如果有回调 则使用tifaWebView.callJavaScriptCallback(String s,String...string)