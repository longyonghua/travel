package com.longge.service.impl;

import com.longge.dao.FavoriteDao;
import com.longge.dao.impl.FavoriteDaoImpl;
import com.longge.domain.Favorite;
import com.longge.service.FavoriteService;

/**
 * @author longge
 * @create 2019-12-11 下午3:49
 */
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(rid,uid);
        return favorite!=null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(rid,uid);
    }
}
