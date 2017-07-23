package dashboard.argiot.co.in;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class FullscreenActivity extends Activity {

    public static WebView PortalView;
    private boolean isConnected = true;
    final String offlineMessageHtml = "Network is disconnected";
    final String timeoutMessageHtml = "Connection timed out";

    private String url = "http://dashboard.agriot.co.in";
    SwipeRefreshLayout swipeRefreshLayout;

    private void showInfoMessageDialog(String meaasge)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(
                FullscreenActivity.this).create();
        alertDialog.setTitle("Connectivity");
        alertDialog.setMessage(meaasge);
        alertDialog.setButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                        finish();
                    }
                });
        //alertDialog.setIcon(R.drawable.error);
        alertDialog.show();
    }

    private boolean isNetworkAvailable2()
    {
        System.out.println("isNetworkAvailable2 called");
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();

        if (info == null || !info.isAvailable() || !info.isConnected())
            return false;
        else return true;
    }


    //On Layout Loaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        // Commenting out Swipe to refresh feature

//        Window window = PortalActivity.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
//        window.setStatusBarColor(PortalActivity.this.getResources().getColor(R.color.green));

        //Swipe to Refresh initilizing
//        swipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipeContainer);
//        swipeRefreshLayout.setEnabled(false);

        //  initializing Webview  and Setting Js
        PortalView = (WebView) this.findViewById(R.id.web_engine);
        PortalView.getSettings().setJavaScriptEnabled(true);
        PortalView.getSettings().setDomStorageEnabled(true);
        PortalView.setWebChromeClient(new WebChromeClient());
        PortalView.getSettings().setPluginState(WebSettings.PluginState.ON);
        PortalView.getSettings().setAllowFileAccess(true);
        PortalView.getSettings().setAllowContentAccess(true);
        PortalView.getSettings().setAllowFileAccessFromFileURLs(true);
        PortalView.getSettings().setAllowUniversalAccessFromFileURLs(true);
//        PortalView.addJavascriptInterface(new WebAppInterface(this), "android");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }




        PortalView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                System.out.println("page loading started");
                // TODO Auto-generated method stub
                if(!isNetworkAvailable2())
                {
                    showInfoMessageDialog("network not available");
                    System.out.println("network not available");
                    return;
                }
                else System.out.println("network available");

                super.onPageStarted(view, url, favicon);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                isConnected=isNetworkAvailable2();
                if (isConnected) {
                    // return false to let the WebView handle the URL
                    return false;
                } else {
                    // show the proper "not connected" message
                     view.loadData(offlineMessageHtml, "text/html", "utf-8");
                    // return true if the host application wants to leave the current
                    // WebView and handle the url itself
                    return true;
                }
            }
            @Override
            public void onReceivedError (WebView view, int errorCode,
                                         String description, String failingUrl) {
                if (errorCode == ERROR_TIMEOUT) {
                    view.stopLoading();  // may not be needed
                    view.loadData(timeoutMessageHtml, "text/html", "utf-8");
                }
            }

            /*
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    Toast.makeText(FullscreenActivity.this,"connected",Toast.LENGTH_LONG).show();
                    // return false to let the WebView handle the URL
                    return false;
                } else {
                    Toast.makeText(FullscreenActivity.this,"not",Toast.LENGTH_LONG).show();
                    // show the proper "not connected" message
                    view.loadData("file:///android_asset/error.html", "text/html", "utf-8");
                    // return true if the host application wants to leave the current
                    // WebView and handle the url itself
                    return true;
                }
            }

            //Displaying Error.html if carenation site unable to load
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                Toast.makeText(FullscreenActivity.this,"adsfasdf",Toast.LENGTH_LONG).show();
                view.loadUrl("file:///android_asset/error.html");
            }

            */


        });

        //loading page

        PortalView.loadUrl(url);


    } // END OF ON CREATE


    // ON Layout Start

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }


/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && PortalView.canGoBack()) {


            String url = PortalView.getUrl();
            if (url.equals("http://dashboard.agriot.co.in/login") ||
                    url.equals("http://dashboard.agriot.co.in/home")) {
                //Toast.makeText(FullscreenActivity.this, "Exiting App",
                  //      Toast.LENGTH_LONG).show();

                super.onBackPressed();
            } else {
                // go back a page, like normal browser
                PortalView.goBack();
            }

        }

        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
    */

    //Handling Back Button Event So that it navigates to previously visited page instead of app closing
    @Override
    public void onBackPressed() {
    //Intent intent = new Intent(Intent.ACTION_MAIN);
      //  intent.addCategory(Intent.CATEGORY_HOME);
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // startActivity(intent);

        if (PortalView.canGoBack()) {

            if (PortalView.getUrl().equals("http://dashboard.agriot.co.in/login") ||
                    PortalView.getUrl().equals("http://dashboard.agriot.co.in/home")) {
                //Toast.makeText(FullscreenActivity.this, "Exiting App",
                //      Toast.LENGTH_LONG).show();

                super.onBackPressed();
            }
            else {
                System.out.println("can go back");
                PortalView.goBack();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit!")
                    .setMessage("Are you sure you want to close?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

}





