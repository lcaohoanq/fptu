package com.fpt.pe.services;

import com.fpt.pe.components.JwtTokenUtils;
import com.fpt.pe.controllers.AccountController.LoginDTO;
import com.fpt.pe.controllers.AccountController.LoginRes;
import com.fpt.pe.models.AccountMember;
import com.fpt.pe.models.AccountMember.Role;
import com.fpt.pe.repositories.AccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public LoginRes login(LoginDTO loginDTO) {

        var email = loginDTO.email();
        var password = loginDTO.password();

        Optional<AccountMember> optionalUser = accountRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong email or password");
        }

        AccountMember user = optionalUser.get();

        if(user.getRole() == Role.MEMBER) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong email or password");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(email, password,
                                                    user.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        String token = jwtTokenUtils.generateToken(user);

        return new LoginRes(
            token,
            user
        );
    }


}
