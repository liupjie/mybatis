package org.apache.ibatis.z_run;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.domain.blog.mappers.BlogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.z_run.mapper.CommonMapper;
import org.apache.ibatis.z_run.mapper.PurchaseMapper;
import org.apache.ibatis.z_run.pojo.QueryCondition;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyTest extends BaseDataTest {

    @BeforeAll
    static void setup() throws Exception {
        // createBlogDataSource();
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
        String resource = "resources/mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
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

}
