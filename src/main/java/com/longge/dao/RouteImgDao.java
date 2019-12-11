package com.longge.dao;

import com.longge.domain.RouteImg;

import java.util.List;

/**
 * @author longge
 * @create 2019-12-11 下午1:50
 */
public interface RouteImgDao {
    List<RouteImg> findByRid(int rid);
}
