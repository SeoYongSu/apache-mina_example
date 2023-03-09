package com.example.demo.app.propertis;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(value = "ftp")
public class FtpProperties {

    //기본 FTP 서버 Root Directory
    private String rootPath = System.getProperty("user.dir") + "/ftp";

    private String host;
    private int port = 10000;

    private int passivePortStart = 10001;
    // ftp passive mode setting end port
    private int passivePortEnd = 10500;

}