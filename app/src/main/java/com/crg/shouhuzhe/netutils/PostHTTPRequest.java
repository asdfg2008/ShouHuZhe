package com.crg.shouhuzhe.netutils;

import android.app.Notification;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.crg.entity.ServerResponse;
import com.crg.globalApplication.MyApplication;
import com.crg.progressUtils.ProgressUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by crg on 16/10/4.
 */

 public class PostHTTPRequest {
    //http://192.168.1.105:8080/Guard
    //http://121.199.56.164:81/shouhuzhe/
    private static  final String SERVER_URL = "http://121.199.56.164:81/shouhuzhe/";
    private static OkHttpClient  client = new OkHttpClient();
    private static ProgressDialog progressDialog;



    public static void  login(final String username, final String password,Handler handler)  {
        final String urlString = SERVER_URL + "login.php";
        Log.d("请求的url------------",urlString+ username + password);
        final Handler finalHandler = handler;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(urlString);
                    //创建okHttpClient对象
                    FormBody formBody = new FormBody.Builder()
                            .add("username",username)
                            .add("password",password)
                            .build();

                    Request request = new Request.Builder().url(url).post(formBody).build();
//                    Response response =  client.newCall(request).execute();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("登录接口失败返回--------",e.toString());
                            try {
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            Message message = new Message();
                            message.what = 404;
                            finalHandler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Gson json = new Gson();
                            Message message = new Message();
                            ServerResponse serverResponse = json.fromJson(response.body().string(),ServerResponse.class);
                            Log.d("登录数据返回--------",response.body().string() + serverResponse.getErroce() + ":" + serverResponse.getErrmsg());
                            message.what = serverResponse.getErroce();
                            finalHandler.sendMessage(message);
                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void register(final String username, final String password, final Handler handler){
        final String urlString = SERVER_URL + "register.php";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(urlString);
                    //创建okHttpClient对象
                    FormBody formBody = new FormBody.Builder()
                            .add("username",username)
                            .add("password",password)
                            .build();
                    final Handler finalHandler = handler;

                    Request request = new Request.Builder().url(url).post(formBody).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message message = new Message();
                            message.what = 404;
                            finalHandler.sendMessage(message);
                            Log.d("注册错误返回--------",e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Message message = new Message();
                            Log.d("注册接口返回--------",response.toString());
                            Gson json = new Gson();
                            ServerResponse serverResponse = json.fromJson(response.body().string(),ServerResponse.class);
                            message.what =200;
                            handler.sendMessage(message);
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}