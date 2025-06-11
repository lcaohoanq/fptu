package com.orchid.orchidbe.services;

import com.orchid.orchidbe.components.JwtTokenUtils;
import com.orchid.orchidbe.dto.AccountDTO;
import com.orchid.orchidbe.exceptions.ExpiredTokenException;
import com.orchid.orchidbe.exceptions.TokenNotFoundException;
import com.orchid.orchidbe.pojos.Account;
import com.orchid.orchidbe.pojos.Role;
import com.orchid.orchidbe.pojos.Token;
import com.orchid.orchidbe.repositories.AccountRepository;
import com.orchid.orchidbe.repositories.TokenRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(int id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    @Override
    public void add(AccountDTO.CreateAccountReq account) {

        if (accountRepository.existsByEmail(account.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        var newAccount = new Account();
        newAccount.setName(account.name());
        newAccount.setEmail(account.email());
        newAccount.setPassword(passwordEncoder.encode(account.password()));
        newAccount.setRole(roleService.getById(2)); // Default role, can be changed later

        log.info("New user registered successfully");
        accountRepository.save(newAccount);
    }

    @Override
    public void update(int id, AccountDTO.UpdateAccountReq account) {

        var role = roleService.getById(account.roleId());
        if(role == null) {
            throw new IllegalArgumentException("Role not found");
        }

        var existingAccount = accountRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (accountRepository.existsByEmailAndIdNot(account.email(), id)) {
            throw new IllegalArgumentException("Email already exists");
        }

        existingAccount.setName(account.name());
        existingAccount.setEmail(account.email());
        existingAccount.setPassword(account.password());
        existingAccount.setRole(role);

        accountRepository.save(existingAccount);
    }

    @Override
    public void delete(int id) {
        var existingAccount = getById(id);
        accountRepository.delete(existingAccount);
    }

    @Override
    public Account getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtils.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String email = jwtTokenUtils.extractEmail(token);
        Optional<Account> user = accountRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new Exception("User not found with email: " + email);
        }
        return user.get();
    }

    @Override
    public Account getUserDetailsFromRefreshToken(String refreshToken) throws Exception {
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new TokenNotFoundException("Refresh token does not exist"));
        return getUserDetailsFromToken(existingToken.getToken());
    }

    @Override
    public String login(String email, String password) throws Exception {
        try{
            Optional<Account> optionalUser = accountRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                throw new BadCredentialsException("Wrong email or password");
            }

            Account existingUser = optionalUser.get();

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password, existingUser.getAuthorities());
            authenticationManager.authenticate(authenticationToken);
            return jwtTokenUtils.generateToken(existingUser);
        }catch (Exception e){
            log.error("Login failed for user {}: {}", email, e.getMessage());
            throw new Exception("Login failed: " + e.getMessage(), e);
        }
    }
}
