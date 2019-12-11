package com.longge.dao.impl;

import com.longge.dao.RouteImgDao;
import com.longge.domain.RouteImg;
import com.longge.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author longge
 * @create 2019-12-11 下午1:50
 */
public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "select * from tab_route_img where rid=?";
        List<RouteImg> list = template.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
        return list;
    }
}
