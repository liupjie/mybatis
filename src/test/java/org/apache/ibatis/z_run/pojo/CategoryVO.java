package org.apache.ibatis.z_run.pojo;

import java.util.List;
import java.util.Objects;

/**
 * @author MySQ
 * @date 2021/8/11 22:25
 */
public class CategoryVO {
    private Integer id;
    private String name;
    private List<Purchase> purchases;

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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CategoryVO that = (CategoryVO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(purchases, that.purchases);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, purchases);
    }

    @Override
    public String toString() {
        return "CategoryVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", purchases=" + purchases +
                '}';
    }
}
