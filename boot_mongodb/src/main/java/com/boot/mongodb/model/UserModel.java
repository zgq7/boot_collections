package com.boot.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/2/19 15:22
 **/
@Document(collection = "test")
public class UserModel {

	@Id
	private Object id;

	private String name;

	private Integer age;

	private String from;

	public UserModel() {
	}

	public UserModel(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public UserModel(Object id, String name, String from) {
		this.id = id;
		this.name = name;
		this.from = from;
	}

	public UserModel(Integer id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public UserModel(Object id, String name, Integer age, String from) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.from = from;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "UserModel{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				", from='" + from + '\'' +
				'}';
	}
}
