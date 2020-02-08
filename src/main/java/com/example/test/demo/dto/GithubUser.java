package com.example.test.demo.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private String bio;
    private String avatar_url;
    private Long id;
}
