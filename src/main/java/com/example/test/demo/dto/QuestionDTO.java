package com.example.test.demo.dto;

import com.example.test.demo.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private int id;
    private Integer creater;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private String title;
    private String tag;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private User user;
}
