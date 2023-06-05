package cn.czyx007.mt.bean;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * 菜品口味关系表(DishFlavor)表实体类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
@Data
@NoArgsConstructor
@TableName("dish_flavor")
public class DishFlavor implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜品
     */
    private Long dishId;

    /**
     * 口味名称
     */
    private String name;

    /**
     * 口味数据list
     */
    private String value;

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

    /**
     * 是否删除
     */
    private Integer isDeleted;

}
