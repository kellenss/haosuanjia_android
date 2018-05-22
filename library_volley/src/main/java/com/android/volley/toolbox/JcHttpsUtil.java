package com.android.volley.toolbox;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by jinchao on 2016/12/14.
 */

public class JcHttpsUtil {
    /**
     * HttpUrlConnection支持所有Https免验证，不建议使用
     *
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public void initSSLALL(String urlPath) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        URL url = new URL(urlPath);
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new TrustManager[] { new TrustAllManager() }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.setRequestMethod("POST");
        // 设置请求的超时时间
        connection.setReadTimeout(5000);
        connection.setConnectTimeout(5000);
        // 传递的数据
//        String data = "username=" + URLEncoder.encode(userName, "UTF-8")
//                + "&userpass=" + URLEncoder.encode(userPass, "UTF-8");
        // 设置请求的头
        connection.setRequestProperty("Connection", "keep-alive");
        // 设置请求的头
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        // 设置请求的头
        connection.setRequestProperty("Content-Length",
                "");
        // 设置请求的头
        connection
                .setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

        connection.setDoOutput(true); // 发送POST请求必须设置允许输出
        connection.setDoInput(true); // 发送POST请求必须设置允许输入
        //setDoInput的默认值就是true
        connection.connect();
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        StringBuffer result = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        Log.e("TTTT", result.toString());
    }


    public class TrustAllManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    }


//    /**
//     * HttpClient方式实现，支持所有Https免验证方式链接
//     *
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public void initSSLAllWithHttpClient() throws ClientProtocolException, IOException {
//        int timeOut = 30 * 1000;
//        HttpParams param = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(param, timeOut);
//        HttpConnectionParams.setSoTimeout(param, timeOut);
//        HttpConnectionParams.setTcpNoDelay(param, true);
//
//        SchemeRegistry registry = new SchemeRegistry();
//        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//        registry.register(new Scheme("https", TrustAllSSLSocketFactory.getDefault(), 443));
//        ClientConnectionManager manager = new ThreadSafeClientConnManager(param, registry);
//        DefaultHttpClient client = new DefaultHttpClient(manager, param);
//
//        HttpGet request = new HttpGet("https://certs.cac.washington.edu/CAtest/");
//        // HttpGet request = new HttpGet("https://www.alipay.com/");
//        HttpResponse response = client.execute(request);
//        HttpEntity entity = response.getEntity();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
//        StringBuilder result = new StringBuilder();
//        String line = "";
//        while ((line = reader.readLine()) != null) {
//            result.append(line);
//        }
//        Log.e("HTTPS TEST", result.toString());
//    }
//
//    /**
//     * HttpClient方式实现，支持验证指定证书
//     *
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public void initSSLCertainWithHttpClient() throws ClientProtocolException, IOException {
//        int timeOut = 30 * 1000;
//        HttpParams param = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(param, timeOut);
//        HttpConnectionParams.setSoTimeout(param, timeOut);
//        HttpConnectionParams.setTcpNoDelay(param, true);
//
//        SchemeRegistry registry = new SchemeRegistry();
//        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//        registry.register(new Scheme("https", TrustCertainHostNameFactory.getDefault(this), 443));
//        ClientConnectionManager manager = new ThreadSafeClientConnManager(param, registry);
//        DefaultHttpClient client = new DefaultHttpClient(manager, param);
//
//        // HttpGet request = new
//        // HttpGet("https://certs.cac.washington.edu/CAtest/");
//        HttpGet request = new HttpGet("https://www.alipay.com/");
//        HttpResponse response = client.execute(request);
//        HttpEntity entity = response.getEntity();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
//        StringBuilder result = new StringBuilder();
//        String line = "";
//        while ((line = reader.readLine()) != null) {
//            result.append(line);
//        }
//        Log.e("HTTPS TEST", result.toString());
//    }
//
//    public class TrustAllManager implements X509TrustManager {
//
//        @Override
//        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
//                throws CertificateException {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
//                throws CertificateException {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public X509Certificate[] getAcceptedIssuers() {
//            // TODO Auto-generated method stub
//            return null;
//        }
//    }
}
