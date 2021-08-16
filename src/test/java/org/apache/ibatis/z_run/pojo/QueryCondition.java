package org.apache.ibatis.z_run.pojo;

import java.util.List;

/**
 * @author MySQ
 * @date 2021/8/9 22:20
 */
public class QueryCondition {

    /**
     * ID
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 价格
     */
    private Integer price;
    /**
     * 分类
     */
    private Integer category;
    private List<Integer> categoryList;

    /**
     * 分页参数
     */
    //偏移量
    private Integer offset;
    //每页条数
    private Integer pageSize;
    //页数
    private Integer pageNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public List<Integer> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Integer> categoryList) {
        this.categoryList = categoryList;
    }

    // 计算分页的起始位置
    public Integer getOffset() {
        return ((pageNum == null || pageNum < 1 ? 1 : pageNum) - 1) * (pageSize == null ? 3 : pageSize);
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
