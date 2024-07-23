package org.lion.medicapi.util;

import lombok.Getter;

@Getter
public enum SexType {
    FEMALE("여성"),
    MALE("남성");


    private final String value;

    SexType(String value) {
        this.value = value;
    }
}
