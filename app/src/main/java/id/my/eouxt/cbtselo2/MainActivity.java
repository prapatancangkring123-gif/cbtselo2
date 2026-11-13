package id.my.eouxt.cbtselo2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private long lastBackPress = 0;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. ANTI SCREENSHOT TOTAL - Mencegah tangkapan layar / rekam layar saat ujian
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // 2. LAYAR PENUH IMMERSIVE (Modern API 30+) - Menyembunyikan status bar & navigation bar
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        setContentView(R.layout.activity_main);

        // Inisialisasi WebView
        webView = findViewById(R.id.webView);
        WebSettings ws = webView.getSettings();
        
        // Konfigurasi WebSettings untuk performa CBT optimal
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        ws.setBuiltInZoomControls(false);
        ws.setDisplayZoomControls(false);
        
        // Custom User Agent untuk identifikasi server CBT
        ws.setUserAgentString(ws.getUserAgentString() + " GarudaCBT-Selo2/1.0");

        // WebViewClient modern (mendukung WebResourceRequest)
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        // Blokir klik lama (mencegah salin teks / copy-paste soal ujian)
        webView.setOnLongClickListener(v -> true);
        webView.setHapticFeedbackEnabled(false);

        // Load URL Website CBT Anda
        webView.loadUrl("https://eouxt.my.id/cbtselo2");
    }

    // Fitur Tombol Kembali (Double-tap untuk keluar aplikasi)
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else if (System.currentTimeMillis() - lastBackPress < 2000) {
            super.onBackPressed();
        } else {
            lastBackPress = System.currentTimeMillis();
            Toast.makeText(this, "Tekan sekali lagi untuk keluar ujian", Toast.LENGTH_SHORT).show();
        }
    }
}
