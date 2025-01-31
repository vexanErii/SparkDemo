package com.example.echarts.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.echarts.dao.TypeNumDao;
import com.example.echarts.dao.UpdataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("NovelUpdated")
public class UpdataController {
    @Autowired
    private UpdataDao updataDao;

    @GetMapping
    public Object echarts(){
        //查询数据库 echarts表的所有数据
        return updataDao.selectList(Wrappers.lambdaQuery());
    }
}


