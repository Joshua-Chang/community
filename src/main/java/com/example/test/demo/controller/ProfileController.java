package com.example.test.demo.controller;

import com.example.test.demo.dto.PageDTO;
import com.example.test.demo.mapper.UserMapper;
import com.example.test.demo.model.User;
import com.example.test.demo.services.NotificationService;
import com.example.test.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request, @PathVariable(name = "action") String action, Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size) {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect/";
        }
        if (action.equals("questions")) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PageDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if (action.equals("replies")) {
            PageDTO paginationDTO = notificationService.list(user.getId(), page, size);
            Long unreadCount = notificationService.unreadCount(user.getId());

            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的回复");
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("unreadCount", unreadCount);

        }
        PageDTO pageDTO=questionService.list(user.getId(),page,size);
        model.addAttribute("pages",pageDTO);
        return "profile";
    }
}
