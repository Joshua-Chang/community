package com.example.test.demo.controller;

import com.example.test.demo.dto.PageDTO;
import com.example.test.demo.dto.QuestionDTO;
import com.example.test.demo.mapper.QuestionMapper;
import com.example.test.demo.mapper.UserMapper;
import com.example.test.demo.model.Question;
import com.example.test.demo.model.User;
import com.example.test.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request ,Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size

    ) {
        PageDTO pageDTO=questionService.list(page,size);
        model.addAttribute("pages",pageDTO);
        return "index";//返回hello.html模版
    }
}
