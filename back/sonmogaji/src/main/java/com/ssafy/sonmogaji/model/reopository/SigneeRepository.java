package com.ssafy.sonmogaji.model.reopository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.sonmogaji.model.entity.Signees;

public interface SigneeRepository extends JpaRepository<Signees, Long>{

}
