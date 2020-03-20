package com.boot.mongodb.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @apiNote YAPI 用戶
 * @date 2020/3/13 14:55
 **/
@Document(collection = "user")
public class YapiUser {

	public final static String MEMBER = "member";
	public final static String ADMIN = "admin";

	private Integer id;

	private Boolean study;

	private String type;

	private String username;

	private String email;

	private String password;

	private String passsalt;

	private String role;

	@Field("add_time")
	private Long addTime;

	@Field("up_time")
	private Long upTime;

	@Field("__v")
	private Integer v;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getStudy() {
		return study;
	}

	public void setStudy(Boolean study) {
		this.study = study;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasssalt() {
		return passsalt;
	}

	public void setPasssalt(String passsalt) {
		this.passsalt = passsalt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public Long getUpTime() {
		return upTime;
	}

	public void setUpTime(Long upTime) {
		this.upTime = upTime;
	}

	public Integer getV() {
		return v;
	}

	public void setV(Integer v) {
		this.v = v;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "YapiUser{" +
				"id=" + id +
				", study=" + study +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", passsalt='" + passsalt + '\'' +
				", role='" + role + '\'' +
				", addTime=" + addTime +
				", upTime=" + upTime +
				", v=" + v +
				'}';
	}
}
