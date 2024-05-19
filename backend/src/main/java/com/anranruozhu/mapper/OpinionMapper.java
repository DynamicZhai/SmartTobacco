package com.anranruozhu.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpinionMapper {
    // 添加意见
    void addOpinion(String phone_number, String description);
}
