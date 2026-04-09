package com.example.toshio.demo.enums;

import com.example.toshio.client.model.InsuredPerson.RelationshipEnum;

public enum RelationShip {
    SELF("本人", "SELF"),
    SPOUSE("配偶者", "SPOUSE"),
    CHILD("子", "CHILD");

    private final String label;
    private final String value;

    RelationShip(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}