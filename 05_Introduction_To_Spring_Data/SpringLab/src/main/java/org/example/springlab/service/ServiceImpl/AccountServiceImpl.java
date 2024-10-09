package org.example.springlab.service.ServiceImpl;

import org.example.springlab.data.entities.Account;
import org.example.springlab.data.repositories.AccountRepository;
import org.example.springlab.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Optional<Account> optional = getAccount(id);

        if (optional.isPresent()) {
            Account account = optional.get();

            if (account.getBalance().compareTo(money) > 0) {
                account.setBalance(account.getBalance().subtract(money));
                this.accountRepository.save(account);
            } else {
                System.out.println("You don't have enough money");
            }
        }
    }

    @Override
    public void depositMoney(BigDecimal money, Long id) {
        Optional<Account> optional = getAccount(id);

        if (optional.isPresent()) {
            Account account = optional.get();

            if (money.compareTo(BigDecimal.ZERO) >= 0) {
                account.setBalance(account.getBalance().add(money));
                this.accountRepository.save(account);
                System.out.println("You add money to the account");
            }
        }
    }

    private Optional<Account> getAccount(Long id) {
        Optional<Account> optional = this.accountRepository.findById(id);
        return optional;
    }
}
