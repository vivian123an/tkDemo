package com.baturu.tkDemo.entity;

public class PtProvince {
    private Long provinceid;

    private Integer regionid;

    private String provincecode;

    private String provincename;

    private Integer qmskregionid;

    private Integer hot;

    public Long getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(Long provinceid) {
        this.provinceid = provinceid;
    }

    public Integer getRegionid() {
        return regionid;
    }

    public void setRegionid(Integer regionid) {
        this.regionid = regionid;
    }

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode == null ? null : provincecode.trim();
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename == null ? null : provincename.trim();
    }

    public Integer getQmskregionid() {
        return qmskregionid;
    }

    public void setQmskregionid(Integer qmskregionid) {
        this.qmskregionid = qmskregionid;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }
}