package org.example.game_store.utils;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidatorComponent {
    <E> boolean isValid(E entity);

    <E> Set<ConstraintViolation<E>> validate(E entity);
}
