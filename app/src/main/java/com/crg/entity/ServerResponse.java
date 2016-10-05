package com.crg.entity;

/**
 * Created by crg on 16/10/4.
 */

public class ServerResponse {
    private int erroce;
    private String errmsg;

    public ServerResponse() {
    }

    public int getErroce() {
        return erroce;
    }

    public void setErroce(int erroce) {
        this.erroce = erroce;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
