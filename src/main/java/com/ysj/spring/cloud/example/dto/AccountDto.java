package com.ysj.spring.cloud.example.dto;

import com.ysj.spring.cloud.example.domain.Account;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class AccountDto {

    private String id;
    private String username;
    private Date createTime;
    private Date lastLoginTime;

    public static AccountDto of(Account account) {
        AccountDto dto = new AccountDto();
        BeanUtils.copyProperties(account, dto);
        return dto;
    }
}
