package com.example.pantosbatch.repository;

import com.example.pantosbatch.entity.CcUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CcUserJpaRepository extends JpaRepository<CcUser, String>, JpaSpecificationExecutor<CcUser> {

    CcUser findByUserSeq(int userSeq);
    // 계정 잠금되지 않고,
    CcUser findByLoginDtAndLockYn(String loginDt, String lockYn);
}
