package top.tangyh.lamp.reg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author nie
 * @date 2022/01/01 12:55
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegCredentialsDTO {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 身份证号
     */
    private Long userId;

    /**
     * 患者姓名
     */
    private String userName;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 就诊科室
     */
    private String treatmentDepartment;

    /**
     * 就诊序号
     */
    private Integer number;


}
