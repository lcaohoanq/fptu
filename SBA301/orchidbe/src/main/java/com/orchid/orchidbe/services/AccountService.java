package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.AccountDTO;
import com.orchid.orchidbe.pojos.Account;
import java.util.List;

public interface AccountService {

    List<Account> getAll();
    Account getById(int id);
    void add(AccountDTO.AccountReq account);
    void update(int id, AccountDTO.AccountReq account);
    void delete(int id);

}
