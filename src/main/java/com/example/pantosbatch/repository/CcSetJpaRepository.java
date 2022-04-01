package com.example.pantosbatch.repository;

import com.example.pantosbatch.entity.CcSet;
import com.example.pantosbatch.entity.CcUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CcSetJpaRepository extends JpaRepository<CcSet, Integer>, JpaSpecificationExecutor<CcSet> {
    CcSet findByCcSetSeq(int ccSetSeq);
}
