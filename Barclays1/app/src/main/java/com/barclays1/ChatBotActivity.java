package com.barclays1;

import android.support.v7.app.AppCompatActivity;


    import android.os.Bundle;
    import android.webkit.WebView;
    import android.app.Activity;

public class ChatBotActivity extends AppCompatActivity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        webView = (WebView) findViewById(R.id.webview);

        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        String customHtml = "<!DOCTYPE html><html>        <head>        <title>Page Title</title>        </head>        <body>       <script type=\"text/javascript\">        var headID = document.getElementsByTagName(\"head\")[0];                var newCss = document.createElement(\'link\');        newCss.rel = \'stylesheet\';        newCss.type = \'text/css\';        window._botUsername = \'56487\';        window._botName = \'venali\';        newCss.href = \"http://rebot.me/assets/css/bot.css\";        var newScript = document.createElement(\'script\');        newScript.src = \"http://rebot.me/assets/js/bot.js\";        newScript.type = \'text/javascript\';        headID.appendChild(newScript);        headID.appendChild(newCss);        </script></body></html>                " ;

        webView.loadData(customHtml, "text/html", "UTF-8");
        //G:\Projects\Android\Workspace\Barclays1\app\src\main\res\asset\chat.html
       // String html = "<html><body>Hello, World!</body></html>";
       // String mime = "text/html";
       // String encoding = "utf-8";

        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadDataWithBaseURL(null, html, mime, encoding, null);

        //webView.loadUrl("http://www.google.com");
       // webView.loadData(customHtml, "text/html", "UTF-8");
    }
}
