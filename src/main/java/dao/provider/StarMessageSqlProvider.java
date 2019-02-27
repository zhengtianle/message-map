package dao.provider;

import org.apache.ibatis.jdbc.SQL;
import pojo.StarMessage;

public class StarMessageSqlProvider {

    public String insertSelective(StarMessage record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("star_message");
        
        if (record.getMid() != null) {
            sql.VALUES("mid", "#{mid,jdbcType=INTEGER}");
        }
        
        if (record.getStid() != null) {
            sql.VALUES("stid", "#{stid,jdbcType=INTEGER}");
        }
        
        if (record.getSfid() != null) {
            sql.VALUES("sfid", "#{sfid,jdbcType=INTEGER}");
        }
        
        if (record.getTime() != null) {
            sql.VALUES("time", "#{time,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String getStaredSelective(StarMessage record) {
        SQL sql = new SQL();
        sql.SELECT("mid", "sfid", "stid", "time");
        sql.FROM("star_message");
        if(record.getMid() != null) {
            sql.WHERE("mid = #{mid,jdbcType=INTEGER}");
        }
        if(record.getSfid() != null) {
            sql.WHERE("sfid = #{sfid,jdbcType=INTEGER}");
        }
        if(record.getStid() != null) {
            sql.WHERE("stid = #{stid,jdbcType=INTEGER}");
        }

        return sql.toString();
    }
}