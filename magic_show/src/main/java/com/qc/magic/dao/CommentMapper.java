package com.qc.magic.dao;

import com.qc.magic.model.Comment;
import com.qc.magic.model.CommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface CommentMapper {
    @SelectProvider(type=CommentSqlProvider.class, method="countByExample")
    long countByExample(CommentExample example);

    @DeleteProvider(type=CommentSqlProvider.class, method="deleteByExample")
    int deleteByExample(CommentExample example);

    @Delete({
        "delete from comment",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into comment (id, content, ",
        "image_id, user_id, ",
        "status, created)",
        "values (#{id,jdbcType=INTEGER}, #{content,jdbcType=VARCHAR}, ",
        "#{imageId,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{status,jdbcType=INTEGER}, #{created,jdbcType=TIMESTAMP})"
    })
    int insert(Comment record);

    @InsertProvider(type=CommentSqlProvider.class, method="insertSelective")
    int insertSelective(Comment record);

    @SelectProvider(type=CommentSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="image_id", property="imageId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="created", property="created", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Comment> selectByExample(CommentExample example);

    @Select({
        "select",
        "id, content, image_id, user_id, status, created",
        "from comment",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="image_id", property="imageId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="created", property="created", jdbcType=JdbcType.TIMESTAMP)
    })
    Comment selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CommentSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    @UpdateProvider(type=CommentSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    @UpdateProvider(type=CommentSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Comment record);

    @Update({
        "update comment",
        "set content = #{content,jdbcType=VARCHAR},",
          "image_id = #{imageId,jdbcType=INTEGER},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "created = #{created,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Comment record);
}