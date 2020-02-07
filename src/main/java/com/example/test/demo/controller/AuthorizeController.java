package com.example.test.demo.controller;

import com.example.test.demo.dto.AccessTokenDTO;
import com.example.test.demo.dto.GithubUser;
import com.example.test.demo.mapper.UserMapper;
import com.example.test.demo.model.User;
import com.example.test.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.client_secret}")
    private String ClientSecret;
    @Value("${github.client.redirect_uri}")
    private String RedirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setRedirect_uri(RedirectUri);
        accessTokenDTO.setClient_secret(ClientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.toString());

        if (githubUser != null) {
            User myUser = new User();
            String token = UUID.randomUUID().toString();
            myUser.setToken(token);
            myUser.setName(githubUser.getName());
            myUser.setAccountId(String.valueOf(githubUser.getId()));
            myUser.setGmtCreate(System.currentTimeMillis());
            myUser.setGmtModified(myUser.getGmtModified());
            userMapper.insert(myUser);

            response.addCookie(new Cookie("token",token));


            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }


}
