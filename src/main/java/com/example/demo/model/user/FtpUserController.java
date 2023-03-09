package com.example.demo.model.user;

import com.example.demo.model.user.data.FtpUserSignup;
import com.example.demo.model.user.service.FtpUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FtpUserController {
    private final FtpUserService ftpUserService;

    /**
     * FTP 계정 생성
     * @Method Name : regist
     * @작성일 : 2022. 8. 29.
     * @param reqData
     */
    @PostMapping("/regist")
    public void regist(@RequestBody FtpUserSignup reqData) {
        ftpUserService.ftpUserRegist(reqData);
    }
    
}
