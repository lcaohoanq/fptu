package com.orchid.orchidbe.dto;

public class AccountDTO {

    public record AccountReq(
        String name,
        String email,
        String password,
        RoleDTO.RoleReq role
    ) {

    }

    public record AccountResp(
        int id,
        String name,
        String email
    ){

    }

}
