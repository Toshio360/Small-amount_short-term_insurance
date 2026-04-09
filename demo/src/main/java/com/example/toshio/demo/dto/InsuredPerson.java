package com.example.toshio.demo.dto;

import com.example.toshio.demo.enums.RelationShip;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InsuredPerson extends Person {
    private RelationShip relationship;
}
