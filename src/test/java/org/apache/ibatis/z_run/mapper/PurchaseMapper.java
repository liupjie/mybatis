package org.apache.ibatis.z_run.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.z_run.pojo.Purchase;
import org.apache.ibatis.z_run.pojo.QueryCondition;

import java.util.List;

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

}
