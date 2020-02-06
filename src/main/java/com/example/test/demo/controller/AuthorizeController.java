package com.example.test.demo.controller;

import com.example.test.demo.dto.AccessTokenDTO;
import com.example.test.demo.dto.GithubUser;
import com.example.test.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.client_secret}")
    private String ClientSecret;
    @Value("${github.client.redirect_uri}")
    private String RedirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setRedirect_uri(RedirectUri);
        accessTokenDTO.setClient_secret(ClientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.toString());
        return "index";
    }


}
