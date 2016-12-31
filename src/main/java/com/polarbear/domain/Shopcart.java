package com.polarbear.domain;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;

@NamedQueries( { @NamedQuery(name = "queryUserId", query = "from Shopcart sc where sc.user = ?") })
@Entity
@Table(name = "shopcart")
public class Shopcart {
    @Id
    @GeneratedValue
    Long id;
    @OneToOne
    User user;
    @Column
    Integer productNum;
    @Column
    Integer createTime;

    public Shopcart() {
    }

    public Shopcart(Long id, User user, Integer productNum, Integer createTime) {
        super();
        this.id = id;
        this.user = user;
        this.productNum = productNum;
        this.createTime = createTime;
    }

    public Shopcart(User user, Integer createTime) {
        super();
        this.user = user;
        this.productNum = 0;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer count) {
        this.productNum = count;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

}