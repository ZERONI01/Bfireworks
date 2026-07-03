package com.zero.bfireworks.vo;

import lombok.Data;

public class UserVO {

    @Data
    public static class LoginVO {
        private String username;
        private String password;
    }

    @Data
    public static class RegisterVO {
        private String username;
        private String password;
    }

    @Data
    public static class ChangePasswordVO {
        private String oldPassword;
        private String newPassword;
    }

    @Data
    public static class UserSaveVO {
        private String username;
        private String password;
        private String role;
    }

    @Data
    public static class UserRespVO {
        private Integer id;
        private String username;
        private String role;
        private String createdAt;
    }
}
