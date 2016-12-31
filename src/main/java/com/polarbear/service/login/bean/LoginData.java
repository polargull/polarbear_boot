package com.polarbear.service.login.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginData<T> {
    T user;
    @JsonIgnore
    String authEncode;

    public LoginData() {
    }

    public LoginData(T user) {
        this.user = user;
    }

    public LoginData(T user, String authEncode) {
        this.user = user;
        this.authEncode = authEncode;
    }

    public String getAuthEncode() {
        return authEncode;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T t) {
        this.user = t;
    }

}