package com.niit.job.task;

import com.niit.job.pojo.Jobinfo;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
public class JobProcess implements PageProcessor {

    private String url = "https://b.faloo.com/y_0_0_0_0_0_0_130.html";

    @Override
    public void process(Page page) {

        List<Selectable> list = page.getHtml().css("div.TwoBox02_08 h1.fontSize17andHei").nodes();

        if (list.size() == 0){
            this.saveJobinfo(page);
        }else {
            for (Selectable selectable : list) {
                String jobInfoUrl = selectable.links().toString();
                page.addTargetRequest(jobInfoUrl);
            }
            String bkUrl = page.getHtml().css("div.pageliste_body a").nodes().get(6).links().toString();
            page.addTargetRequest(bkUrl);
        }

        String html = page.getHtml().toString();

    }

    private void saveJobinfo(Page page) {
        Jobinfo jobinfo = new Jobinfo();

        Html html = page.getHtml();

        jobinfo.setNovel_name(html.css("div.T-L-O-Z-Box1 h1","text").toString());
        jobinfo.setNovel_author(html.css("div.T-L-O-Z-Box1 a","text").toString());
        jobinfo.setNovel_update(html.css("div.T-L-O-Z-Box1 span.colorLv","text").toString());
        jobinfo.setNovel_mounthread(Jsoup.parse(html.css("div.T-L-O-Z-Box2 span.colorHei").nodes().get(0).toString()).text());
        jobinfo.setNovel_introduce(Jsoup.parse(html.css("div.T-L-T-C-Box1 p").all().toString()).text());
        jobinfo.setNovel_recommend(Jsoup.parse(html.css("div.T-L-O-Z-Box2 span.colorHei").nodes().get(3).toString()).text());
        jobinfo.setNovel_recommend_week(Jsoup.parse(html.css("div.T-L-O-Z-Box2 span.colorHei").nodes().get(1).toString()).text());
        jobinfo.setNovel_read(Jsoup.parse(html.css("div.T-L-O-Z-Box2 span.colorHei").nodes().get(2).toString()).text());
        jobinfo.setNovel_type(Jsoup.parse(html.css("div.T-R-T-Box2 div.T-R-T-B2-Box1").nodes().get(0).toString()).text());
        jobinfo.setUrl(page.getUrl().toString());

//        page.putField("jobInfo",Jobinfo);
        page.putField("jobinfo",jobinfo);
    }

    private Site site = Site.me()
            .setCharset("gbk")
            .setTimeOut(10*1000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);


    @Autowired
    private SpringDataPipeline springDataPipeline;
    @Override
    public Site getSite() {
        return site;
    }

    @Scheduled(initialDelay = 100,fixedDelay = 100*1000)
    public void process(){
        Spider.create(new JobProcess())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                .addPipeline(this.springDataPipeline)
                .run();
    }
}
