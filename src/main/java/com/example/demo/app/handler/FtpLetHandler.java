package com.example.demo.app.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.ftplet.*;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
//InterFace : FtpLet
public class FtpLetHandler  extends DefaultFtplet {


    @Override
    public FtpletResult onConnect(FtpSession session) throws FtpException, IOException {
        log.info("****** onConnect ******");
        return super.onConnect(session);
    }

    @Override
    public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException {
        return FtpletResult.DISCONNECT;
    }

    @Override
    public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return super.onLogin(session, request);
    }

    @Override
    public FtpletResult onUploadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        log.info("-- onUploadStart -- ThreadID  : {}   //  sessionID : {}",Thread.currentThread().getId(), session.getSessionId());
        return super.onUploadStart(session, request);
    }

    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        FileSystemView fileSystemView = session.getFileSystemView();
        FtpFile file = fileSystemView.getFile(request.getArgument());
        log.info(" file Info  : {}" , file);
        log.info("-- onUploadEnd -- ThreadID  : {}   //  sessionID : {}",Thread.currentThread().getId(), session.getSessionId());
        return super.onUploadEnd(session, request);
    }


}
