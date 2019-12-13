package com.example.authenticate_retrofit.model;

public class Login {
    private String code;
    private String tel;
    private String login_type;
    private String device_key;
    private String type;
    private String app_id;

    @Override
    public String toString() {
        return "Login{" +
                "code='" + code + '\'' +
                ", tel='" + tel + '\'' +
                ", login_type='" + login_type + '\'' +
                ", device_key='" + device_key + '\'' +
                ", type='" + type + '\'' +
                ", app_id='" + app_id + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getDevice_key() {
        return device_key;
    }

    public void setDevice_key(String device_key) {
        this.device_key = device_key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public Login(String code, String tel, String login_type, String device_key, String type, String app_id) {
        this.code = code;
        this.tel = tel;
        this.login_type = login_type;
        this.device_key = device_key;
        this.type = type;
        this.app_id = app_id;
    }
}
