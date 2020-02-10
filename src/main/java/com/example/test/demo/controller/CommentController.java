package com.example.test.demo.controller;

import com.example.test.demo.dto.CommentCreateDTO;
import com.example.test.demo.dto.CommentDTO;
import com.example.test.demo.dto.ResultDTO;
import com.example.test.demo.enums.CommentTypeEnum;
import com.example.test.demo.exception.CustomizeErrorCode;
import com.example.test.demo.model.Comment;
import com.example.test.demo.model.User;
import com.example.test.demo.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;



    @ResponseBody()
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
//        if (commentCreateDTO == null || commentCreateDTO.getContent()==null) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(1L);
        comment.setLikeCount(0L);
        comment.setCommentator(user.getId());
        commentService.insert(comment, user);
        log.error("-------------测试");
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.comment);
        return ResultDTO.okOf(commentDTOS);
    }
}
