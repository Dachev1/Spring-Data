package org.example.springlab.service;

import org.example.springlab.data.entities.Account;

import java.math.BigDecimal;

public interface AccountService {
    void withdrawMoney(BigDecimal money, Long id);
    void depositMoney(BigDecimal money, Long id);
}
