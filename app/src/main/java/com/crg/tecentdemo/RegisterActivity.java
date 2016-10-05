package com.crg.tecentdemo;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crg.constan.utils.ConstantUtils;
import com.crg.progressUtils.ProgressUtils;
import com.crg.shouhuzhe.netutils.PostHTTPRequest;
import com.crg.shouhuzhe.netutils.RequestServers;
import com.crg.shouhuzhe.netutils.SHZRegister;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private Button backButton;
    private EditText username;
    private EditText password;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.register_etUsername);
        password = (EditText) findViewById(R.id.register_etPassword);
        backButton = (Button) findViewById(R.id.register_leftBtn);
        registerButton = (Button) findViewById(R.id.register_btnRegister);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ProgressUtils.showProgress(RegisterActivity.this, ProgressDialog.STYLE_SPINNER);
                StringBuilder nameBuilder = new StringBuilder(username.getText().toString());
                StringBuilder pwdBuilder = new StringBuilder(password.getText().toString());
                Log.e("开始发送注册请求==============", nameBuilder.toString() + "====" + pwdBuilder.toString());
                startRegister(nameBuilder.toString(),pwdBuilder.toString());
            }
        });

    }

    private void startRegister(String username, String password){
        Log.e("开始发送注册请求==============", username + "====" + password);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtils.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        SHZRegister shzRegister = retrofit.create(SHZRegister.class);
        Call<String> call = shzRegister.register(username, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null){
                        Log.e("=============","返回:" + response.body().toString());
                        JSONObject serverResponse = JSON.parseObject(response.body());
                        if (serverResponse.getString("code").equals("200")){
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, serverResponse.getString("errmsg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (response.errorBody() != null){
                        Log.e("==========","返回:" + response.errorBody().string());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
//                    ProgressUtils.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                ProgressUtils.dismiss();
                Toast.makeText(RegisterActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
