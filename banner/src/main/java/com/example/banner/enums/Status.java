package com.example.banner.enums;

public enum Status {
    ACTIVE(1),
    DELETED(0);

    private int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
