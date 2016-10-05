package com.crg.progressUtils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by crg on 16/10/4.
 */

public class ProgressUtils {
    private  static ProgressDialog mProgressDialog;
    public static void showProgress(Context context ,int style){
        ProgressDialog progressDialog = new ProgressDialog(context);
        mProgressDialog = progressDialog;
        progressDialog.setProgressStyle(style);
        progressDialog.setTitle("正在登录中.....");
        progressDialog.show();
    }
    public static void dismiss(){
        mProgressDialog.dismiss();
    }
}
