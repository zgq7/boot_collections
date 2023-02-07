package com.boot.kafka.sender.mapper;

import com.boot.kafka.sender.model.Stu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 功能：xx
 *
 * @param
 * @author Leethea
 * @return
 * @date 2020/1/17 15:47
 **/
@Repository
public interface StuMapper extends JpaRepository<Stu, Integer> {

}
