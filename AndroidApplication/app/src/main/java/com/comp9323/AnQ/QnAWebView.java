package com.comp9323.AnQ;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.comp9323.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link QnAWebView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QnAWebView extends Fragment {

    private WebView mWebView;

    public QnAWebView() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QnAWebView.
     */
    // TODO: Rename and change types and number of parameters
    public static QnAWebView newInstance() {
        QnAWebView fragment = new QnAWebView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qn_aweb_view, container, false);

        //getting web view from view by id
        mWebView = view.findViewById(R.id.QnA_WebView);

        //set starting url
        mWebView.loadUrl("http://52.65.129.3:8080/questions/");

        //set allow js in this web view
        mWebView.getSettings().setJavaScriptEnabled(true);
        //set load url content in this web view
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
