package cn.czyx007.mt.controller;

import cn.czyx007.mt.bean.User;
import cn.czyx007.mt.service.UserService;
import cn.czyx007.mt.utils.R;
import cn.czyx007.mt.utils.SendEmailUtils;
import cn.czyx007.mt.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户信息(User)表控制层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:09:40
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    //发送验证码
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //生成6位随机数字验证码
        String code = ValidateCodeUtils.generateValidateCode(6).toString();
        log.info("验证码：{}", code);
        //发送短信，让用户接受验证码
        try {
            SendEmailUtils.sendAuthCodeEmail(user.getPhone(), code);
            //把验证码保存到session
            session.setAttribute("code", code);
            return R.success("验证码发送成功");
        } catch (EmailException e) {
            e.printStackTrace();
            return R.error("验证码发送失败");
        }
    }

    //用户登录
    @PostMapping("/login")
    public R<String> login(HttpSession session, @RequestBody Map<String, Object> map){
        //获取前端传递的数据
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");
        //将前端传来的code与session中的code比较
        Object sessionCode = session.getAttribute("code");
        if(code!=null && code.equals(sessionCode)){
            //根据手机号查询用户信息
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone, phone);
            User user = userService.getOne(lqw);
            if (user == null) {
                //用户不存在，注册用户信息
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            //将用户id存到session中
            session.setAttribute("user", user.getId());
            return R.success("用户登录成功");
        }
        return R.error("验证码错误");
    }
}
