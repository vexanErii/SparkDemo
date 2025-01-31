package com.example.echarts.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.echarts.dao.TypeClickDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("NovelTypeClick")
public class TypeClickController {
    @Autowired
    private TypeClickDao typeClickDao;

    @GetMapping
    public Object echarts(){
        //查询数据库 echarts表的所有数据
        return typeClickDao.selectList(Wrappers.lambdaQuery());
    }
}


