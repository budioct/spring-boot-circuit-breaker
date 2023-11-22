package com.tutorial.dto;

import lombok.*;

public class DTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class responseAccount{

        private String name;
        private String accountNumber;
    }

}
