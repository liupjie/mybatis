package org.apache.ibatis.z_run.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.z_run.pojo.Purchase;
import org.apache.ibatis.z_run.pojo.QueryCondition;

import java.util.List;
import java.util.Map;

/**
 * @author MySQ
 * @date 2021/8/9 22:20
 */
public interface PurchaseMapper {

    /**
     * 根据条件查询
     */
    List<Purchase> findByCondition(QueryCondition condition);

    /**
     * 根据ID查询
     */
    @Select("select * from purchase where id = #{id}")
    Purchase selectById(Integer id);

    /**
     * 基于POJO的新增XML方式
     */
    int insertXmlPojo(Purchase purchase);

    /**
     * 基于POJO的查询XML方式
     */
    Purchase findXmlPojoByID(Purchase purchase);

    /**
     * 基于POJO的修改XML方式
     */
    int updateXmlPojoByID(Purchase purchase);

    /**
     * 基于POJO的删除XML方式
     */
    int deleteXmlPojoByID(Purchase purchase);

    /**
     * 新增：基于Map的XML方式
     */
    int insertMapPojo(Map<String, Object> purchase);

    /**
     * 查询：基于Map的XML方式
     */
    Purchase findMapPojoByID(Map<String, Object> purchase);

    /**
     * 修改：基于Map的XML方式
     */
    int updateMapPojoByID(Map<String, Object> purchase);

    /**
     * 删除：基于Map的XML方式
     */
    int deleteMapPojoByID(Map<String, Object> purchase);

    List<Purchase> findByPriceAndCategory(@Param("price") Integer price, @Param("category") Integer category);

}
