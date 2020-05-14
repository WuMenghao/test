package com.brillilab.entity.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class SCategory {

  private long id;
  private String name;
  private long parentId;
  private long sortOrder;
  private Date createTime;
  private Date updateTime;
  private String isDel;

}
