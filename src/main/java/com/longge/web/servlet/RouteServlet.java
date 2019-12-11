package com.longge.web.servlet;

import com.longge.domain.PageBean;
import com.longge.domain.Route;
import com.longge.domain.User;
import com.longge.service.FavoriteService;
import com.longge.service.RouteService;
import com.longge.service.impl.FavoriteServiceImpl;
import com.longge.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author longge
 * @create 2019-12-10 下午6:55
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    //分页查询
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        //处理参数
        int cid = 0;
        if(cidStr!=null && cidStr.length()>0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 1; //默认为第1页
        if(currentPageStr!=null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }
        int pageSize = 5; //默认每页显示5条
        if(pageSizeStr!=null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }
        //调用service查询
        PageBean<Route> pb = routeService.pageQuery(cid,currentPage,pageSize,rname);
        //将结果序列化为json，返回
        writeValue(pb,response);
    }

    //根据id查询一个旅游线路的详细信息
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = routeService.findById(Integer.parseInt(rid));
        writeValue(route,response);
    }

    //判断是否收藏过
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if(user!=null){
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(Integer.parseInt(rid),uid);
        writeValue(flag,response);
    }

    //添加收藏
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid = 0;
        if(user==null){
            return;
        }else{
            uid = user.getUid();
        }
        favoriteService.add(rid,uid);
    }

}
