package model;

import model.PageForm;

import java.util.List;

/** 分页查询结果 */
public class Page<T> extends PageForm {
  /** 请求数据的非分页总量 */
  private Long total;

  /** 本次请求的数据 */
  private List<T> list;

  public Page() {}

  public Page(Long pageNo, Long pageSize, Long total) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
    this.total = total;
  }

  public Page(Long pageNo, Long pageSize, Long total, List<T> list) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
    this.total = total;
    this.list = list;
  }

  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }
}
