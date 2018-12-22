package dao.mapper;

import java.util.Date;
import java.util.List;

import dao.provider.MessageSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import pojo.Message;
import pojo.UserAndMessage;

public interface MessageMapper {
    @Delete({
        "delete from message",
        "where mid = #{mid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer mid);


    @InsertProvider(type= MessageSqlProvider.class, method="insertSelective")
    int insertSelective(Message record);


    @UpdateProvider(type=MessageSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Message record);

    @SelectProvider(type = MessageSqlProvider.class, method = "selectSelective")
    List<UserAndMessage> selectSelective(Message record);

}