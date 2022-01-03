package top.tangyh.lamp.reg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.tangyh.lamp.reg.entity.Registration;

import java.util.List;

/**
 * @author nie
 * @date 2022/01/02 17:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MinDTO {

    private Registration registration;

    private RegUserInfo regUserInfo;

    private List<HistoryDTO> hisList;

}
