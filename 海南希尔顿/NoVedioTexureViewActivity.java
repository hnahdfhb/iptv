package com.yht.iptv.view.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.yht.iptv.MainFragment;
import com.yht.iptv.MainMenuDialogFragment;
import com.yht.iptv.R;
import com.yht.iptv.callback.IDialogIPSetClick;
import com.yht.iptv.callback.IFragmentOnclickListenr;
import com.yht.iptv.callback.IPresenterBase;
import com.yht.iptv.callback.IPresenterDownloadBase;
import com.yht.iptv.model.AdvertInfo;
import com.yht.iptv.model.AdvertVideoInfo;
import com.yht.iptv.model.BaseModel;
import com.yht.iptv.model.EventNetwork;
import com.yht.iptv.model.EventRefreshMainInfo;
import com.yht.iptv.model.FoodCarInfo;
import com.yht.iptv.model.MainPageInfo;
import com.yht.iptv.presenter.AdRecordPresenter;
import com.yht.iptv.presenter.AdvertDownloadPresenter;
import com.yht.iptv.presenter.MainPagePresenter;
import com.yht.iptv.push.MinaClientService;
import com.yht.iptv.service.CheckNewVersionService;
import com.yht.iptv.service.TimerService;
import com.yht.iptv.service.WeatherService;
import com.yht.iptv.socket.MinaService;
import com.yht.iptv.tools.IP_SET_CustomDialog;
import com.yht.iptv.utils.AppUtils;
import com.yht.iptv.utils.Constants;
import com.yht.iptv.utils.DBUtils;
import com.yht.iptv.utils.DialogUtils;
import com.yht.iptv.FileUtils;
import com.yht.iptv.utils.HttpConstants;
import com.yht.iptv.utils.LanguageUtils;
import com.yht.iptv.utils.OkHttpUtils;
import com.yht.iptv.utils.SPUtils;
import com.yht.iptv.utils.ServiceUtils;
import com.yht.iptv.utils.ShowImageUtils;
import com.yht.iptv.utils.ToastUtils;
import com.yht.iptv.BaseActivity;
import com.yht.iptv.view.BaseFragment;
import com.yht.iptv.view.MyApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;


public class NoVedioTexureViewActivity extends BaseActivity{

    private TextureView mTextureView;
    private HttpProxyCacheServer proxy;
    private String room_id;
    private MainPagePresenter presenter;
    private final int MAINPAGE = 1;
    private final int DELAY = 2;
    private final int ADVERT_DELAY = 3;
   // private TexureViewActivity.MyHandler handler;
    private ImageView iv_bg;
    private String lastCacheUrl;
    private int currentPosition;
    private boolean isMainFragmentUI = false;//是否mainFragment处于前端展示
    private int advertPosition;
    private int playTimes = 0;
    private int currentAdvertId = -1;//广告ID
    private boolean isDownload = false;
    private boolean isNotNeedPlay = false;
    private TextView tv_adv;
    private MediaPlayer mediaPlayer;
    private int mediaPlayerState;
    private Surface mSurface;
    private boolean isFirst;
}
