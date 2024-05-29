package com.study.allinonestudy.helper;

public enum Role {
    USER("ROLE_USER");
    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
