package com.instagram.downloader.Views.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.instagram.downloader.Utils.Preference.SharePrefs;
import com.instagram.downloader.R;
import com.instagram.downloader.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity{
    LoginActivity activity;
    private String cookies;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.activity = this;
        Login();
        this.binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("WrongConstant")
            @Override
            public final void onRefresh() {
                Login();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void Login() {
        this.binding.webView.getSettings().setJavaScriptEnabled(true);
        this.binding.webView.clearCache(true);
        this.binding.webView.setWebViewClient(new MyBrowser());
        CookieSyncManager.createInstance(this.activity);
        CookieManager.getInstance().removeAllCookie();
        this.binding.webView.loadUrl(getString(R.string.login_url));
        this.binding.webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                binding.swipeRefreshLayout.setRefreshing(i != 100);
            }
        });
    }

    public String getCookie(String str, String str2) {
        String cookie = CookieManager.getInstance().getCookie(str);
        if (cookie != null && !cookie.isEmpty()) {
            String[] split = cookie.split(";");
            for (String str3 : split) {
                if (str3.contains(str2)) {
                    return str3.split("=")[1];
                }
            }
        }
        return null;
    }

    public class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            LoginActivity.this.cookies = CookieManager.getInstance().getCookie(str);
            try {
                String cookie = LoginActivity.this.getCookie(str, getString(R.string.session));
                String cookie2 = LoginActivity.this.getCookie(str, getString(R.string.csrftoken));
                String cookie3 = LoginActivity.this.getCookie(str, getString(R.string.user_id));
                if (cookie != null && cookie2 != null && cookie3 != null) {
                    SharePrefs.getInstance(activity).putString(SharePrefs.COOKIES, LoginActivity.this.cookies);
                    SharePrefs.getInstance(activity).putString(SharePrefs.CSRF, cookie2);
                    SharePrefs.getInstance(activity).putString(SharePrefs.SESSIONID, cookie);
                    SharePrefs.getInstance(activity).putString(SharePrefs.USERID, cookie3);
                    SharePrefs.getInstance(activity).putBoolean(SharePrefs.IS_INSTAGRAM_LOGIN, true);
                    LoginActivity.this.binding.webView.destroy();
                    Intent intent = new Intent();
                    intent.putExtra(getString(R.string.result), getString(R.string.result));
                    LoginActivity.this.setResult(-1, intent);
                    LoginActivity.this.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}