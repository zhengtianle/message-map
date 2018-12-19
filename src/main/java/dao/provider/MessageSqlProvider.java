package dao.provider;

import org.apache.ibatis.jdbc.SQL;
import pojo.Message;

public class MessageSqlProvider {

    public String insertSelective(Message record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("message");
        
        if (record.getMid() != null) {
            sql.VALUES("mid", "#{mid,jdbcType=INTEGER}");
        }
        
        if (record.getUid() != null) {
            sql.VALUES("uid", "#{uid,jdbcType=INTEGER}");
        }
        
        if (record.getLocation() != null) {
            sql.VALUES("location", "#{location,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            sql.VALUES("content", "#{content,jdbcType=VARCHAR}");
        }
        
        if (record.getTime() != null) {
            sql.VALUES("time", "#{time,jdbcType=DATE}");
        }
        
        if (record.getStars() != null) {
            sql.VALUES("stars", "#{stars,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Message record) {
        SQL sql = new SQL();
        sql.UPDATE("message");
        
        if (record.getUid() != null) {
            sql.SET("uid = #{uid,jdbcType=INTEGER}");
        }
        
        if (record.getLocation() != null) {
            sql.SET("location = #{location,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            sql.SET("content = #{content,jdbcType=VARCHAR}");
        }
        
        if (record.getTime() != null) {
            sql.SET("time = #{time,jdbcType=DATE}");
        }
        
        if (record.getStars() != null) {
            sql.SET("stars = #{stars,jdbcType=INTEGER}");
        }
        
        sql.WHERE("mid = #{mid,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}