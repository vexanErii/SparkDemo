package org.example.pojo;

import javax.persistence.*;

@Entity
@Table(name = "novel_message1")
public class JobInfo {
    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //主键id
    private Long id;
    //小说名称
    private String novelName;
    //作者名称
    private String novelAuthor;
    //小说月点击量
    private String novelMouthClick;
    //小说点击量
    private String novelClick;
    //小说字数
    private String novelCount;
    //小说类型
    private String novelType;
    //小说月推荐票
    private String novelMouthRecommend;
    //小说推荐票
    private String novelRecommend;
    //小说简介
    private String novel_detail;

    //小说最后的更新时间
    private String time;

    //小说详情页信息
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public String getNovelAuthor() {
        return novelAuthor;
    }

    public void setNovelAuthor(String novelAuthor) {
        this.novelAuthor = novelAuthor;
    }

    public String getNovelClick() {
        return novelClick;
    }

    public void setNovelClick(String novelClick) {
        this.novelClick = novelClick;
    }

    public String getNovelCount() {
        return novelCount;
    }

    public void setNovelCount(String novelCount) {
        this.novelCount = novelCount;
    }

    public String getNovelType() {
        return novelType;
    }

    public void setNovelType(String novelType) {
        this.novelType = novelType;
    }

    public String getNovelRecommend() {
        return novelRecommend;
    }

    public void setNovelRecommend(String novelRecommend) {
        this.novelRecommend = novelRecommend;
    }

    public String getNovel_detail() {
        return novel_detail;
    }

    public void setNovel_detail(String novel_detail) {
        this.novel_detail = novel_detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNovelMouthClick() {
        return novelMouthClick;
    }

    public void setNovelMouthClick(String novelMouthClick) {
        this.novelMouthClick = novelMouthClick;
    }

    public String getNovelMouthRecommend() {
        return novelMouthRecommend;
    }

    public void setNovelMouthRecommend(String novelMouthRecommend) {
        this.novelMouthRecommend = novelMouthRecommend;
    }

    @Override
    public String toString() {
        return "JobInfo{" +
                "id=" + id +
                ", novelName='" + novelName + '\'' +
                ", novelAuthor='" + novelAuthor + '\'' +
                ", novelMouthClick='" + novelMouthClick + '\'' +
                ", novelClick='" + novelClick + '\'' +
                ", novelCount='" + novelCount + '\'' +
                ", novelType='" + novelType + '\'' +
                ", novelMouthRecommend='" + novelMouthRecommend + '\'' +
                ", novelRecommend='" + novelRecommend + '\'' +
                ", novel_detail='" + novel_detail + '\'' +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
