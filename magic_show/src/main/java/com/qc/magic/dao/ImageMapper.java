package com.qc.magic.dao;

import com.qc.magic.model.Image;
import com.qc.magic.model.ImageExample;
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

public interface ImageMapper {
    @SelectProvider(type=ImageSqlProvider.class, method="countByExample")
    long countByExample(ImageExample example);

    @DeleteProvider(type=ImageSqlProvider.class, method="deleteByExample")
    int deleteByExample(ImageExample example);

    @Delete({
        "delete from image",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into image (id, url, ",
        "status, star_count, ",
        "like_count, comment_count, ",
        "created_date, user_id, ",
        "group_id)",
        "values (#{id,jdbcType=INTEGER}, #{url,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=INTEGER}, #{starCount,jdbcType=INTEGER}, ",
        "#{likeCount,jdbcType=INTEGER}, #{commentCount,jdbcType=INTEGER}, ",
        "#{createdDate,jdbcType=TIMESTAMP}, #{userId,jdbcType=INTEGER}, ",
        "#{groupId,jdbcType=INTEGER})"
    })
    int insert(Image record);

    @InsertProvider(type=ImageSqlProvider.class, method="insertSelective")
    int insertSelective(Image record);

    @SelectProvider(type=ImageSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="star_count", property="starCount", jdbcType=JdbcType.INTEGER),
        @Result(column="like_count", property="likeCount", jdbcType=JdbcType.INTEGER),
        @Result(column="comment_count", property="commentCount", jdbcType=JdbcType.INTEGER),
        @Result(column="created_date", property="createdDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="group_id", property="groupId", jdbcType=JdbcType.INTEGER)
    })
    List<Image> selectByExample(ImageExample example);

    @Select({
        "select",
        "id, url, status, star_count, like_count, comment_count, created_date, user_id, ",
        "group_id",
        "from image",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="star_count", property="starCount", jdbcType=JdbcType.INTEGER),
        @Result(column="like_count", property="likeCount", jdbcType=JdbcType.INTEGER),
        @Result(column="comment_count", property="commentCount", jdbcType=JdbcType.INTEGER),
        @Result(column="created_date", property="createdDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="group_id", property="groupId", jdbcType=JdbcType.INTEGER)
    })
    Image selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ImageSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Image record, @Param("example") ImageExample example);

    @UpdateProvider(type=ImageSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Image record, @Param("example") ImageExample example);

    @UpdateProvider(type=ImageSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Image record);

    @Update({
        "update image",
        "set url = #{url,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "star_count = #{starCount,jdbcType=INTEGER},",
          "like_count = #{likeCount,jdbcType=INTEGER},",
          "comment_count = #{commentCount,jdbcType=INTEGER},",
          "created_date = #{createdDate,jdbcType=TIMESTAMP},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "group_id = #{groupId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Image record);
}