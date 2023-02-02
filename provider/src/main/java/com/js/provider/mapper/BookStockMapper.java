package com.js.provider.mapper;

import com.js.api.model.BookStock;
import com.js.api.model.BookStockExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


@Mapper
public interface BookStockMapper {
    @SelectProvider(type=BookStockSqlProvider.class, method="countByExample")
    long countByExample(BookStockExample example);

    @DeleteProvider(type=BookStockSqlProvider.class, method="deleteByExample")
    int deleteByExample(BookStockExample example);

    @Delete({
        "delete from book_stock",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into book_stock (id, bookno, ",
        "stock, is_active)",
        "values (#{id,jdbcType=INTEGER}, #{bookno,jdbcType=VARCHAR}, ",
        "#{stock,jdbcType=INTEGER}, #{isActive,jdbcType=TINYINT})"
    })
    int insert(BookStock record);

    @InsertProvider(type=BookStockSqlProvider.class, method="insertSelective")
    int insertSelective(BookStock record);

    @SelectProvider(type=BookStockSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="bookno", property="bookno", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock", property="stock", jdbcType=JdbcType.INTEGER),
        @Result(column="is_active", property="isActive", jdbcType=JdbcType.TINYINT)
    })
    List<BookStock> selectByExample(BookStockExample example);

    @Select({
        "select",
        "id, bookno, stock, is_active",
        "from book_stock",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="bookno", property="bookno", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock", property="stock", jdbcType=JdbcType.INTEGER),
        @Result(column="is_active", property="isActive", jdbcType=JdbcType.TINYINT)
    })
    BookStock selectByPrimaryKey(Integer id);


    @Select({
            "select",
            "stock",
            "from book_stock",
            "where bookno = #{bookNo,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="stock", property="stock", jdbcType=JdbcType.INTEGER)
    })
    Integer countStock(String bookNo);

    @UpdateProvider(type=BookStockSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") BookStock record, @Param("example") BookStockExample example);

    @UpdateProvider(type=BookStockSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") BookStock record, @Param("example") BookStockExample example);

    @UpdateProvider(type=BookStockSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BookStock record);

    @Update({
        "update book_stock",
        "set bookno = #{bookno,jdbcType=VARCHAR},",
          "stock = #{stock,jdbcType=INTEGER},",
          "is_active = #{isActive,jdbcType=TINYINT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(BookStock record);

    @Update({
            "update book_stock",
            "set stock =stock-1 ",
            "where bookno = #{bookNo,jdbcType=VARCHAR}",
            "and stock>0"
    })
    int updateStock(String bookNo);
}