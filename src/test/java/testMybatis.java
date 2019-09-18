import cn.ccsu.persistence.NewsItemEntityDao;
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
 * @Time: 20:27
 */
public class testMybatis
{
    public static void main(String[] args)
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


        newsItemEntityDao.insertNewsItemEntity(new NewsItemEntity("重大新闻","www.baidu.com","2019-08-10 12:54:21"));
    }
}
