package top.tangyh.lamp.reg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author nie
 * @date 2022/01/03 13:43
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegUserInfo {

    /**
     * id
     */
    private Long id;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 当前就诊状态
     */
    private String state;

}
