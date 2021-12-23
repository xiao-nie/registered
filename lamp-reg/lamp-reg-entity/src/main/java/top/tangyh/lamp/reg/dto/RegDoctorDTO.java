package top.tangyh.lamp.reg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.tangyh.lamp.authority.enumeration.auth.Sex;

/**
 * <p>
 *
 * </p>
 *
 * @author nie
 * @date: 2021-12-21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegDoctorDTO {

    @JsonProperty("value")
    private Long doctor_id;
    @JsonProperty("label")
    private String name;
    private Sex sex;
    private String avatar;
    private Integer sourceCount;
    private String theTitle;
    private String context;
    private String sonOrgName;

}
