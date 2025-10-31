package com.fpt.mss.msaccount_se181513.domain.user;

import com.fpt.mss.annotations.RequireRole;
import com.fpt.mss.api.MyApiResponse;
import com.fpt.mss.enums.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/users")
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping
    @RequireRole(Role.ADMIN)
    public ResponseEntity<MyApiResponse<List<Account>>> getAllUsers() {
        log.info("Fetching all users");
        var users = accountRepository.findAll();
        return MyApiResponse.success(users);
    }

    @GetMapping("test-only")
    public ResponseEntity<MyApiResponse<List<Account>>> getAllUsersTestOnly() {
        log.info("Fetching all users");
        var users = accountRepository.findAll();
        return MyApiResponse.success(users);
    }


}
