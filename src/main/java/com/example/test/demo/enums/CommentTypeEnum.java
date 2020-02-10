package com.example.test.demo.enums;

public enum CommentTypeEnum {
    question(1),
    comment(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : values()) {
            if (commentTypeEnum.getType()==type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
