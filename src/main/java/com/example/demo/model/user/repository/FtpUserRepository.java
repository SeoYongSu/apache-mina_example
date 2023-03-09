package com.example.demo.model.user.repository;

import com.example.demo.model.user.domain.FtpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FtpUserRepository extends JpaRepository<FtpUser,Long> {
    Optional<FtpUser> findByUserid(String userid);
}
