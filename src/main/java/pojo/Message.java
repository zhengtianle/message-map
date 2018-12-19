package pojo;

import java.util.Date;

public class Message {
    private Integer mid;

    private Integer uid;

    private String location;

    private String content;

    private Date time;

    private Integer stars;

    public Message(Integer mid, Integer uid, String location, String content, Date time, Integer stars) {
        this.mid = mid;
        this.uid = uid;
        this.location = location;
        this.content = content;
        this.time = time;
        this.stars = stars;
    }

    public Message() {
        super();
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}