package com.tutorial.rest;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class RestResponse {

    @Getter
    @Setter
    @Data
    @Builder
    public static class object<T> {
        private T data;
        private Integer status_code;
        private String message;
        private String errors;
    }

}
