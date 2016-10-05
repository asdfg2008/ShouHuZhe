package com.crg.tecentdemo;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.crg.entity.ServerResponse;
import com.crg.progressUtils.ProgressUtils;
import com.crg.shouhuzhe.netutils.PostHTTPRequest;
import com.crg.shouhuzhe.netutils.RequestServers;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private Button registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = (EditText) findViewById(R.id.etUsername);
        et_password = (EditText) findViewById(R.id.etPassword);
        et_username.setText("crg");
        et_password.setText("123");
        registerBtn = (Button) findViewById(R.id.btRegister);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button button = (Button) findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressUtils.showProgress(LoginActivity.this, ProgressDialog.STYLE_SPINNER);
                StringBuilder builder = new StringBuilder(et_username.getText().toString());
                StringBuilder builder2 = new StringBuilder(et_password.getText().toString());
                sendPost(builder.toString(), builder2.toString());
            }
        });
    }

    private void sendPost(String username, String password){
        Log.e("参数是============",username + ":" + password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtils.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        final RequestServers requestServers = retrofit.create(RequestServers.class);
        Call<String> call = requestServers.login(username,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null){
                        Message message = new Message();
                        JSONObject serverResponse = JSON.parseObject(response.body());
                        if (serverResponse.getString("code").equals("200")){
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, serverResponse.getString("errmsg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (response.errorBody() != null){
                        Log.e("==========","返回:" + response.errorBody().string());
                        Toast.makeText(LoginActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    ProgressUtils.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ProgressUtils.dismiss();
                Toast.makeText(LoginActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
