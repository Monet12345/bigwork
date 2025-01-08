package model;

import lombok.Getter;

/**
 * @author: shihui
 * @create: 2023-05-07 10:48
 */
@Getter
public class PageForm {
  /** 本次请求的页码 */
  protected Long pageNo = 1L;

  /** 本次请求的单页数据量 */
  protected Long pageSize = 20L;

  /** 偏移量 */
  public long getOffset() {
    if (pageNo == null || pageNo < 1L) {
      pageNo = 1L;
    }

    return (pageNo - 1) * pageSize;
  }

  public void setPageNo(Long pageNo) {
    if (pageNo == null || pageNo < 1L) {
      this.pageNo = 1L;
    } else {
      this.pageNo = pageNo;
    }
  }

  public void setPageSize(Long pageSize) {
    if (pageSize == null || pageSize < 1L) {
      pageSize = 20L;
    } else {
      this.pageSize = pageSize;
    }
  }
}
