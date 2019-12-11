package com.longge.service.impl;

import com.longge.dao.CategoryDao;
import com.longge.dao.impl.CategoryDaoImpl;
import com.longge.domain.Category;
import com.longge.service.CategoryService;
import com.longge.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author longge
 * @create 2019-12-10 下午4:35
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //从redis中查询
        Jedis jedis = JedisUtil.getJedis();
        List<Category> cs;
        if(jedis!=null){
            Set<Tuple> categorys = jedis.zrangeWithScores("category",0,-1);
            if(categorys==null || categorys.size()==0){
                //若没有查到，则从数据库中查询，并存入redis
                cs = categoryDao.findAll();
                for(int i=0;i<cs.size();i++){
                    jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
                }
            }else{
                //若查到了，则将set的数据存入list
                cs = new ArrayList<>();
                for(Tuple tuple : categorys){
                    Category category = new Category();
                    category.setCid((int)tuple.getScore());
                    category.setCname(tuple.getElement());
                    cs.add(category);
                }
            }
        }else{
            cs = categoryDao.findAll();
        }
        return cs;
    }
}
