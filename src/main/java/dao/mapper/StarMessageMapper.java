package dao.mapper;

import dao.provider.MessageSqlProvider;
import dao.provider.StarMessageSqlProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import pojo.StarMessage;

import java.util.List;

public interface StarMessageMapper {
    @Insert({
        "insert into star_message (mid, stid, ",
        "sfid, time)",
        "values (#{mid,jdbcType=INTEGER}, #{stid,jdbcType=INTEGER}, ",
        "#{sfid,jdbcType=INTEGER}, #{time,jdbcType=DATE})"
    })
    int insert(StarMessage record);

    @InsertProvider(type= StarMessageSqlProvider.class, method="insertSelective")
    int insertSelective(StarMessage record);

    @SelectProvider(type = StarMessageSqlProvider.class, method = "getStaredSelective")
    List<StarMessage> getStaredSelective(StarMessage starMessage);
}