package top.tangyh.lamp.tenant.no;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author nie
 * @date 2021/12/04 15:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeleteIds {

    List<Long> ids;

}
