package org.example.task;

import org.example.pojo.JobInfo;
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
public class JobProocessor implements PageProcessor {

    private String url = "https://b.faloo.com/r_0_3.html";
    @Override
    public void process(Page page) {
        //解析页面，获取招聘信息详情的url地址
        List<Selectable> list = page.getHtml().css("div.TwoBox02_08 h1.fontSize17andHei").nodes();
        //判断获取到的集合是否为空
        if (list.size() == 0) {
            //如果为空，表示这是小说的详情页，解析页面，获取小说信息详情，保存数据
            this.saveJobInfo(page);

        }else {
            //如果不为空，表示这是列表页,解析出详情页的url地址，放到任务队列中
            for (Selectable selectable : list) {
                //获取url地址
                String jobInfoUrl = selectable.links().toString();
                //把获取到的url地址放到任务队列中
                page.addTargetRequest(jobInfoUrl);
            }
            //获取下一页的url
            String bkUrl = page.getHtml().css("div.pageliste_body a").nodes().get(6).links().toString();
            //把url放到任务队列中
            page.addTargetRequest(bkUrl);
        }

        String html = page.getHtml().toString();

    }
    //解析页面，获取招聘信息详情，保存数据
    private void saveJobInfo(Page page){
        //创建招聘详情对象
        JobInfo jobInfo = new JobInfo();

        //解析页面
        Html html = page.getHtml();

        //获取数据，封装到对象中
        jobInfo.setNovelName(html.css("div.T-L-O-Z-Box1 h1","text").toString());
        jobInfo.setNovelAuthor(html.css("div.T-L-O-Z-Box1 a","text").toString());
        jobInfo.setNovelType(Jsoup.parse(html.css("div.T-R-T-Box2 div.T-R-T-B2-Box1").nodes().get(0).toString()).text());
        jobInfo.setNovelMouthClick(Jsoup.parse(html.css("div.T-L-O-Z-Box2 span").nodes().get(0).toString()).text());
        jobInfo.setNovelClick(Jsoup.parse(html.css("div.T-L-O-Z-Box2 span").nodes().get(4).toString()).text());
        jobInfo.setNovelMouthRecommend(Jsoup.parse(html.css("div.T-L-O-Z-Box2 span").nodes().get(2).toString()).text());
        jobInfo.setNovelRecommend(Jsoup.parse(html.css("div.T-L-O-Z-Box2 span").nodes().get(6).toString()).text());
        jobInfo.setNovelCount(Jsoup.parse(html.css("div.T-R-Md-Bobx1 span").all().toString()).text());
        jobInfo.setNovel_detail(Jsoup.parse(html.css("div.T-L-T-C-Box1 p").all().toString()).text());
        jobInfo.setTime(html.css("div.T-L-O-Z-Box1 span.colorLv","text").toString());
        jobInfo.setUrl(page.getUrl().toString());

        //把结果保存起来
        page.putField("jobInfo",jobInfo);
    }

    private Site site = Site.me()
            .setCharset("gbk")//设置编码
            .setTimeOut(10*1000)//设置超时时间
            .setRetrySleepTime(3000)//设置重试的时间间隔
            .setRetryTimes(3);//设置重试的次数

    @Autowired
    private SpringDataPipeline springDataPipeline;
    @Override
    public Site getSite() {
        return site;
    }
    //initialDelay当任务启动后，等多久执行方法
    //fixedDelay每隔多久执行方法
    @Scheduled(initialDelay = 1000,fixedDelay = 100*1000)
    public void process(){
        Spider.create(new JobProocessor())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                .addPipeline(this.springDataPipeline)
                .run();
    }
}
