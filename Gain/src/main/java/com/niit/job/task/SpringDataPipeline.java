package com.niit.job.task;

import com.niit.job.pojo.Jobinfo;
import com.niit.job.service.Jobinfoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class SpringDataPipeline implements Pipeline {
    @Autowired
    private Jobinfoservice jobInfoService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取封装好的小说详情对象
        Jobinfo jobInfo = resultItems.get("jobinfo");
        //判断数据是否不为空
        if(jobInfo != null){
            //如果数据不为空把数据保存到数据库中
            this.jobInfoService.save(jobInfo);
        }
    }
}