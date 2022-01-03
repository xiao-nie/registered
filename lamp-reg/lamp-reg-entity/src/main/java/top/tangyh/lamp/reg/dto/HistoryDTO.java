package top.tangyh.lamp.reg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nie
 * @date 2022/01/03 21:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDTO {

    private Long id;
    private String date;
    private String info;

}
