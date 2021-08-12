package org.apache.ibatis.z_run.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.z_run.pojo.*;
import org.apache.ibatis.z_run.util.PurchaseResultHandler;

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

    List<Purchase> findByPriceAndCategory(Integer price, Integer category);

    //Insert注解传入的参数是字符串数组，因此可以将SQL语句分解成字符串数组，也可以拼成一个字符串
    // @Insert("insert into purchase (id, name, price, category) values(#{id},#{name},#{price},#{category})")
    @Insert({"insert", "into purchase", " (id, name, price, category)", " values(#{id},#{name},#{price},#{category})"})
    //Options注解用于插入数据后返回数据的自增ID
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insertAnnoPojo(Purchase purchase);

    @Select("select id,name from purchase where id = #{id}")
    // @ConstructorArgs({
    //         @Arg(column = "id", javaType = Integer.class, id = true),
    //         @Arg(column = "name", javaType = String.class)
    // })
    @Results(id = "purchaseMap", value = {
            @Result(property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER, id = true),
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    Purchase findAnnoPojo(Purchase purchase);

    @Select("select id,name from purchase where id = #{id}")
    @ResultType(Purchase.class)
    void findAnnoById(PurchaseResultHandler resultHandler, @Param("id") Integer id);

    @UpdateProvider(type = SqlProvider.class, method = "provideUpdate")
    int updateAnnoById(@Param("id") Integer id, @Param("price") Integer price);

    class SqlProvider {
        public String provideUpdate(@Param("id") Integer id, @Param("price") Integer price) {
            StringBuilder sql = new StringBuilder();
            if (id != null) {
                sql.append("update purchase ");
                if (price != null) {
                    sql.append("set price = #{price}");
                }
                sql.append(" where id = #{id}");
            }
            return sql.toString();
        }
    }

    @Delete("delete from purchase where id = #{id}")
    int deleteAnnoById(Integer id);

    // @Select("select * from purchase where id = #{id}")
    // @Results(id = "purchaseVoMapper", value = {
    //         @Result(property = "category", column = "category", one = @One(select = "org.apache.ibatis.z_run.mapper.PurchaseMapper.findCategoryById"))
    // })
    // PurchaseVO findPurchaseById(Integer id);
    //
    // @Select("select * from category where id = #{id}")
    // Category findCategoryById(Integer id);


    // 这里必须要写id的映射关系，否则id值为null TODO
    @Select("select * from category where id = #{id}")
    @Results(id = "categoryVoMapper", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "purchases", column = "id", many = @Many(select = "org.apache.ibatis.z_run.mapper.PurchaseMapper.findPurchaseByCategory"))
    })
    CategoryVO findCategoryByID(Integer id);

    @Select("select * from purchase where category = #{category}")
    Purchase findPurchaseByCategory(Integer category);


    Purchase findPurchaseByCategoryId(Integer category);

    /**
     * 根据ID查询商品
     */
    Purchase findByID(Integer id);

    PurchaseVO findPurchaseById(Integer id);

    /**
     * 根据ID查询商品分类
     */
    CategoryVO findCategoryById(Integer id);
}
