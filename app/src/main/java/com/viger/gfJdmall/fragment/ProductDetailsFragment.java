package com.viger.gfJdmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.cons.NetworkConst;

/**商品详情界面
 * Created by lean on 16/10/28.
 */

public class ProductDetailsFragment extends Fragment {

    private View mView;
    private WebView webview;
    private long pId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_product_details,container,false);
        webview = (WebView) mView.findViewById(R.id.webview);
        pId = getArguments().getLong("pid");
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webview.loadUrl(NetworkConst.PRODUCTDETAIL + "?productId=" + pId);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.getSettings().setJavaScriptEnabled(true);
    }

}
