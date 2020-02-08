package com.example.test.demo.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private String avatar;
    private Long gmtCreate;
    private Long gmtModified;
}
