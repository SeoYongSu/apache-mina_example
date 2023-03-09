package com.example.demo.model.user.service;

import com.example.demo.model.user.data.FtpUserSignup;
import com.example.demo.model.user.domain.FtpUser;
import com.example.demo.model.user.repository.FtpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FtpUserService {
    private final FtpUserRepository ftpUserRepository;

    public void ftpUserRegist(FtpUserSignup reqData) {
        FtpUser ftpUser = FtpUser.builder()
                .userid(reqData.getFtpID())
                .userpassword(reqData.getPassword())
                .build();
        ftpUserRepository.save(ftpUser);

    }

}
