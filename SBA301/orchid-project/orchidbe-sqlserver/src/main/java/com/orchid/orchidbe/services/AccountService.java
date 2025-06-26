package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.AccountDTO;
import com.orchid.orchidbe.pojos.Account;
import java.util.List;

public interface AccountService {

    List<AccountDTO.AccountResp> getAll();

    Account getById(Long id);

    Account getByEmail(String email);

    void addEmployee(AccountDTO.CreateStaffReq account);
    void add(AccountDTO.CreateAccountReq account);

    void update(Long id, AccountDTO.UpdateAccountReq account);

    void delete(Long id);

    String login(String email, String password) throws Exception;

}
