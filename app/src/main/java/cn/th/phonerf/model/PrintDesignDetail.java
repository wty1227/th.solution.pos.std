package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * print_design_detail
 * @author 
 */
public class PrintDesignDetail extends BaseEntity implements Serializable {
    /**
     * id
     */
    private Integer flowId;

    /**
     * master_id
     */
    private Integer printId;

    /**
     * 标题类型
     */
    private Integer titleId;

    private String titleName;

    /**
     * 是否打印标题内容
     */
    private String printTitle;

    /**
     * 坐标
     */
    private Integer x;

    /**
     * 坐标
     */
    private Integer y;

    /**
     * 字体
     */
    private Integer font;

    private Integer size;

    /**
     * x倍宽
     */
    private Integer xmulip;

    /**
     * y倍宽
     */
    private Integer ymulip;

    /**
     * 旋转角度
     */
    private String rotation;

    /**
     * 加粗
     */
    private String boldFlag;

    /**
     * 是否打印
     */
    private String displayFlag;

    private static final long serialVersionUID = 1L;

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getPrintId() {
        return printId;
    }

    public void setPrintId(Integer printId) {
        this.printId = printId;
    }

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getPrintTitle() {
        return printTitle;
    }

    public void setPrintTitle(String printTitle) {
        this.printTitle = printTitle;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getFont() {
        return font;
    }

    public void setFont(Integer font) {
        this.font = font;
    }

    public Integer getXmulip() {
        return xmulip;
    }

    public void setXmulip(Integer xmulip) {
        this.xmulip = xmulip;
    }

    public Integer getYmulip() {
        return ymulip;
    }

    public void setYmulip(Integer ymulip) {
        this.ymulip = ymulip;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getBoldFlag() {
        return boldFlag;
    }

    public void setBoldFlag(String boldFlag) {
        this.boldFlag = boldFlag;
    }

    public String getDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(String displayFlag) {
        this.displayFlag = displayFlag;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PrintDesignDetail other = (PrintDesignDetail) that;
        return (this.getFlowId() == null ? other.getFlowId() == null : this.getFlowId().equals(other.getFlowId()))
            && (this.getPrintId() == null ? other.getPrintId() == null : this.getPrintId().equals(other.getPrintId()))
            && (this.getTitleId() == null ? other.getTitleId() == null : this.getTitleId().equals(other.getTitleId()))
            && (this.getTitleName() == null ? other.getTitleName() == null : this.getTitleName().equals(other.getTitleName()))
            && (this.getPrintTitle() == null ? other.getPrintTitle() == null : this.getPrintTitle().equals(other.getPrintTitle()))
            && (this.getX() == null ? other.getX() == null : this.getX().equals(other.getX()))
            && (this.getY() == null ? other.getY() == null : this.getY().equals(other.getY()))
            && (this.getFont() == null ? other.getFont() == null : this.getFont().equals(other.getFont()))
            && (this.getXmulip() == null ? other.getXmulip() == null : this.getXmulip().equals(other.getXmulip()))
            && (this.getYmulip() == null ? other.getYmulip() == null : this.getYmulip().equals(other.getYmulip()))
            && (this.getRotation() == null ? other.getRotation() == null : this.getRotation().equals(other.getRotation()))
            && (this.getBoldFlag() == null ? other.getBoldFlag() == null : this.getBoldFlag().equals(other.getBoldFlag()))
            && (this.getDisplayFlag() == null ? other.getDisplayFlag() == null : this.getDisplayFlag().equals(other.getDisplayFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFlowId() == null) ? 0 : getFlowId().hashCode());
        result = prime * result + ((getPrintId() == null) ? 0 : getPrintId().hashCode());
        result = prime * result + ((getTitleId() == null) ? 0 : getTitleId().hashCode());
        result = prime * result + ((getTitleName() == null) ? 0 : getTitleName().hashCode());
        result = prime * result + ((getPrintTitle() == null) ? 0 : getPrintTitle().hashCode());
        result = prime * result + ((getX() == null) ? 0 : getX().hashCode());
        result = prime * result + ((getY() == null) ? 0 : getY().hashCode());
        result = prime * result + ((getFont() == null) ? 0 : getFont().hashCode());
        result = prime * result + ((getXmulip() == null) ? 0 : getXmulip().hashCode());
        result = prime * result + ((getYmulip() == null) ? 0 : getYmulip().hashCode());
        result = prime * result + ((getRotation() == null) ? 0 : getRotation().hashCode());
        result = prime * result + ((getBoldFlag() == null) ? 0 : getBoldFlag().hashCode());
        result = prime * result + ((getDisplayFlag() == null) ? 0 : getDisplayFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", flowId=").append(flowId);
        sb.append(", printId=").append(printId);
        sb.append(", titleId=").append(titleId);
        sb.append(", titleName=").append(titleName);
        sb.append(", printTitle=").append(printTitle);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", font=").append(font);
        sb.append(", xmulip=").append(xmulip);
        sb.append(", ymulip=").append(ymulip);
        sb.append(", rotation=").append(rotation);
        sb.append(", boldFlag=").append(boldFlag);
        sb.append(", displayFlag=").append(displayFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}