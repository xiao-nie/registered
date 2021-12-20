package top.tangyh.lamp.reg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import top.tangyh.basic.base.entity.Entity;

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
@TableName("c_user")
@ApiModel(value = "User", description = "用户")
@AllArgsConstructor
@Builder
public class SourceCount extends Entity<Long> {

    private static final long serialVersionUID = 1L;


}
