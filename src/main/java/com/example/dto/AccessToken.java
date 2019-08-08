package com.example.dto;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/3.
 * PS: Not easy to write code, please indicate.
 */
public class AccessToken {

    private  String  client_id;
    private  String   client_secret;
    private  String   code;
    private  String   redirect_url;
    private  String   state;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "client_id='" + client_id + '\'' +
                ", client_secret='" + client_secret + '\'' +
                ", code='" + code + '\'' +
                ", redirect_url='" + redirect_url + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
