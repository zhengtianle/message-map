package dao.mapper;

import dao.provider.StarMessageSqlProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import pojo.StarMessage;

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
}