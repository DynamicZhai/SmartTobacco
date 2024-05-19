package com.anranruozhu.service.impl;

import cn.hutool.json.JSONObject;
import com.anranruozhu.common.Result;
import com.anranruozhu.mapper.OpinionMapper;
import com.anranruozhu.service.OpinionService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OpinionServiceImpl implements OpinionService {
    @Autowired
    OpinionMapper opinionMapper;
    @Override
    public Result addOpinion(String phone, String description) {
        Result res = new Result();
        try {
            opinionMapper.addOpinion(phone, description);
        }catch (Exception e) {
            log.error("error: " + e.getMessage());
            throw new RuntimeException("意见添加失败");
        }
        res.setCode(200);
        JSONObject data = new JSONObject();
        data.set("phone_number", phone).set("description", description);
        res.setData(data);
        res.setMsg("意见存储成功！");
        return res;
    }
}
