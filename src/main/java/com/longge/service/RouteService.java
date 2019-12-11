package com.longge.service;

import com.longge.domain.PageBean;
import com.longge.domain.Route;

/**
 * @author longge
 * @create 2019-12-10 下午7:11
 */
public interface RouteService {
    PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);

    Route findById(int rid);
}
