package cn.th.phonerf.application;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

import cn.th.phonerf.application.CrashHandler;

/**
 * Created by
 */
public class MyApplication extends Application {
    private static Context context;
    //public LocationService locationService;
    //public BDListener bdListener;

    //是否模拟充值
    public static boolean isSimulation = true;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        Fresco.initialize(this);
        //isSimulation = true;

        //极光统计初始化
        //JAnalyticsInterface.setDebugMode(true);
        //JAnalyticsInterface.init(this);
        //JAnalyticsInterface.initCrashHandler(this);

        //百度地图初始化
        //locationService = new LocationService(getApplicationContext());
        //获取locationService实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationService实例的
        //bdListener = new BDListener(getApplicationContext(), locationService);
        //locationService.setLocationOption(locationService.getDefaultLocationClientOption());


        // Crash捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        // 图片缓存
        /*File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "CacheDir");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(1000, 450)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .discCacheExtraOptions(1000, 450, Bitmap.CompressFormat.JPEG, 75, null)
                // Can slow ImageLoader, use it carefully (Better don't use
                // it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // You can pass your own memory cache
                // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                // .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                // .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                // 缓存的文件数量
                .discCache(new UnlimitedDiscCache(cacheDir))
                // 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout
                // (5
                // s),
                // readTimeout
                // (30
                // s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);*/
    }

    /**
     * 获取全局的Context
     *
     * @return
     */
    public static Context getContext(){
        return context;
    }

}
