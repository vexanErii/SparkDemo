package org.example.service;

import org.example.pojo.JobInfo;

import java.util.List;

public interface JobInfoService {

    //保存小说信息
    public void save(JobInfo jobInfo);

    //根据条件查询小说信息
    public List<JobInfo> findJobInfo(JobInfo jobInfo);
}
