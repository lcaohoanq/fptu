package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.AccountDTO;
import com.orchid.orchidbe.pojos.Account;
import java.util.List;

public interface AccountService {

    List<Account> getAll();

    Account getById(String id);

    void add(AccountDTO.CreateAccountReq account);

    void update(String id, AccountDTO.UpdateAccountReq account);

    void delete(String id);

    String login(String email, String password) throws Exception;

    Account getUserDetailsFromToken(String token) throws Exception;

    Account getUserDetailsFromRefreshToken(String refreshToken) throws Exception;

}
