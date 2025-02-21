package org.example.service.impl;

import org.example.dao.JobInfoDao;
import org.example.pojo.JobInfo;
import org.example.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class JobInfoServiceImpl implements JobInfoService {
    @Autowired
    private JobInfoDao jobInfoDao;

    @Override
    @Transactional
    public void save(JobInfo jobInfo) {
        //根据url和发布时间查询数据
        JobInfo param = new JobInfo();
        param.setUrl(jobInfo.getUrl());
        param.setTime(jobInfo.getTime());

        //执行查询
        List<JobInfo> list = this.findJobInfo(param);

        //判断查询结果是否为空
        if (list.size()==0){
            //如果查询结果为空，表示招聘信息数据不存在，或者已经更新了，需要新增或者更新数据
            this.jobInfoDao.saveAndFlush(jobInfo);
        }

    }

    @Override
    public List<JobInfo> findJobInfo(JobInfo jobInfo) {

        //设置查询条件
        Example example = Example.of(jobInfo);
        //执行查询
        List list =  this.jobInfoDao.findAll(example);

        return list;
    }
}
