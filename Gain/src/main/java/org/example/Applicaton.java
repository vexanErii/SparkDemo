package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling   //开启定时任务
public class Applicaton {
    public static void main(String[] args){
        SpringApplication.run(Applicaton.class,args);
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lenovo\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver");
//        WebDriver driver = new ChromeDriver();
//        String url = "https://sou.zhaopin.com/?jl=703&kw=java&p=";
//        for (int i = 1; i < 14; i++) {
//            String url1 = url + 1;
//            TaskJob.process(url1);
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
}
