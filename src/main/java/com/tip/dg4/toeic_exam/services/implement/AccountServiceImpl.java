package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.AccountDto;
import com.tip.dg4.toeic_exam.dto.AuthorizationDto;
import com.tip.dg4.toeic_exam.dto.LoginDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.UnauthorizedException;
import com.tip.dg4.toeic_exam.mappers.AccountMapper;
import com.tip.dg4.toeic_exam.models.Account;
import com.tip.dg4.toeic_exam.repositories.AccountRepository;
import com.tip.dg4.toeic_exam.services.AccountService;
import com.tip.dg4.toeic_exam.services.JwtService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              JwtService jwtService,
                              PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthorizationDto loginAccount(LoginDto loginDto) {
        Optional<Account> optionalAccount = findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        if (optionalAccount.isEmpty()) {
            throw new UnauthorizedException(TExamExceptionConstant.ACCOUNT_E004);
        }
        AuthorizationDto authorizationDto = new AuthorizationDto();
        authorizationDto.setToken(jwtService.generateToken(loginDto.getUsername()));

        return authorizationDto;
    }

    @Override
    public AccountDto registerAccount(RegisterDto registerDto) {
        if (accountRepository.existsByUsername(registerDto.getUsername())) {
            throw new BadRequestException(TExamExceptionConstant.ACCOUNT_E002 + registerDto.getUsername());
        }
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new BadRequestException(TExamExceptionConstant.ACCOUNT_E003);
        }
        Account account = accountMapper.convertRegisterDtoToModel(registerDto);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.insert(account);

        return accountMapper.convertModelToDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream().map(accountMapper::convertModelToDto).toList();
    }

    @Override
    public AccountDto findByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.ACCOUNT_E001 + username));

        return accountMapper.convertModelToDto(account);
    }

    private Optional<Account> findByUsernameAndPassword(String username, String password) {
        return accountRepository.findAll().stream()
                .filter(account -> username.equals(account.getUsername()) && passwordEncoder.matches(password, account.getPassword()))
                .findFirst();
    }
}
