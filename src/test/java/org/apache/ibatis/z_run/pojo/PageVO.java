package org.apache.ibatis.z_run.pojo;

import java.util.List;

/**
 * @author MySQ
 * @date 2021/8/14 22:16
 */
public class PageVO<T> {

    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 页码
     */
    private int pageNum;

    /**
     * 总页数
     */
    private int pages;
    /**
     * 总条数
     */
    private int total;

    /**
     * 当前页的数据
     */
    private List<T> data;


    public PageVO(int pageSize, int pageNum, int total, List<T> data) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.total = total;
        this.data = data;
        this.pages = total / pageSize + (total % pageSize == 0 ? 0 : 1);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageVO{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", pages=" + pages +
                ", total=" + total +
                ", data=" + data +
                '}';
    }
}
