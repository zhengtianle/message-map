package dao.mapper;

import dao.provider.UserSqlProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import pojo.User;

public interface UserMapper {
    @Insert({
        "insert into user (uid, tel, password, ",
        "avatar, username, ",
        "sex, birthday, address, ",
        "profile, sid, institute)",
        "values (#{uid,jdbcType=INTEGER}, #{tel,jdbcType=CHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{avatar,jdbcType=VARCHAR}, #{username,jdbcType=VARCHAR}, ",
        "#{sex,jdbcType=INTEGER}, #{birthday,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{profile,jdbcType=VARCHAR}, #{sid,jdbcType=VARCHAR}, #{institute,jdbcType=VARCHAR},",
            "#{profile,jdbcType=VARCHAR}, #{sid,jdbcType=VARCHAR}, #{institute,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER})"
    })
    int insert(User record);

    @InsertProvider(type= UserSqlProvider.class, method="insertSelective")
    int insertSelective(User record);

    @Select("select * from user where tel = #{tel}")
    User getUserByTel(String tel);
}