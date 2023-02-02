package com.js.provider.mapper;

import com.js.api.model.BookRob;
import com.js.api.model.BookRobExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


@Mapper
public interface BookRobMapper {
    @SelectProvider(type=BookRobSqlProvider.class, method="countByExample")
    long countByExample(BookRobExample example);

    @DeleteProvider(type=BookRobSqlProvider.class, method="deleteByExample")
    int deleteByExample(BookRobExample example);

    @Delete({
        "delete from book_rob",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into book_rob (id, userid, ",
        "bookno, rob_time)",
        "values (#{id,jdbcType=INTEGER}, #{userid,jdbcType=INTEGER}, ",
        "#{bookno,jdbcType=VARCHAR}, #{robTime,jdbcType=DATE})"
    })
    int insert(BookRob record);

    @InsertProvider(type=BookRobSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insertSelective(BookRob record);

    @SelectProvider(type=BookRobSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="userid", property="userid", jdbcType=JdbcType.INTEGER),
        @Result(column="bookno", property="bookno", jdbcType=JdbcType.VARCHAR),
        @Result(column="rob_time", property="robTime", jdbcType=JdbcType.DATE)
    })
    List<BookRob> selectByExample(BookRobExample example);

    @Select({
        "select",
        "id, userid, bookno, rob_time",
        "from book_rob",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="userid", property="userid", jdbcType=JdbcType.INTEGER),
        @Result(column="bookno", property="bookno", jdbcType=JdbcType.VARCHAR),
        @Result(column="rob_time", property="robTime", jdbcType=JdbcType.DATE)
    })
    BookRob selectByPrimaryKey(Integer id);


    @Select({
            "select",
            "count(id)",
            "from book_rob",
            "where bookno = #{bookNo,jdbcType=VARCHAR}",
            "and userid = #{userId,jdbcType=INTEGER}"
    })
    int countBookRob(Integer userId, String bookNo);




    @UpdateProvider(type=BookRobSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") BookRob record, @Param("example") BookRobExample example);

    @UpdateProvider(type=BookRobSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") BookRob record, @Param("example") BookRobExample example);

    @UpdateProvider(type=BookRobSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BookRob record);

    @Update({
        "update book_rob",
        "set userid = #{userid,jdbcType=INTEGER},",
          "bookno = #{bookno,jdbcType=VARCHAR},",
          "rob_time = #{robTime,jdbcType=DATE}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(BookRob record);
}