package org.apache.ibatis;

import org.apache.ibatis.domain.blog.mappers.BlogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

public class MyTest extends BaseDataTest {

  @BeforeAll
  static void setup() throws Exception {
    createBlogDataSource();
  }

  @Test
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

}
