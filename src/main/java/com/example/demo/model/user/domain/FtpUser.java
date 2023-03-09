package com.example.demo.model.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ftpserver.usermanager.PasswordEncryptor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FtpUser{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(64) comment 'FTP 계정'")
    private String userid;

    @Column(nullable = false, columnDefinition = "varchar(255) comment 'FTP비밀번호'")
    private String userpassword;

    @Column(nullable = false, columnDefinition = "varchar(128) not null comment '계정의 home디렉토리'")
    private String homedirectory;

    @Column(columnDefinition = "varchar(30) comment '사용자 Type'")
    private String userType;

    @Column(columnDefinition = "varchar(10) comment '권한 유형'")
    private String roleType;

    //사용 여부
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE comment '계정 활성화여부'")
    private boolean enableflag;

    //쓰기 권한
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE comment '쓰기권한 false:0  true:1'")
    private boolean writepermission;


    @Column(columnDefinition = "INT DEFAULT 0 comment '유휴시간'")
    private int idletime;

    //업로드 권한
    @Column(columnDefinition = "INT DEFAULT 0 comment '업로드권한 false:0  true:1'")
    private int uploadrate;

    //다운로드 권한
    @Column(columnDefinition = "INT DEFAULT 0 comment '다운로드권한 false:0  true:1'")
    private int downloadrate;

    @Column(columnDefinition = "INT DEFAULT 0 comment '최대 로그인 갯수'")
    private int maxloginnumber;

    //접근 허용 IP 갯수
    @Column(columnDefinition = "INT DEFAULT 0 comment '접근허용 IP 갯수'")
    private int maxloginperip;



    //PASSWORD Encoding 함수
    public void setEncodigPassword(PasswordEncryptor passwordEncryptor, String strPassword) {
        this.userpassword = passwordEncryptor.encrypt(strPassword);
    }
}
