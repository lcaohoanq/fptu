package com.fpt.pe.services;

import com.fpt.pe.components.JwtTokenUtils;
import com.fpt.pe.controllers.AuthController.LoginDTO;
import com.fpt.pe.controllers.AuthController.LoginRes;
import com.fpt.pe.models.SystemAccount;
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

        Optional<SystemAccount> optionalUser = accountRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong email or password");
        }

        SystemAccount user = optionalUser.get();

        if(!user.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong email or password");
        }

        var authenticationToken =
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
