package org.example.product_shop.util;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidatorComponent {
    <E> boolean isValid(E entity);
    <E> Set<ConstraintViolation<E>> validate(E entity);
}
