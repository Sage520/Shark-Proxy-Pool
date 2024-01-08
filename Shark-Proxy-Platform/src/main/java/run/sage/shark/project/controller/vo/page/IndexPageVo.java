package run.sage.shark.project.controller.vo.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页VO
 *
 * @author Sage
 * @date 2023/02/24
 */
@Data
public class IndexPageVo implements Serializable {

    private int pageSize = 0;

    private int pageNo = 0;

    private long pageCount = 0;

    private List<IndexPageItemVo> list;

}
