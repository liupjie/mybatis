package org.apache.ibatis.z_run;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.domain.blog.mappers.BlogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.z_run.mapper.PurchaseMapper;
import org.apache.ibatis.z_run.pojo.CategoryVO;
import org.apache.ibatis.z_run.pojo.PageVO;
import org.apache.ibatis.z_run.pojo.Purchase;
import org.apache.ibatis.z_run.pojo.QueryCondition;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyTest extends BaseDataTest {

    SqlSessionFactory sqlSessionFactory;
    SqlSession sqlSession;

    @BeforeAll
    void setup() throws Exception {
        // createBlogDataSource();
        sqlSession = getSqlSession(getSqlSessionFactory());
    }

    @AfterAll
    void endup() {
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    @Ignore
    public void test1() throws IOException {
        final String resource = "org/apache/ibatis/builder/MapperConfig.xml";
        final Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Map> maps = mapper.selectAllPosts();
        for (Map map : maps) {
            map.forEach((k, v) -> {
                System.out.println(k + ":" + v);
            });
        }
    }

    @Test
    public void localDBTest1() {
        //配置文件
        // String resource = "resources/mybatis-config.xml";
        // InputStream inputStream = null;
        // try {
        //     inputStream = Resources.getResourceAsStream(resource);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        //创建SqlSessionFactory
        // SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //创建SqlSession
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            //获取Mapper接口的代理对象
            PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
            //设置查询参数
            QueryCondition queryCondition = new QueryCondition();
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);
            list.add(3);
            queryCondition.setCategoryList(list);
            //执行查询
            System.out.println(mapper.findByCondition(queryCondition));
        }
    }

    @Test
    public void insert() {
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        //组装参数
        Purchase purchase = new Purchase();
        purchase.setName("西瓜");
        purchase.setPrice(12);
        purchase.setCategory(3);
        mapper.insertAnnoPojo(purchase);
        System.out.println(purchase);
        // Map<String, Object> param = new HashMap<>();
        // param.put("name", "辣条");
        // param.put("price", "1");
        // param.put("category", "2");
        // mapper.insertMapPojo(param);
    }

    @Test
    public void query() {
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        //组装参数
        Purchase purchase = new Purchase();
        purchase.setId(12);
        // System.out.println(mapper.findXmlPojoByID(purchase));

        // Map<String, Object> param = new HashMap<>();
        // param.put("id", 7);

        System.out.println(mapper.findCategoryByID(1));
        // PurchaseResultHandler resultHandler = new PurchaseResultHandler();
        // mapper.findAnnoById(resultHandler, null);
        // System.out.println(resultHandler.getPurchases());
    }

    @Test
    public void update() {
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        //组装参数
        // Purchase purchase = new Purchase();
        // purchase.setId(6);
        // //将价格修改为6
        // purchase.setPrice(6);
        // mapper.updateXmlPojoByID(purchase);

        Map<String, Object> param = new HashMap<>();
        param.put("id", 7);
        param.put("price", "3");
        mapper.updateAnnoById(12, 10);
    }

    @Test
    public void delete() {
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        //组装参数
        // Purchase purchase = new Purchase();
        // purchase.setId(6);
        // mapper.deleteXmlPojoByID(purchase);
        Map<String, Object> param = new HashMap<>();
        param.put("id", 7);
        System.out.println(mapper.deleteAnnoById(12));
    }


    @Test
    public void oneLevelCache() {
        // 使用同一个SqlSession进行操作
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        System.out.println("======第一次查询======");
        System.out.println(mapper.findByID(2));

        System.out.println("======第二次查询======");
        System.out.println(mapper.findByID(2));
    }

    @Test
    public void twoLevelCache() {
        // 获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 第一个SqlSession下的查询
        SqlSession sqlSession = getSqlSession(sqlSessionFactory);
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        System.out.println("============第一个SqlSession下的第一次查询============");
        System.out.println(mapper.findByID(2));
        System.out.println("============第一个SqlSession下的第二次查询============");
        System.out.println(mapper.findByID(2));
        // 刷新缓存到SqlSessionFactory中
        sqlSession.commit();
        sqlSession.close();
        // 第二个SqlSession下的查询
        System.out.println("============第二个SqlSessionFactory下的第一次查询============");
        sqlSession = getSqlSession(sqlSessionFactory);
        mapper = sqlSession.getMapper(PurchaseMapper.class);
        System.out.println(mapper.findByID(2));
    }

    @Test
    public void lazyLoadTest() {
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        CategoryVO categoryVO = mapper.findCategoryById(1);
        System.out.println("==========使用purchases属性==========");
        categoryVO.getPurchases();
        System.out.println(categoryVO);
    }

    @Test
    @Transactional
    public void oneLevelQuestion() {
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        System.out.println("=========第一次查询并使用=======");
        Purchase purchase = mapper.findByID(2);
        System.out.println(purchase);
        //对缓存的数据进行修改
        purchase.setPrice(null);
        System.out.println("=========第二次查询并使用=======");
        Purchase purchase1 = mapper.findByID(2);
        System.out.println(purchase1);
    }

    @Test
    public void dynamicSQL() {
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        QueryCondition condition = new QueryCondition();
        condition.setId(1);
        condition.setCategory(1);
        System.out.println(mapper.findByCondition(condition));
    }

    int pageSize = 2;
    int pageNum = 2;

    @Test
    public void pageQueryByHand() {
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        QueryCondition condition = new QueryCondition();
        condition.setPageNum(pageNum);
        condition.setPageSize(pageSize);
        System.out.println(new PageVO<>(pageSize, pageNum, mapper.count(condition), mapper.findPageByHand(condition)));
    }

    @Test
    public void pageQueryByRowBounds() {
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        QueryCondition condition = new QueryCondition();
        System.out.println(new PageVO<>(pageSize, pageNum, mapper.count(condition),
                mapper.findPageByRowBounds(condition, new RowBounds((pageNum - 1) * pageSize, pageSize))));
    }

    @Test
    public void pageQueryByPageHelper() {
        PurchaseMapper mapper = sqlSession.getMapper(PurchaseMapper.class);
        QueryCondition condition = new QueryCondition();
        // 这一行代码之后需要立即接上需要分页的查询方法，否则可能导致分页失效
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Purchase> purchaseList = mapper.findByCondition(condition);
        System.out.println(new PageVO<>(pageSize, pageNum, (int) page.getTotal(), purchaseList));
    }

    @Test
    public void testSort() {
        List<String> list1 = new ArrayList<>();
        list1.add("aaaaa");
        list1.add("dddd");
        list1.add("eeeee");
        list1.add("bbbbb");
        System.out.println(list1);
        List<String> list2 = new ArrayList<>();
        list2.add("aaaaa");
        list2.add("dddd");
        list2.add("bbbbb");
        list2.add("eeeee");
        list1.sort((o1, o2) -> {
            int idx1 = list2.indexOf(o1);
            int idx2 = list2.indexOf(o2);
            return idx1 - idx2;
        });
        System.out.println(list1);
    }


    public SqlSessionFactory getSqlSessionFactory() {
        // 指定配置文件所处的位置
        String resource = "resources/mybatis-config.xml";
        // 读取配置文件为输入流
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使用输入流创建SqlSessionFactory
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public SqlSession getSqlSession(SqlSessionFactory sqlSessionFactory) {
        return sqlSessionFactory.openSession();
    }

}
