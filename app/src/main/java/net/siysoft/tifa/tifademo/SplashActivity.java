package net.siysoft.tifa.tifademo;

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
