package com.longge.service.impl;

import com.longge.dao.FavoriteDao;
import com.longge.dao.RouteDao;
import com.longge.dao.RouteImgDao;
import com.longge.dao.SellerDao;
import com.longge.dao.impl.FavoriteDaoImpl;
import com.longge.dao.impl.RouteDaoImpl;
import com.longge.dao.impl.RouteImgDaoImpl;
import com.longge.dao.impl.SellerDaoImpl;
import com.longge.domain.Favorite;
import com.longge.domain.PageBean;
import com.longge.domain.Route;
import com.longge.service.RouteService;

import java.util.List;

/**
 * @author longge
 * @create 2019-12-10 下午7:11
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pb = new PageBean<>();
        //当前页码
        pb.setCurrentPage(currentPage);
        //每页显示条数
        pb.setPageSize(pageSize);
        //总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //当前页显示的数据集合
        int start = (currentPage-1)*pageSize;
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);
        //总页数
        int totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public Route findById(int rid) {
        Route route = routeDao.findById(rid);
        if(route!=null){
            route.setSeller(sellerDao.findById(route.getSid()));
            route.setRouteImgList(routeImgDao.findByRid(route.getRid()));
            route.setCount(favoriteDao.findCountByRid(rid));
        }
        return route;
    }

}
