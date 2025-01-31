package com.example.echarts.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.echarts.dao.TypeClickDao;
import com.example.echarts.dao.TypeNumDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("NovelTypeNum")
public class TypeNumController {
    @Autowired
    private TypeNumDao typeNumDao;

    @GetMapping
    public Object echarts(){
        //查询数据库 echarts表的所有数据
        return typeNumDao.selectList(Wrappers.lambdaQuery());
    }
}


