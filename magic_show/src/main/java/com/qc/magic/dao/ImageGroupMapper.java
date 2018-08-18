package com.qc.magic.dao;

import com.qc.magic.model.ImageGroup;
import com.qc.magic.model.ImageGroupExample;
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

public interface ImageGroupMapper {
    @SelectProvider(type=ImageGroupSqlProvider.class, method="countByExample")
    long countByExample(ImageGroupExample example);

    @DeleteProvider(type=ImageGroupSqlProvider.class, method="deleteByExample")
    int deleteByExample(ImageGroupExample example);

    @Delete({
        "delete from image_group",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into image_group (id, name, ",
        "description)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR})"
    })
    int insert(ImageGroup record);

    @InsertProvider(type=ImageGroupSqlProvider.class, method="insertSelective")
    int insertSelective(ImageGroup record);

    @SelectProvider(type=ImageGroupSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR)
    })
    List<ImageGroup> selectByExample(ImageGroupExample example);

    @Select({
        "select",
        "id, name, description",
        "from image_group",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR)
    })
    ImageGroup selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ImageGroupSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ImageGroup record, @Param("example") ImageGroupExample example);

    @UpdateProvider(type=ImageGroupSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ImageGroup record, @Param("example") ImageGroupExample example);

    @UpdateProvider(type=ImageGroupSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ImageGroup record);

    @Update({
        "update image_group",
        "set name = #{name,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ImageGroup record);
}