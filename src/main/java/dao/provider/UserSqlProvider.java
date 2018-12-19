package dao.provider;

import org.apache.ibatis.jdbc.SQL;
import pojo.User;

public class UserSqlProvider {

    public String insertSelective(User record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user");
        
        if (record.getUid() != null) {
            sql.VALUES("uid", "#{uid,jdbcType=INTEGER}");
        }
        
        if (record.getTel() != null) {
            sql.VALUES("tel", "#{tel,jdbcType=CHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getAvatar() != null) {
            sql.VALUES("avatar", "#{avatar,jdbcType=VARCHAR}");
        }
        
        if (record.getUsername() != null) {
            sql.VALUES("username", "#{username,jdbcType=VARCHAR}");
        }
        
        if (record.getSex() != null) {
            sql.VALUES("sex", "#{sex,jdbcType=INTEGER}");
        }
        
        if (record.getBirthday() != null) {
            sql.VALUES("birthday", "#{birthday,jdbcType=VARCHAR}");
        }
        
        if (record.getAddress() != null) {
            sql.VALUES("address", "#{address,jdbcType=VARCHAR}");
        }
        
        if (record.getProfile() != null) {
            sql.VALUES("profile", "#{profile,jdbcType=VARCHAR}");
        }
        
        if (record.getSid() != null) {
            sql.VALUES("sid", "#{sid,jdbcType=VARCHAR}");
        }
        
        if (record.getInstitute() != null) {
            sql.VALUES("institute", "#{institute,jdbcType=VARCHAR}");
        }

        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }
}