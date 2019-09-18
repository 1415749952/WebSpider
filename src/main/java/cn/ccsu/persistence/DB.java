package cn.ccsu.persistence;

import cn.ccsu.pojos.entity.NewsItemEntity;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: TheFei
 * @Date: 2019-09-18
 * @Time: 21:11
 */
public class DB
{
    public static void inserNewsItemEntitytoDB(NewsItemEntity itemEntity)
    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        NewsItemEntityDao newsItemEntityDao = sqlSession.getMapper(NewsItemEntityDao.class);
        newsItemEntityDao.insertNewsItemEntity(itemEntity);
    }
}
