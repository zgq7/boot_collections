package com.boot.kafka.sender.model;

import javax.persistence.*;

/**
 * 功能：xx
 *
 * @param
 * @author Leethea
 * @return
 * @apiNote 1: 沒有 @Entity 会报错: Not a managed type: class com.boot.jpa.model.Aopi
 * 2: 没有 @Table 注解并不会报错,默认为类名为表名
 * 3: 没有 @Colum 注解并不会报错，默认为字段名=属性名
 * 4: 没有 @Id 会报错， No identifier specified for entity: com.boot.jpa.model.Aopi
 * @date 2020/1/17 16:07
 **/
@Entity
@Table(name = "aopi")
public class Aopi {

	public Aopi() {
	}

	@Override
	public String toString() {
		return "Aopi{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 姓名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 年龄
	 */
	@Column(name = "age")
	private Integer age;

	/**
	 * 获取id
	 *
	 * @return id - id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置id
	 *
	 * @param id id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取姓名
	 *
	 * @return name - 姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 *
	 * @param name 姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取年龄
	 *
	 * @return age - 年龄
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * 设置年龄
	 *
	 * @param age 年龄
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
}