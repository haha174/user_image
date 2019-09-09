package com.wen.user_image.job.entity;

public class YearBase {
   private String groupField;

    /**
     * when people born
     */
    private int bornYear;
    /**
     * 年代类型
     */
    private String yearType;
    /**
     * 数量
     */
    private long yearCount;

    public int getBornYear() {
        return bornYear;
    }

    public void setBornYear(int bornYear) {
        this.bornYear = bornYear;
    }



    public long getYearCount() {
        return yearCount;
    }

    public void setYearCount(long yearCount) {
        this.yearCount = yearCount;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }

    public String getYearType() {
        return yearType;
    }

    public void setYearType(String yearType) {
        this.yearType = yearType;
    }

    @Override
    public String toString() {
        return "YearBase{" +
                "groupField='" + groupField + '\'' +
                ", bornYear=" + bornYear +
                ", yearType='" + yearType + '\'' +
                ", yearCount=" + yearCount +
                '}';
    }
}
