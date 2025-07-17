package com.fpt.pe.services;


import com.fpt.pe.controllers.AccountController.LoginDTO;
import com.fpt.pe.controllers.AccountController.LoginRes;

public interface AccountService {

    LoginRes login(LoginDTO loginDTO);

}
