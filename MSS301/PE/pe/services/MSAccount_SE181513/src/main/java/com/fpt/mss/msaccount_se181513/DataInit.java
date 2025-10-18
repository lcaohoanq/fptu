package com.fpt.mss.msaccount_se181513;

import com.fpt.mss.msaccount_se181513.model.Account;
import java.util.List;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private String defaultPassword = "@2";
    private String hashedPassword = "";

    @PostConstruct
    public void hashDefaultPassword() {
        hashedPassword = passwordEncoder.encode(defaultPassword);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {

        if(accountRepository.count() == 0){
            var account1 = Account.builder()
                .username("adminsys")
                .email("admin@blindboxes.vn")
                .password(hashedPassword)
                .role(Role.ADMIN)
                .isActive(true)
                .build();

            var account2 = Account.builder()
                .username("johndoe")
                .email("john@blindboxes.vn")
                .password(hashedPassword)
                .role(Role.MEMBER)
                .isActive(true)
                .build();

            var account3 = Account.builder()
                .username("modmichel")
                .email("michel@blindboxes.vn")
                .password(hashedPassword)
                .role(Role.MODERATOR)
                .isActive(true)
                .build();

            var account4 = Account.builder()
                .username("vanvan")
                .email("vanavan@blindboxes.vn")
                .password(defaultPassword)
                .role(Role.MEMBER)
                .isActive(true)
                .build();

            var account5 = Account.builder()
                .username("devops")
                .email("dev@blindboxes.vn")
                .password(defaultPassword)
                .role(Role.DEVELOPER)
                .isActive(true)
                .build();

            accountRepository.saveAll(List.of(account1, account2, account3, account4, account5));

        }

    }
}
