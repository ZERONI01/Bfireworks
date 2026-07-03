package com.zero.bfireworks.vo;

import lombok.Data;

public class MessagesVO {
    @Data
    public static class MessagesSaveVO {
        private String content;
        private String author;
        private String type;
    }
}
