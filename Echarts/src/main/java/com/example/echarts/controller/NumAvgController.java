package com.example.echarts.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.echarts.dao.NumAvgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("ChapterNumAvg")
public class NumAvgController {
    @Autowired
    private NumAvgDao numAvgDao;

    @GetMapping
    public Object echarts(){
        //查询数据库 echarts表的所有数据
        return numAvgDao.selectList(Wrappers.lambdaQuery());
    }
}


