package dao.mapper;

import dao.provider.UserSqlProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import pojo.User;

public interface UserMapper {

    @InsertProvider(type= UserSqlProvider.class, method="insertSelective")
    int insertSelective(User record);

    @Select("select * from user where tel = #{tel}")
    User getUserByTel(String tel);

    @Select("select * from user where uid = #{uid}")
    User getUserByUid(Integer uid);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateSelective")
    int updateSelective(User record);
}