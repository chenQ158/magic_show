package com.qc.magic.model;

/**
 * 图片分组
 * @author john
 *
 */
public class ImageGroup {

	/**
	 * 分组主键id
	 */
	private Integer id;
	/**
	 * 分组名称
	 */
	private String name;
	/**
	 * 分组描述
	 */
	private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}