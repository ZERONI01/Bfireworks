package com.zero.bfireworks.vo;

import lombok.Data;

public class AnnouncementVO {

    @Data
    public static class SaveVO {
        private String title;
        private String content;
        private Boolean pinned;
    }
}
