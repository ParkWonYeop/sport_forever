package com.example.sport_forever.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PermissionEnum {
    USER("user"),
    ADMIN("admin");

    final String permission;
}
