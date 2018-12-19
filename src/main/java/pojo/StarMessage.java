package pojo;

import java.util.Date;

public class StarMessage {
    private Integer mid;

    private Integer stid;

    private Integer sfid;

    private Date time;

    public StarMessage(Integer mid, Integer stid, Integer sfid, Date time) {
        this.mid = mid;
        this.stid = stid;
        this.sfid = sfid;
        this.time = time;
    }

    public StarMessage() {
        super();
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getStid() {
        return stid;
    }

    public void setStid(Integer stid) {
        this.stid = stid;
    }

    public Integer getSfid() {
        return sfid;
    }

    public void setSfid(Integer sfid) {
        this.sfid = sfid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}