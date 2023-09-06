package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.*;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.UnauthorizedException;
import com.tip.dg4.toeic_exam.mappers.AccountMapper;
import com.tip.dg4.toeic_exam.models.Account;
import com.tip.dg4.toeic_exam.models.AccountRole;
import com.tip.dg4.toeic_exam.repositories.AccountRepository;
import com.tip.dg4.toeic_exam.services.AccountService;
import com.tip.dg4.toeic_exam.services.JwtService;
import com.tip.dg4.toeic_exam.services.UserService;
import com.tip.dg4.toeic_exam.utils.ApiUtil;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              JwtService jwtService,
                              UserService userService,
                              PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationDto loginAccount(LoginDto loginDto) {
        Optional<Account> optionalAccount = findOneByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        if (optionalAccount.isEmpty()) {
            throw new UnauthorizedException(TExamExceptionConstant.ACCOUNT_E004);
        }

        Account account = optionalAccount.get();
        String token = jwtService.generateToken(loginDto.getUsername());

        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setUsername(account.getUsername());
        authenticationDto.setId(account.getId());
        authenticationDto.setRole(AccountRole.getValueRole(account.getRole()));
        authenticationDto.setToken(token);

        return authenticationDto;
    }

    @Override
    public void logoutAccount(HttpServletRequest request, HttpServletResponse response) {
        Cookie authCookie = TExamUtil.getAuthCookie(request);
        if (Objects.nonNull(authCookie)) {
            authCookie.setValue(TExamConstant.EMPTY);
            authCookie.setMaxAge(0);
            authCookie.setPath(TExamConstant.SLASH);
            response.addCookie(authCookie);
        }
    }

    @Override
    public void registerAccount(RegisterDto registerDto) {
        if (accountRepository.existsByUsername(registerDto.getUsername())) {
            throw new ConflictException(TExamExceptionConstant.ACCOUNT_E002 + registerDto.getUsername());
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

    @Override
    public AccountDto getAccountById(UUID idAccount) {
        Account account = accountRepository.findById(idAccount)
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.ACCOUNT_E001));

        return accountMapper.convertModelToDto(account);
    }

    @Override
    public void changePasswordAccount(HttpServletRequest request, ChangePasswordDto changePasswordDto) {
        Account account = accountRepository.findById(changePasswordDto.getId())
                          .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.ACCOUNT_E006));
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), account.getPassword())) {
            throw new BadRequestException(TExamExceptionConstant.ACCOUNT_E007);
        }
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
            throw new BadRequestException(TExamExceptionConstant.ACCOUNT_E008);
        }

        account.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        accountRepository.save(account);
    }

    private Optional<Account> findOneByUsernameAndPassword(String username, String password) {
        return accountRepository.findAll().stream()
                .filter(account -> username.equals(account.getUsername()) &&
                        passwordEncoder.matches(password, account.getPassword()))
                .findFirst();
    }
}
