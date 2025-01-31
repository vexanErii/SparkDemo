package com.niit.job.service.impl;

import com.niit.job.dao.JoninfoDao;
import com.niit.job.pojo.Jobinfo;
import com.niit.job.service.Jobinfoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class Jobinfoserviceimpl implements Jobinfoservice {

    @Autowired
    private JoninfoDao jobInfoDao;

    @Override
    @Transactional
    public void save(Jobinfo jobInfo) {
        Jobinfo param = new Jobinfo();
        param.setUrl(jobInfo.getUrl());
        param.setNovel_update(jobInfo.getNovel_update());

        List<Jobinfo> list = this.findJobInfo(param);

        if (list.size() == 0) {
            this.jobInfoDao.saveAndFlush(jobInfo);

        }
    }

    @Override
    public List<Jobinfo> findJobInfo(Jobinfo jobInfo) {

        Example example = Example.of(jobInfo);

        List list = this.jobInfoDao.findAll(example);

        return list;
    }
}
