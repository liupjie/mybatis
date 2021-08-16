package org.apache.ibatis.z_run.pojo;

import java.util.Objects;

/**
 * @author MySQ
 * @date 2021/8/9 22:20
 */
public class PurchaseVO {

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
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PurchaseVO purchase = (PurchaseVO) o;
        return Objects.equals(id, purchase.id) &&
                Objects.equals(name, purchase.name) &&
                Objects.equals(price, purchase.price) &&
                Objects.equals(category, purchase.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }

    public PurchaseVO() {
    }

    public PurchaseVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
