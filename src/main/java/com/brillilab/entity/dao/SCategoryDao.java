package com.brillilab.entity.dao;

import com.brillilab.entity.pojo.SCategory;
import jdk.internal.org.objectweb.asm.Type;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *  使用MappingSqlQuery编写的DAO层
 */
@Repository
public class SCategoryDao {

    @Autowired
    BasicDataSource dataSource;

    /**
     * 查找所有Category记录
     * @return
     */
    public List<SCategory> getSCategoryList(){
        ScategoryMappingQuery scategoryMappingQuery = getScategoryMappingQuery("select * from s_category");
        List<SCategory> sCategories = scategoryMappingQuery.execute();
        return sCategories;
    }

    /**
     * 查询单条Category记录
     * @param id
     * @return
     */
    public SCategory getScategoryById(Long id){
        ScategoryMappingQuery scategoryMappingQuery = getScategoryMappingQuery("select * from s_category where id=?");
        scategoryMappingQuery.declareParameter(new SqlParameter("id", Type.LONG));
        List<SCategory> sCategories = scategoryMappingQuery.execute(id);
        if (sCategories.isEmpty()){
            return null;
        }
        return sCategories.get(0);
    }

    /**
     * Scategory 的 MappingSqlQuery类 该类用于sql执行与结果映射处理
     */
    private class  ScategoryMappingQuery extends MappingSqlQuery<SCategory>{

        public ScategoryMappingQuery(DataSource ds, String sql) {
            super(ds, sql);
        }

        @Override
        protected SCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            SCategory sCategory=new SCategory();
            sCategory.setId(rs.getLong("id"));
            sCategory.setName(rs.getString("name"));
            sCategory.setParentId(rs.getLong("parent_id"));
            sCategory.setCreateTime(rs.getTimestamp("create_time"));
            sCategory.setUpdateTime(rs.getTimestamp("update_time"));
            sCategory.setIsDel(rs.getString("is_del"));
            return sCategory;
        }
    }

    /**
     * 获取ScategoryMappingQuery实例
     * @param sql
     * @return
     */
    private ScategoryMappingQuery getScategoryMappingQuery(String sql){
        return new ScategoryMappingQuery(dataSource,sql);
    }
}
