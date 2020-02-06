package com.example.test.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.test.demo.dto.AccessTokenDTO;
import com.example.test.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String s = response.body().string();
            System.out.println(s);
//            access_token=5617323600286edd367d9bb2b51206c6f27e1b28&scope=user&token_type=bearer
            String token = s.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessTokenDTO){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/user?access_token="+accessTokenDTO).build();
        try {
            Response response =  okHttpClient.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
