package com.niit.job.service;

import com.niit.job.pojo.Jobinfo;

import java.util.List;

public interface Jobinfoservice {

    public void save(Jobinfo jobInfo);

    public List<Jobinfo> findJobInfo(Jobinfo jobInfo);
}
