package com.orchid.s1accountservice.data;

import com.orchid.s1accountservice.domain.account.Account;
import com.orchid.s1accountservice.domain.role.Role;
import com.orchid.s1accountservice.domain.role.Role.RoleName;
import com.orchid.s1accountservice.repositories.AccountRepository;
import com.orchid.s1accountservice.repositories.RoleRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("h2")
@Component
@RequiredArgsConstructor
public class SampleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    /*
    - Reset sequences to avoid conflicts with existing data

    SELECT setval('accounts_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM accounts), false);
    SELECT setval('categories_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM categories), false);
    SELECT setval('orchids_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM orchids), false);
    SELECT setval('order_details_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM order_details), false);
    SELECT setval('orders_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM orders), false);
    SELECT setval('roles_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM roles), false);
    SELECT setval('tokens_id_seq', (SELECT COALESCE(MAX(id), 0) + 1 FROM tokens), false);
    */

    @Override
    public void run(String... args) throws Exception {

        var hashedPassword = passwordEncoder.encode("Iloveyou123^^");

        // Create roles if empty
        List<Role> roles;
        if (roleRepository.findAll().isEmpty()) {
            var role1 = Role.builder().name(RoleName.ADMIN).build();
            var role2 = Role.builder().name(RoleName.MANAGER).build();
            var role3 = Role.builder().name(RoleName.USER).build();
            var role4 = Role.builder().name(RoleName.STAFF).build();
            roles = roleRepository.saveAll(Arrays.asList(role1, role2, role3, role4));
        } else {
            roles = roleRepository.findAll();
        }

        // Create accounts if empty
        List<Account> accounts;
        if (accountRepository.findAll().isEmpty()) {
            var acc1 = Account.builder()
                .name("Admin")
                .email("admin@gmail.com")
                .password(hashedPassword)
                .role(roles.isEmpty() ? null : roles.get(0))
                .build();

            var acc2 = Account.builder()
                .name("Manager")
                .email("manager@gmail.com")
                .password(hashedPassword)
                .role(roles.size() > 1 ? roles.get(1) : null)
                .build();

            var acc3 = Account.builder()
                .name("User")
                .email("user@gmail.com")
                .password(hashedPassword)
                .role(roles.size() > 2 ? roles.get(2) : null)
                .build();

            var acc4 = Account.builder()
                .name("Staff")
                .email("staff@gmail.com")
                .password(hashedPassword)
                .role(roles.size() > 2 ? roles.get(3) : null)
                .build();

            accounts = accountRepository.saveAll(Arrays.asList(acc1, acc2, acc3, acc4));
        } else {
            accounts = accountRepository.findAll();
        }
    }
}
