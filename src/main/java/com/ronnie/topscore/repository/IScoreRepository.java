package com.ronnie.topscore.repository;

import com.ronnie.topscore.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IScoreRepository extends JpaRepository<Score, Long>, JpaSpecificationExecutor<Score> {

}
