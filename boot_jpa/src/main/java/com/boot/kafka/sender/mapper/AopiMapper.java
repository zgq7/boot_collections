package com.boot.kafka.sender.mapper;

import com.boot.kafka.sender.model.Aopi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
* 功能：xx
*
* @return
* @param
* @author Leethea
* @date 2020/1/17 15:47
**/
@Repository
public interface AopiMapper extends JpaRepository<Aopi,Integer> {

}
