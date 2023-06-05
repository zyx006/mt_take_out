package cn.czyx007.mt.interceptor;

import cn.czyx007.mt.utils.BaseContext;
import cn.czyx007.mt.utils.R;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : 张宇轩
 * @createTime : 2023/5/29 - 15:23
 */
//自定义拦截器
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    //请求到达控制器之前拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取当前线程id
        long id = Thread.currentThread().getId();
        log.info("LoginInterceptor >> 线程Id: {}", id);
        //1.通过请求获取session中的'员工'信息
        Object empObj = request.getSession().getAttribute("employee");
        //判断是否为空
        if (empObj != null) {
            log.info("当前员工用户已经登录，员工用户id为：{}", request.getSession().getAttribute("employee"));
            Long empId = (Long) empObj;
            //吧userId放入ThreadLocal中
            BaseContext.setCurrentId(empId);
            return true;
        }

        //2.通过请求获取session中的'用户'信息
        Object userObj = request.getSession().getAttribute("user");
        //判断是否为空
        if (userObj != null) {
            log.info("当前用户已经登录，用户id为：{}", request.getSession().getAttribute("user"));
            Long userId = (Long) userObj;
            //吧userId放入ThreadLocal中
            BaseContext.setCurrentId(userId);
            return true;
        }
        //响应数据
        response.getWriter().write(JSONObject.toJSONString(R.error("NOTLOGIN")));
        return false;
    }
}
