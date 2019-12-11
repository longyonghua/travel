package com.longge.dao;

import com.longge.domain.Seller;

/**
 * @author longge
 * @create 2019-12-11 下午1:50
 */
public interface SellerDao {
    Seller findById(int sid);
}
