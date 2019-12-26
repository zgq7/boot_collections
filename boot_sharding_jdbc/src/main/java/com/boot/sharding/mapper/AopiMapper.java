package com.boot.sharding.mapper;

import com.boot.sharding.model.Aopi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AopiMapper {

    List<Aopi> selectAll();

    Aopi selectById(@Param("id") Integer id);

    Integer insert(@Param("aopi") Aopi aopis);

    Integer insertList(@Param("aopis") List<Aopi> aopis);
}