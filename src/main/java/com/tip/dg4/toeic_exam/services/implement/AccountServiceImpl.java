package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.AccountDto;
import com.tip.dg4.toeic_exam.dto.LoginDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.UnauthorizedException;
import com.tip.dg4.toeic_exam.mappers.AccountMapper;
import com.tip.dg4.toeic_exam.models.Account;
import com.tip.dg4.toeic_exam.repositories.AccountRepository;
import com.tip.dg4.toeic_exam.repositories.UserRepository;
import com.tip.dg4.toeic_exam.services.AccountService;
import com.tip.dg4.toeic_exam.services.JwtService;
import com.tip.dg4.toeic_exam.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository,
                              UserRepository userRepository,
                              AccountMapper accountMapper,
                              JwtService jwtService,
                              UserService userService,
                              PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void loginAccount(LoginDto loginDto, HttpServletResponse response) {
        Optional<Account> optionalAccount = findOneByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        if (optionalAccount.isEmpty()) {
            throw new UnauthorizedException(TExamExceptionConstant.ACCOUNT_E004);
        }

        String accessToken = jwtService.generateToken(loginDto.getUsername());
        Cookie authCookie = new Cookie(TExamConstant.ACCESS_TOKEN, accessToken);
        authCookie.setMaxAge(60 * 60 * 24 * 7);
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        response.addCookie(authCookie);
    }

    @Override
    public void registerAccount(RegisterDto registerDto) {
        if (accountRepository.existsByUsername(registerDto.getUsername())) {
            throw new BadRequestException(TExamExceptionConstant.ACCOUNT_E002 + registerDto.getUsername());
        }
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new BadRequestException(TExamExceptionConstant.ACCOUNT_E003);
        }

        Account account = accountMapper.convertRegisterDtoToModel(registerDto);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        userService.createUser(registerDto, account.getId());
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream().map(accountMapper::convertModelToDto).toList();
    }

    @Override
    public AccountDto getAccountByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.ACCOUNT_E001 + username));

        return accountMapper.convertModelToDto(account);
    }

    private Optional<Account> findOneByUsernameAndPassword(String username, String password) {
        return accountRepository.findAll().stream()
                .filter(account -> username.equals(account.getUsername()) && passwordEncoder.matches(password, account.getPassword()))
                .findFirst();
    }
}
