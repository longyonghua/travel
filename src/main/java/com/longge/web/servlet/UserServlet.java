package com.longge.web.servlet;

import com.longge.domain.ResultInfo;
import com.longge.domain.User;
import com.longge.service.UserService;
import com.longge.service.impl.UserServiceImpl;
import com.longge.util.CheckCodeUtils;
import com.longge.util.Md5Util;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author longge
 * @create 2019-12-10 下午1:22
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet{
    private UserService service = new UserServiceImpl();

    //获取验证码
    public void checkCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CheckCodeUtils.returnCheckCode(req,resp);
    }

    //注册
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取验证码并校验
        String check = req.getParameter("check");
        HttpSession session = req.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER"); //保证验证码只能使用一次
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(info,resp);
            return;
        }
        //获取数据
        Map<String,String[]> map = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map); //封装对象
            user.setPassword(Md5Util.encodeByMd5(user.getPassword())); //密码明文转密文
        } catch (Exception e) {
            e.printStackTrace();
        }
        //调用service处理
        boolean flag = service.regist(user);
        //响应结果
        ResultInfo info = new ResultInfo();
        if(flag){
            info.setFlag(true);
        }else{
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }
        writeValue(info,resp);
    }

    //激活
    public void active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if(code!=null){
            boolean flag = service.active(code);
            String msg;
            if(flag){
                msg = "激活成功，请<a href='../login.html'>登录</a>";
            }else{
                msg = "激活失败，请联系管理员";
            }
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(msg);
        }
    }

    //登录
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取验证码，并校验
        String check = req.getParameter("check");
        HttpSession session = req.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER"); //保证验证码只能使用一次
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(info,resp);
            return;
        }
        //获取用户名和密码数据
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try{
            password = Md5Util.encodeByMd5(password);
        }catch(Exception e){
            e.printStackTrace();
        }
        //调用service查询
        User u = service.login(username,password);

        ResultInfo info = new ResultInfo();
        if (u == null) { //判断用户对象是否为null
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        } else if (!"Y".equals(u.getStatus())) { //判断用户是否激活
            info.setFlag(false);
            info.setErrorMsg("尚未激活，请登录邮箱激活");
        } else {
            session.setAttribute("user",u); //登录成功标记
            info.setFlag(true);
        }
        //响应数据
        writeValue(info,resp);
    }

    //查找登录用户
    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session中获取登录用户
        Object user = req.getSession().getAttribute("user");
        writeValue(user,resp);
    }

    //退出
    public void exit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //销毁session
        req.getSession().invalidate();
        //重定向到登录页面
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }
}
