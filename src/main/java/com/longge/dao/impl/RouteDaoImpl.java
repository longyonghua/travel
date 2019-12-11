package com.longge.dao.impl;

import com.longge.dao.RouteDao;
import com.longge.domain.Route;
import com.longge.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * @author longge
 * @create 2019-12-10 下午7:13
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid,String rname) {
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid!=0){
            sb.append(" and cid=? ");
            params.add(cid);
        }
        if(rname!=null && rname.length()>0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();

        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        String sql = "select * from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid!=0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname!=null && rname.length()>0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append("limit ?,?");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();

        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findById(int rid) {
        Route route = null;
        try {
            String sql = "select * from tab_route where rid=?";
            route = template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
        } catch (DataAccessException e) {
        }
        return route;
    }
}
