package top.tangyh.lamp.reg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class RegOrgDTO {

    @JsonProperty("value")
    private Long orgId;
    @JsonProperty("label")
    private String label;
    @JsonProperty("children")
    private List<RegDoctorDTO> doctors;


}
