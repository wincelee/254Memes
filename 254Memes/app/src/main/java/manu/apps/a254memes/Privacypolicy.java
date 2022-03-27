package manu.apps.a254memes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Privacypolicy extends Fragment {

    private WebView webView;

    public Privacypolicy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_privacypolicy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.privacy_policy_webview);
        webView.setWebViewClient(new MyBrowser());
        String url="https://254memesappprivacypolicy.blogspot.com/2019/05/254memes-app-privacy-policy.html";

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);

    }

    private class MyBrowser extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return true;
        }
    }


}
