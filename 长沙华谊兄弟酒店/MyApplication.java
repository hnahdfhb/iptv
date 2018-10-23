package com.yht.iptv.view;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.danikula.videocache.HttpProxyCacheServer;
import com.hisense.hotel.HotelSystemManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.squareup.leakcanary.LeakCanary;
import com.yht.iptv.BaseActivity;
import com.yht.iptv.utils.CrashHandler;
import com.yht.iptv.FileUtils;
import com.yht.iptv.utils.TypefaceUtil;
import com.yht.iptv.utils.Utils;

import org.xutils.x;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.prefs.Preferences;

import okhttp3.OkHttpClient;

/**
 *
 */

public class MyApplication extends Application {


    private static int TIME_OUT = 10 * 1000;
    private static Context context;

    /**
     * adb 开关 true开 false关.
     */
    private final boolean mAdbSwitch = true;

    /**
     * 待机模式, 0 待机模式  1 关屏模式
     */
    private final int mStandbyModel = 1;

    /**
     * 开机设置 0:直接启动模式 1:上次记忆的模式  2:待机模式
     */
    private final int mBootModel = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        CrashHandler.getInstance().init(getApplicationContext());
        //工具类
        Utils.init(this);
        LeakCanary.install(this);
        x.Ext.init(this);
        initLogUtils();
        initOkhttp();
        TypefaceUtil.replaceSystemDefaultFont(this, "font/font.ttf");


        //海信设置
        HotelSystemManager hotelSystemManager = new HotelSystemManager(this);
        boolean adb = hotelSystemManager.enableAdb(mAdbSwitch);
        Log.e("hx", "adb is open  :" + adb);

//        boolean adbPortset = hotelSystemManager.setAdbPort(9027);
//        Log.e("hx","adb_port is open :"+ adbPortset);

        int adbPort = hotelSystemManager.getAdbPort();
        Log.e("hx", "adb_port :" + adbPort);

        hotelSystemManager.setStandbyModel(mStandbyModel);
        hotelSystemManager.setBootModel(mBootModel);

        /**
         * 直播键 170
         影视键 131
         游戏键 209
         指南键 132
         */
        hotelSystemManager.setKeyLock(170, true);
        hotelSystemManager.setKeyLock(131, true);
        hotelSystemManager.setKeyLock(209, true);
        hotelSystemManager.setKeyLock(132, true);

        hotelSystemManager.setKeyLock(82, true);

    }

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        MyApplication app = (MyApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {

        String[] command_data = {"chmod", "777", FileUtils.getInfoPath("VideoCache")};
        ProcessBuilder builder_data = new ProcessBuilder(command_data);
        try {
            builder_data.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024 * 1024 * 1024)
                .fileNameGenerator(new MyFileNameGenerator())
                .cacheDirectory(new File(FileUtils.getInfoPath("VideoCache")))
                .maxCacheFilesCount(1)
                .build();
    }

    private void initLogUtils() {
        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix("LogUtils")
                .configShowBorders(true)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");

        // 支持写入日志到文件
        LogUtils.getLog2FileConfig().configLog2FileEnable(false);
    }

    private void initOkhttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //配置日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkHttp");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        //配置超时时间
        //全局的读取超时时间
        builder.readTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        //全局配置
        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0);
    }


    public static Context getAppContext() {
        return context;
    }


}
