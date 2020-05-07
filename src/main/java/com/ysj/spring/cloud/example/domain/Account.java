package com.ysj.spring.cloud.example.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document
public class Account implements Serializable {
    @Id
    private String id;
    @Indexed
    private String username;
    private String password;
    private Date createTime;
    private Date lastLoginTime;
}
