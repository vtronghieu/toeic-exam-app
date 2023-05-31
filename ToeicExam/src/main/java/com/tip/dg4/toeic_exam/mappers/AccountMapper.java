package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.AccountDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;
import com.tip.dg4.toeic_exam.models.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account convertRegisterDtoToModel(RegisterDto registerDto) {
        Account account = new Account();

        account.setUsername(registerDto.getUsername());
        account.setPassword(registerDto.getPassword());
        account.setActive(true);

        return account;
    }

    public AccountDto convertModelToDto(Account account) {
        AccountDto accountDto = new AccountDto();

        accountDto.setId(account.getId());
        accountDto.setUsername(account.getUsername());
        accountDto.setPassword(account.getPassword());
        accountDto.setActive(account.isActive());

        return accountDto;
    }
}
