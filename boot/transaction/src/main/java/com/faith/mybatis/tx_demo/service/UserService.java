package com.faith.mybatis.tx_demo.service;

import com.faith.mybatis.tx_demo.util.TransactionUtil;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    public void saveUser() {
        TransactionUtil.execute("insert into dome_user(user_id,user_name) values (?,?)", 110, "faithgreen");
//        throw new RuntimeException();
    }
}
