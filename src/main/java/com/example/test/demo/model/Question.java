package com.example.test.demo.model;

import lombok.Data;

@Data
public class Question {
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
}
