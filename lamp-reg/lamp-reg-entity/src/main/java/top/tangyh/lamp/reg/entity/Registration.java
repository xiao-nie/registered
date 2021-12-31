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
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author nie
 * @date: 2021-12-28
 **/
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("c_registration")
@ApiModel(value = "Registration", description = "挂号信息")
@AllArgsConstructor
@Builder
public class Registration extends Entity<Long> {

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
     * 患者id
     */
    @ApiModelProperty(value = "患者id")
    @NotEmpty(message = "患者id")
    @Size(max = 30, message = "患者id长度不能超过30")
    @TableField(value = "user_id")
    @Excel(name = "患者id")
    private Long userId;

    /**
     * 就诊时间
     */
    @ApiModelProperty(value = "就诊时间")
    @NotEmpty(message = "就诊时间")
    @TableField(value = "clinical_time")
    @Excel(name = "就诊时间")
    private Date clinicalTime;

    /**
     * 就诊序号
     */
    @ApiModelProperty(value = "就诊序号")
    @NotEmpty(message = "就诊序号")
    @Size(max = 3, message = "就诊序号长度不能超过3")
    @TableField(value = "number")
    @Excel(name = "就诊序号")
    private Integer number;

    /**
     * 就诊状态
     * 0:待就诊
     * 1:就诊中
     * 2:已就诊
     */
    @ApiModelProperty(value = "就诊状态")
    @NotEmpty(message = "就诊状态")
    @Size(max = 1, message = "就诊状态长度不能超过1")
    @TableField(value = "state")
    @Excel(name = "就诊状态")
    private Integer state;

    /**
     * 病历信息
     */
    @ApiModelProperty(value = "病历信息")
    @NotEmpty(message = "病历信息")
    @Size(max = 255, message = "病历信息长度不能超过255")
    @TableField(value = "case_history")
    @Excel(name = "病历信息")
    private String caseHistory;


}
