package com.longge.dao;

import com.longge.domain.Favorite;

/**
 * @author longge
 * @create 2019-12-11 下午3:41
 */
public interface FavoriteDao {
    Favorite findByRidAndUid(int rid,int uid);

    int findCountByRid(int rid);

    void add(String rid, int uid);
}
