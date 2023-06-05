package cn.czyx007.mt.bean;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * 菜品及套餐分类(Category)表实体类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
@Data
@NoArgsConstructor
@TableName("category")
public class Category implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型   1 菜品分类 2 套餐分类
     */
    private Integer type;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 顺序
     */
    private Integer sort;

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
