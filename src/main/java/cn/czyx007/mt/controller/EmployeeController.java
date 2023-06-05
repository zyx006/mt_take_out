package cn.czyx007.mt.controller;

import cn.czyx007.mt.bean.Employee;
import cn.czyx007.mt.service.EmployeeService;
import cn.czyx007.mt.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * 员工信息(Employee)表控制层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:09:40
 */
@RestController
@RequestMapping("employee")
public class EmployeeController {
    /**
     * 服务对象
     */
    @Autowired
    private EmployeeService employeeService;

    //用户登录
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpSession session){
        //加密前端提交的密码
        String pwd = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        //根据提交的用户名查询数据库
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(lqw);
        //若没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("用户名不存在");
        }
        //密码比对，不一致则返回登录失败结果
        if (!emp.getPassword().equals(pwd)){
            return R.error("密码错误");
        }
        //查看员工状态，若为已禁用则返回员工已禁用结果
        if (emp.getStatus() == 0){
            return R.error("用户已经禁用，无法登录！");
        }
        //登录成功，将员工id存入session，并返回登录成功结果
        session.setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    //退出登录
    @PostMapping("/logout")
    R<String> logout(HttpSession session){
        //把当前登录用户的id从session中移除
        session.removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> addEmployee(@RequestBody Employee employee, HttpSession session){
        //1.默认密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex(("123456").getBytes()));
        //2.status有默认值1
        //3.创建时间
        employee.setCreateTime(LocalDateTime.now());
        //4.更新时间
        employee.setUpdateTime(LocalDateTime.now());
        //5.创建人
        Long id = (Long) session.getAttribute("employee");
        employee.setCreateUser(id);
        //6.更新人
        employee.setUpdateUser(id);
        employeeService.save(employee);
        return R.success("添加成功");
    }

    //分页查询员工信息
    @GetMapping("/page")
    public R<IPage<Employee>> page(@RequestParam Integer page,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(required = false) String name){
        IPage<Employee> iPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.like(!StringUtils.isEmpty(name), Employee::getName, name).orderByDesc(Employee::getUpdateTime);
        employeeService.page(iPage, lqw);
        return R.success(iPage);
    }

    //修改员工状态
    @PutMapping
    public R<String> updateEmployee(HttpSession session, @RequestBody Employee employee){
        //获取当前线程id
        long id = Thread.currentThread().getId();

        Long userId = (Long) session.getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(userId);
        //mybatisplus只会更新非空字段
        employeeService.updateById(employee);
        return R.success("状态修改成功！");
    }

    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable("id") Long userId){
        Employee employee = employeeService.getById(userId);
        return R.success(employee);
    }
}
