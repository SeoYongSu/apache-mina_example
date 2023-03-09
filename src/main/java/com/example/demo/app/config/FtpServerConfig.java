package com.example.demo.app.config;

import com.example.demo.app.handler.FtpLetHandler;
import com.example.demo.app.propertis.FtpProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.DbUserManagerFactory;
import org.apache.ftpserver.usermanager.UserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
@Component
public class FtpServerConfig {

    private final FtpProperties ftpProperties;
    private final DataSource dataSource;

    @PostConstruct
    public void initFtp() {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();
        //1.Port 설정
        listenerFactory.setPort(ftpProperties.getPort());
        
        //2. DataConnectionConfigurationFactory 생성
        DataConnectionConfigurationFactory dataConnectionConfFactory = new DataConnectionConfigurationFactory();
        // PassvicePort 설정
        //dataConnectionConfFactory.setPassivePorts("10001-10500");
        dataConnectionConfFactory.setPassivePorts(ftpProperties.getPassivePortStart() + "-" +ftpProperties.getPassivePortEnd());
        listenerFactory.setDataConnectionConfiguration(dataConnectionConfFactory.createDataConnectionConfiguration());

        //3. 리스너 생성
        Listener listener = listenerFactory.createListener();
        serverFactory.addListener("default", listener);

        //4. ftpLet 설정
        //Component등을 만들어서 Ftplet에 설정
        Map<String, Ftplet> ftplet = new HashMap<>();
        //ftplet.put("Ftplet name", handler 객체);
        serverFactory.setFtplets(ftplet);

        UserManagerFactory dbUserManagerFactory = ftpUserMangerFactory();
        UserManager um = dbUserManagerFactory.createUserManager();
        serverFactory.setUserManager(um);


        // Base User 생성
        BaseUser user = new BaseUser();
        user.setName("admin");
        user.setPassword("1234");
        user.setHomeDirectory("/");
        user.setEnabled(true);
        try {
            um.save(user);
        } catch (FtpException e1) {
            e1.printStackTrace();
        }




        FtpServer server = serverFactory.createServer();
        try {
            server.start();
            log.info("FTP start ~~~~~~~~~~~~~~~~~");
        } catch (FtpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //여기 JPA로 수정 예정임.  ->> 확인결과 JPA로는 JDBC 커넥션이 계속 물려있어 안쓰는게 좋을듯함.
    private UserManagerFactory ftpUserMangerFactory() {
        DbUserManagerFactory dbUserManagerFactory = new DbUserManagerFactory();
        //todo....
        dbUserManagerFactory.setDataSource(dataSource);
        dbUserManagerFactory.setAdminName("admin");
        dbUserManagerFactory.setSqlUserAdmin("SELECT userid FROM ftp_user WHERE userid='{userid}' AND userid='admin'");
        dbUserManagerFactory.setSqlUserInsert("INSERT INTO ftp_user (userid, userpassword, homedirectory, " +
                "enableflag, writepermission, idletime, uploadrate, downloadrate) VALUES " +
                "('{userid}', '{userpassword}', '{homedirectory}', {enableflag}, " +
                "{writepermission}, {idletime}, {uploadrate}, {downloadrate})");
        dbUserManagerFactory.setSqlUserDelete("DELETE FROM ftp_user WHERE userid = '{userid}'");
        dbUserManagerFactory.setSqlUserUpdate("UPDATE ftp_user SET userpassword='{userpassword}',homedirectory='{homedirectory}',enableflag={enableflag},writepermission={writepermission},idletime={idletime},uploadrate={uploadrate},downloadrate={downloadrate},maxloginnumber={maxloginnumber}, maxloginperip={maxloginperip} WHERE userid='{userid}'");
        dbUserManagerFactory.setSqlUserSelect("SELECT * FROM ftp_user WHERE userid = '{userid}'");
        dbUserManagerFactory.setSqlUserSelectAll("SELECT userid FROM ftp_user ORDER BY userid");
        dbUserManagerFactory.setSqlUserAuthenticate("SELECT userid, userpassword FROM ftp_user WHERE userid='{userid}'");

        //Encoding 설정
        //여기서는 임시로 인코딩 없이 일반 Text로 설정
        dbUserManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());

        return dbUserManagerFactory;
    }
}
