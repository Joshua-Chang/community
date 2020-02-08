package com.example.test.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO {
    private List<QuestionDTO> questions;
    private boolean showPre, showFirst, showNext, showEnd;
    private Integer currentPage;
    private List<Integer> pages = new ArrayList<>();
    private int totalPage;

    public void setPage(Integer count, Integer page, Integer size) {
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;

        currentPage = page;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        if (page == 1) {
            showPre = false;
        } else {
            showPre = true;
        }

        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        if (pages.contains(1)) {
            showFirst = false;
        } else {
            showFirst = true;
        }
        if (pages.contains(totalPage)) {
            showEnd = false;
        } else {
            showEnd = true;
        }

    }
}
