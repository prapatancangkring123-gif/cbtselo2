package id.my.eouxt.cbtselo2;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private long lastBackPress = 0;
    @SuppressLint("SetJavaScriptEnabled")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ANTI SCREENSHOT TOTAL - Modified by Taufik
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true); ws.setDomStorageEnabled(true); ws.setDatabaseEnabled(true);
        ws.setLoadWithOverviewMode(true); ws.setUseWideViewPort(true);
        ws.setBuiltInZoomControls(false); ws.setDisplayZoomControls(false);
        ws.setUserAgentString(ws.getUserAgentString() + " GarudaCBT-Selo2/1.0");
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView v, String url){ v.loadUrl(url); return true; }
        });
        webView.setOnLongClickListener(v -> true);
        webView.loadUrl("https://eouxt.my.id/cbtselo2");
    }
    @Override public void onBackPressed() {
        if (webView.canGoBack()) webView.goBack();
        else if (System.currentTimeMillis() - lastBackPress < 2000) super.onBackPressed();
        else { lastBackPress = System.currentTimeMillis(); Toast.makeText(this, "Tekan Back sekali lagi untuk keluar", Toast.LENGTH_SHORT).show(); }
    }
}
