package com.example.springintro.repository;

import com.example.springintro.model.entity.enums.AgeRestriction;
import com.example.springintro.model.entity.enums.EditionType;

import java.math.BigDecimal;

public interface BookInfo {
    String getTitle();
    EditionType getEditionType();
    AgeRestriction getAgeRestriction();
    BigDecimal getPrice();
}
