package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.AccountDTO;
import com.orchid.orchidbe.pojos.Account;
import java.util.List;

public interface AccountService {

    List<Account> getAll();

    Account getById(int id);

    void add(AccountDTO.CreateAccountReq account);

    void update(int id, AccountDTO.UpdateAccountReq account);

    void delete(int id);

    String login(String email, String password) throws Exception;

    Account getUserDetailsFromToken(String token) throws Exception;

    Account getUserDetailsFromRefreshToken(String refreshToken) throws Exception;

}
