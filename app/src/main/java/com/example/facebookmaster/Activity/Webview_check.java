package com.example.facebookmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.facebookmaster.R;
import com.example.facebookmaster.key.console;

public class Webview_check extends AppCompatActivity {
    
    private WebView webview;
    private Toolbar toolbar;
    private ImageView back,next;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_check);
        
        get_inten();
        anhxa();
        set_toolbar();
        set_webview();
        load_webview();
    }

    private void set_toolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void get_inten() {
        if (getIntent() != null) {
            link = getIntent().getStringExtra(console.LINK);
        }
    }

    private void load_webview() {
        webview.loadUrl(link);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (webview.canGoBack()) {
                    webview.goBack();
                }else {
                    Toast.makeText(getApplicationContext(), "Không có dữ liệu trang trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoForward()) {
                    webview.canGoForward();
                }else {
                    Toast.makeText(getApplicationContext(), "Không có dữ liệu trang sau", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void set_webview() {
        WebViewClient webViewClient = new WebViewClient();
        webview.setWebViewClient(webViewClient);
    }

    private void anhxa() {
        webview = findViewById(R.id.webview);
        toolbar = findViewById(R.id.toolbar);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
    }
}
