package com.example.sport_forever.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WareEnum {
    BALL("ball"),
    TOOL("tool");

    final String category;
}
