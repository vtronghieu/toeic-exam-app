package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.models.Account;
import com.tip.dg4.toeic_exam.models.AccountToken;
import com.tip.dg4.toeic_exam.repositories.AccountTokenRepository;
import com.tip.dg4.toeic_exam.services.AccountTokenService;
import com.tip.dg4.toeic_exam.services.JwtService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class AccountTokenServiceImpl implements AccountTokenService {
    private final AccountTokenRepository accountTokenRepository;
    private final JwtService jwtService;
    public AccountTokenServiceImpl(AccountTokenRepository accountTokenRepository,
                                   JwtService jwtService) {
        this.accountTokenRepository = accountTokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public String createToken(Account account) {
        Optional<AccountToken> optionalAccountToken = accountTokenRepository.findByAccountId(account.getId());
        AccountToken accountToken;
        String token;

        if (optionalAccountToken.isPresent()) {
            accountToken = optionalAccountToken.get();
            accountToken.setToken(jwtService.generateToken(account.getUsername()));
            token = accountToken.getToken();

            accountTokenRepository.save(accountToken);
        } else {
            accountToken = new AccountToken();
            token = jwtService.generateToken(account.getUsername());

            accountToken.setAccountId(account.getId());
            accountToken.setToken(token);
            accountToken.setActive(true);
            accountTokenRepository.save(accountToken);
        }

        return token;
    }
}
