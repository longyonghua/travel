package com.longge.dao.impl;

import com.longge.dao.SellerDao;
import com.longge.domain.Seller;
import com.longge.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author longge
 * @create 2019-12-11 下午1:51
 */
public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findById(int sid) {
        Seller seller = null;
        try {
            String sql = "select * from tab_seller where sid=?";
            seller = template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
        } catch (DataAccessException e) {
        }
        return seller;
    }
}
