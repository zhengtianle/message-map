package dao.provider;

import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.User;

public class UserSqlProvider {

    private static final Logger LOG = LoggerFactory.getLogger(UserSqlProvider.class);

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

        if(record.getTime() != null) {
            sql.VALUES("time","#{time,jdbcType=VARCHAR}");
        }

        if(record.getReadtime() != null) {
            sql.VALUES("readtime","#{readtime,jdbcType=VARCHAR}");
        }

        LOG.info(sql.toString());
        return sql.toString();
    }

    public String updateSelective(User record){

        SQL sql = new SQL();
        sql.UPDATE("user");

        if(record.getTel() != null){
            sql.SET("tel = #{tel}");
        }

        if(record.getPassword() != null){
            sql.SET("password = #{password}");
        }

        if (record.getAvatar() != null) {
            sql.SET("avatar = #{avatar}");
        }

        if (record.getUsername() != null) {
            sql.SET("username = #{username}");
        }

        if (record.getSex() != null) {
            sql.SET("sex = #{sex}");
        }

        if (record.getBirthday() != null) {
            sql.SET("birthday = #{birthday}");
        }

        if (record.getAddress() != null) {
            sql.SET("address = #{address}");
        }

        if (record.getProfile() != null) {
            sql.SET("profile = #{profile}");
        }

        if (record.getSid() != null) {
            sql.SET("sid = #{sid}");
        }

        if (record.getInstitute() != null) {
            sql.SET("institute = #{institute}");
        }

        if (record.getStatus() != null) {
            sql.SET("status = #{status}");
        }

        if(record.getReadtime() != null) {
            sql.SET("readtime = #{readtime}");
        }

        sql.WHERE("uid = #{uid}");
        LOG.info(sql.toString());
        return sql.toString();
    }
}