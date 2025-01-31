package com.example.echarts.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.echarts.dao.ClickRedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("CountClickRed")
public class ClickRedController {
    @Autowired
    private ClickRedDao clickRedDao;
//
//    @RequestMapping("/list")
//    public List<ClickRedEntity> list(){
//        return clickRedService.list();
//    }
    @GetMapping
    public Object echarts(){
    //查询数据库 echarts表的所有数据
        return clickRedDao.selectList(Wrappers.lambdaQuery());
    }
}


