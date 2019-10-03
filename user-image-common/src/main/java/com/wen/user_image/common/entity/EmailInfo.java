package com.wen.user_image.common.entity;

/**
 * Created by li on 2019/1/5.
 */
public class EmailInfo {
    private String emailCompanyName;
    private Long emailCount;
    private String groupField;

    public String getEmailCompanyName() {
        return emailCompanyName;
    }

    public void setEmailCompanyName(String emailCompanyName) {
        this.emailCompanyName = emailCompanyName;
    }

    public Long getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(Long emailCount) {
        this.emailCount = emailCount;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }

    @Override
    public String toString() {
        return "EmaiInfo{" +
                "emailCompanyName='" + emailCompanyName + '\'' +
                ", emailCount=" + emailCount +
                ", groupField='" + groupField + '\'' +
                '}';
    }
}
