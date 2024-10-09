package org.example.springlab;

import org.example.springlab.data.entities.Account;
import org.example.springlab.data.entities.User;
import org.example.springlab.service.AccountService;
import org.example.springlab.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final AccountService accountService;

    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }


    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Petq", 19);
        User user2 = new User("Qvor", 19);

        Account acc1 = new Account(BigDecimal.ONE);
        Account acc2 = new Account(BigDecimal.ONE);

        acc1.setUser(user1);
        acc2.setUser(user2);

        user1.setAccounts(new HashSet<>() {{
            add(acc1);
        }});

        user2.setAccounts(new HashSet<>() {{
            add(acc2);
        }});

        this.userService.registerUser(user1);
        this.userService.registerUser(user2);

        this.accountService.depositMoney(BigDecimal.valueOf(100000), user1.getId());
        this.accountService.depositMoney(BigDecimal.valueOf(1), user2.getId());

        this.accountService.withdrawMoney(BigDecimal.valueOf(1.99), user2.getId());
    }
}
