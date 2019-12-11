package com.longge.service;

/**
 * @author longge
 * @create 2019-12-11 下午3:49
 */
public interface FavoriteService {
    boolean isFavorite(int rid,int uid);

    void add(String rid, int uid);
}
