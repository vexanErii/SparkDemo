package com.niit.job.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Jobinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String novel_name;
    private String novel_author;
    private String novel_update;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNovel_name() {
        return novel_name;
    }

    public void setNovel_name(String novel_name) {
        this.novel_name = novel_name;
    }

    public String getNovel_author() {
        return novel_author;
    }

    public void setNovel_author(String novel_author) {
        this.novel_author = novel_author;
    }

    public String getNovel_update() {
        return novel_update;
    }

    public void setNovel_update(String novel_update) {
        this.novel_update = novel_update;
    }

    public String getNovel_mounthread() {
        return novel_mounthread;
    }

    public void setNovel_mounthread(String novel_mounthread) {
        this.novel_mounthread = novel_mounthread;
    }

    public String getNovel_introduce() {
        return novel_introduce;
    }

    public void setNovel_introduce(String novel_introduce) {
        this.novel_introduce = novel_introduce;
    }


    public String getNovel_recommend() {
        return novel_recommend;
    }

    public void setNovel_recommend(String novel_recommend) {
        this.novel_recommend = novel_recommend;
    }

    public String getNovel_recommend_week() {
        return novel_recommend_week;
    }

    public void setNovel_recommend_week(String novel_recommend_week) {
        this.novel_recommend_week = novel_recommend_week;
    }

    public String getNovel_read() {
        return novel_read;
    }

    public void setNovel_read(String novel_read) {
        this.novel_read = novel_read;
    }


    private String novel_mounthread;
    private String novel_introduce;
    private String novel_recommend;
    private String novel_recommend_week;
    private String novel_read;
    private String novel_type;

    public String getNovel_type() {
        return novel_type;
    }

    public void setNovel_type(String novel_type) {
        this.novel_type = novel_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;



    @Override
    public String toString() {
        return "Jobinfo{" +
                "id=" + id +
                ", novel_name='" + novel_name + '\'' +
                ", novel_author='" + novel_author + '\'' +
                ", novel_update='" + novel_update + '\'' +
                ", novel_mounthread='" + novel_mounthread + '\'' +
                ", novel_introduce='" + novel_introduce + '\'' +
                ", novel_recommend='" + novel_recommend + '\'' +
                ", novel_recommend_week='" + novel_recommend_week + '\'' +
                ", novel_read='" + novel_read + '\'' +
                ", novel_type='" + novel_type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
