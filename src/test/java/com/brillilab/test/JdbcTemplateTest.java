package com.brillilab.test;

import com.brillilab.entity.dao.SCategoryDao;
import com.brillilab.entity.pojo.SCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/jdbcTemplate.xml"})
public class JdbcTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    SCategoryDao sCategoryDao;

    /**
     * 测试JdbcTemplate
     */
    @Test
    public void test01(){
        Map execute = jdbcTemplate.execute(new StatementCallback<Map>() {

            @Override
            public Map<String, Object> doInStatement(Statement stmt) throws SQLException, DataAccessException {
                Map<String,Object> map = new HashMap<>();
                String sql = "select * from s_category";
                ResultSet resultSet = stmt.executeQuery(sql);
                if (resultSet.next()){
                    map.put("id",resultSet.getLong("id"));
                    map.put("name",resultSet.getString("name"));
                    map.put("parent_id",resultSet.getLong("parent_id"));
                }
                return map;
            }
        });

        System.out.println(execute);
    }

    /**
     * 测试JdbcTemplate
     */
    @Test
    public void testDateTime(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.withHour(23).withMinute(59).withSecond(59).withNano(0).atZone(ZoneId.of("GMT+8")).toLocalDateTime();
//        String sql = "insert into date_time (`create_time`) value (:create_time)";
//        SqlParameterSource namedParameters = new MapSqlParameterSource("create_time", end);
//        Integer integer = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
//        System.out.println(integer);

        // 报错  Can not issue data manipulation statements with executeQuery()
        // 无法使用 query相关的方法执行写操作
        for (int i =0; i<100; i++){
            String sql = "insert into date_time (`create_time`) value (?)";
            int update = jdbcTemplate.update(sql, end);
            System.out.println(update);
        }
    }

    /**
     * 测试MappingSqlQuery 查询所有记录
     */
    @Test
    public void test02(){
        List<SCategory> sCategoryList = sCategoryDao.getSCategoryList();
        System.out.println(sCategoryList);
    }

    /**
     * 测试MappingSqlQuery 查询单条记录
     */
    @Test
    public void test03(){
        SCategory scategory = sCategoryDao.getScategoryById(4L);
        System.out.println(scategory);
    }

    /**
     * 测试NamedParameterJdbcTemplate 类似于DBUtil
     */
    @Test
    public void test04(){
        String sql = "select * from s_category where id=:id";
        SCategory sCategory = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", 4), new BeanPropertyRowMapper<>(SCategory.class));
        System.out.println(sCategory);
    }
}
