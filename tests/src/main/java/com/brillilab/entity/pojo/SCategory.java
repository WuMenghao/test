package com.brillilab.entity.pojo;


public class SCategory {

  private long id;
  private String name;
  private long parentId;
  private long sortOrder;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;
  private String isDel;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getParentId() {
    return parentId;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
  }


  public long getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(long sortOrder) {
    this.sortOrder = sortOrder;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public java.sql.Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }


  public String getIsDel() {
    return isDel;
  }

  public void setIsDel(String isDel) {
    this.isDel = isDel;
  }

  @Override
  public String toString() {
    return "SCategory{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", parentId=" + parentId +
            ", sortOrder=" + sortOrder +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", isDel='" + isDel + '\'' +
            '}';
  }
}
