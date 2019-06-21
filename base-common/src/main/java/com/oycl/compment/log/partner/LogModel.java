package com.oycl.compment.log.partner;

/**
 * 自定义log内容
 */
public class LogModel {
    /** 模块*/
    private String module;
    /** 操作*/
    private String event;
    /** 操作内容*/
    private double createDate;
    /** 操作内容 */
    private String optContent;
    /** 备注 */
    private String desc;
    /** 状态 */
    private String status;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public double getCreateDate() {
        return createDate;
    }

    public void setCreateDate(double createDate) {
        this.createDate = createDate;
    }

    public String getOptContent() {
        return optContent;
    }

    public void setOptContent(String optContent) {
        this.optContent = optContent;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
