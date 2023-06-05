package cn.czyx007.mt.bean;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * 员工信息(Employee)表实体类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
@Data
@NoArgsConstructor
@TableName("employee")
public class Employee implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 状态 0:禁用，1:正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
