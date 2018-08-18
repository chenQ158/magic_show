package com.qc.magic.model;

/**
 * 用户实体类
 * @author john
 *
 */
public class User {
	/**
	 * 用户名
	 */
	/*private String username;*/

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像url
	 */
	private String headUrl;
	/**
	 * 加密salt
	 */
	private String salt;
	/**
	 * 用户是否激活
	 */
	private boolean isActived;
	/**
	 * 激活链接
	 */
	private String activeCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Boolean getIsActived() {
        return isActived;
    }

    public void setIsActived(Boolean isActived) {
        this.isActived = isActived;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode == null ? null : activeCode.trim();
    }

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!obj.getClass().equals(this.getClass())) return false;
		
		User user = (User) obj;
		if (!user.getId().equals(this.getId())) return false;
		if (!user.getIsActived().equals(this.getIsActived())) return false;
		if (!user.getActiveCode().equals(this.getActiveCode())) return false;
		if (!user.getEmail().equals(this.getEmail())) return false;
		if (!user.getHeadUrl().equals(this.getHeadUrl())) return false;
		if (!user.getNickname().equals(this.getNickname())) return false;
		if (!user.getPassword().equals(this.getPassword())) return false;
		if (!user.getSalt().equals(this.getSalt())) return false;
		
		return true;
	}
    
    
}