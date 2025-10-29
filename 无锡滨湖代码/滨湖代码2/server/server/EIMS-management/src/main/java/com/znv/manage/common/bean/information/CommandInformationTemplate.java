package com.znv.manage.common.bean.information;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CommandInformationTemplate {
    private Long id;

    private String templateTitle;

    private String templateType1;

    private String templateType2;

    private String templateProducer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date produceTime;

    private String templateContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle == null ? null : templateTitle.trim();
    }

    public String getTemplateType1() {
        return templateType1;
    }

    public void setTemplateType1(String templateType1) {
        this.templateType1 = templateType1 == null ? null : templateType1.trim();
    }

    public String getTemplateType2() {
        return templateType2;
    }

    public void setTemplateType2(String templateType2) {
        this.templateType2 = templateType2 == null ? null : templateType2.trim();
    }

    public String getTemplateProducer() {
        return templateProducer;
    }

    public void setTemplateProducer(String templateProducer) {
        this.templateProducer = templateProducer == null ? null : templateProducer.trim();
    }

    public Date getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(Date produceTime) {
        this.produceTime = produceTime;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent == null ? null : templateContent.trim();
    }
}