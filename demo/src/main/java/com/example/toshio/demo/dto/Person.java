package com.example.toshio.demo.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public abstract class Person {
    private String name;
    private String address;
    private LocalDate birthDate;
    private String phone;
}
