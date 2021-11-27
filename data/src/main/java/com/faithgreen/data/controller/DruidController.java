package com.faithgreen.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DruidController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("insert")
    public String insert() {
        String sql = "insert into bsn_cus_user(user_id,password,user_name,gender)values('aaaa','aaaa','aaaa',1)";
        jdbcTemplate.update(sql);
        return "insert success ....";
    }

    @RequestMapping("update")
    public String update() {
        String sql = "update bsn_cus_user set name = ? wher user_id = 'aaaa'";
        String name = "faith";
        jdbcTemplate.update(sql, name);
        return "update success ....";
    }

    @RequestMapping("select/{id}")
    public Object select(@PathVariable("id") String id) {
        String sql = "select * from bsn_cus_user where user_id = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, id);
        return maps;
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable("id") String id) {
        String sql = "delete from bsn_cus_user where user_id = ?";
        jdbcTemplate.update(sql, id);
        return "delete success ....";
    }
}
