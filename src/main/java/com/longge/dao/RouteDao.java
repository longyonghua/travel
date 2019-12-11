package com.longge.dao;

import com.longge.domain.Route;

import java.util.List;

/**
 * @author longge
 * @create 2019-12-10 下午7:13
 */
public interface RouteDao {
    //根据cid查询总记录数
    int findTotalCount(int cid,String rname);
    //根据cid,start,pageSize查询当前页的数据集合
    List<Route> findByPage(int cid,int start,int pageSize,String rname);
    //根据id查找
    Route findById(int rid);
}
