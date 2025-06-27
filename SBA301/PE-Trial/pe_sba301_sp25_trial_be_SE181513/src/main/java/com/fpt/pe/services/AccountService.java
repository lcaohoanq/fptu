package com.fpt.pe.services;


import com.fpt.pe.controllers.AuthController.LoginDTO;
import com.fpt.pe.controllers.AuthController.LoginRes;

public interface AccountService {

    LoginRes login(LoginDTO loginDTO);

}
