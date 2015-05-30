package com.jordanweaver.billsplitter;

import java.io.Serializable;

/**
 * Created by jordanweaver on 5/18/15.
 */
public class LoginObject implements Serializable{

    String username;
    String secret;

    public LoginObject(String username, String secret) {
        this.username = username;
        this.secret = secret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
