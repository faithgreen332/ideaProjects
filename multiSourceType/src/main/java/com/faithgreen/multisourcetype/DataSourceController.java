package com.faithgreen.multisourcetype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DataSourceController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("remote")
    @DataSource(MultiDataSourceTypeEnum.REMOTE)
    public Object remote() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from tmp");
        return maps;
    }

    @RequestMapping("local")
    @DataSource(MultiDataSourceTypeEnum.LOCAL)
    public Object local() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from bsn_cus_user");
        return maps;
    }
}
