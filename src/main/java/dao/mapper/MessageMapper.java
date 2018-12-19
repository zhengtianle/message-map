package dao.mapper;

import java.util.Date;

import dao.provider.MessageSqlProvider;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import pojo.Message;

public interface MessageMapper {
    @Delete({
        "delete from message",
        "where mid = #{mid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer mid);

    @Insert({
        "insert into message (mid, uid, ",
        "location, content, ",
        "time, stars)",
        "values (#{mid,jdbcType=INTEGER}, #{uid,jdbcType=INTEGER}, ",
        "#{location,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR}, ",
        "#{time,jdbcType=DATE}, #{stars,jdbcType=INTEGER})"
    })
    int insert(Message record);

    @InsertProvider(type= MessageSqlProvider.class, method="insertSelective")
    int insertSelective(Message record);

    @Select({
        "select",
        "mid, uid, location, content, time, stars",
        "from message",
        "where mid = #{mid,jdbcType=INTEGER}"
    })
    @ConstructorArgs({
        @Arg(column="mid", javaType=Integer.class, jdbcType=JdbcType.INTEGER, id=true),
        @Arg(column="uid", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="location", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="content", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="time", javaType=Date.class, jdbcType=JdbcType.DATE),
        @Arg(column="stars", javaType=Integer.class, jdbcType=JdbcType.INTEGER)
    })
    Message selectByPrimaryKey(Integer mid);

    @UpdateProvider(type=MessageSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Message record);

    @Update({
        "update message",
        "set uid = #{uid,jdbcType=INTEGER},",
          "location = #{location,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=VARCHAR},",
          "time = #{time,jdbcType=DATE},",
          "stars = #{stars,jdbcType=INTEGER}",
        "where mid = #{mid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Message record);
}