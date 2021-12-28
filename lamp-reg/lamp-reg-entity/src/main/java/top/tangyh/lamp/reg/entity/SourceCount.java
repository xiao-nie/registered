package top.tangyh.lamp.reg.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import top.tangyh.basic.base.entity.Entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 *
 * </p>
 *
 * @author nie
 * @date: 2021-12-20
 **/
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("c_source_count")
@ApiModel(value = "SourceCount", description = "号源")
@AllArgsConstructor
@Builder
public class SourceCount extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 医生id
     */
    @ApiModelProperty(value = "医生id")
    @NotEmpty(message = "医生id")
    @Size(max = 30, message = "医生id长度不能超过30")
    @TableField(value = "doctor_id")
    @Excel(name = "账号")
    private Long doctorId;

    /**
     * 号源
     */
    @ApiModelProperty(value = "号源")
    @NotEmpty(message = "号源")
    @Size(max = 3, message = "号源长度不能超过3")
    @TableField(value = "source_count")
    @Excel(name = "账号")
    private Integer sourceCount;


}
