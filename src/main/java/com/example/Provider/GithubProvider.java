package com.example.Provider;

import com.alibaba.fastjson.JSON;
import com.example.dto.AccessToken;
import com.example.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/3.
 * PS: Not easy to write code, please indicate.
 */
@Component
public class GithubProvider {

    public String GetAccessToken(AccessToken accessToken)  {
            MediaType  mediaType  = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
                String string = response.body().string();
                String token = string.split("&")[0].split("=")[1];
            System.out.println(string);
                System.out.println("success");
                return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
                return null;

    }
     public GithubUser getUser(String accessToken){
         OkHttpClient client = new OkHttpClient();
         Request request = new Request.Builder()
                 .url("https://api.github.com/user?access_token="+accessToken)
                 .build();
         try {
             Response response = client.newCall(request).execute();
             String string = response.body().string();
             GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
             return githubUser;
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;

     }


}
