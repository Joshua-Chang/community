package com.example.test.demo.controller;

import com.example.test.demo.dto.CommentCreateDTO;
import com.example.test.demo.dto.CommentDTO;
import com.example.test.demo.dto.QuestionDTO;
import com.example.test.demo.enums.CommentTypeEnum;
import com.example.test.demo.services.CommentService;
import com.example.test.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO questionDTO=questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);

        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.question);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
