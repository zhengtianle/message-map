package dao.provider;

import org.apache.ibatis.jdbc.SQL;
import pojo.Message;

public class  MessageSqlProvider {

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
            sql.VALUES("time", "#{time,jdbcType=VARCHAR}");
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
            sql.SET("time = #{time,jdbcType=VARCHAR}");
        }
        
        if (record.getStars() != null) {
            sql.SET("stars = stars+1");//注意这里只能加1，不能直接赋值
        }
        
        sql.WHERE("mid = #{mid,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    public String selectSelective(Message record){
        SQL sql = new SQL();
        sql.SELECT("mid","message.uid uid","location","content","time","stars","username");
        sql.FROM("user","message");
        sql.WHERE("message.uid = user.uid");
        if(record.getUid() != null){
            sql.WHERE("message.uid = #{uid,jdbcType=INTEGER}");
        }
        if(record.getMid() != null){
            sql.WHERE("mid = #{mid,jdbcType=INTEGER}");
        }
        if(record.getLocation() != null){
            sql.WHERE("location = #{location,jdbcType=VARCHAR}");
        }

        return sql.toString();

    }
}