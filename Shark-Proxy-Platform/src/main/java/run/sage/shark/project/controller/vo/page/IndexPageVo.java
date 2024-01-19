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

    /**
     * 页面数量
     */
    private int pageSize = 0;

    /**
     * 页码
     */
    private int pageNo = 0;

    /**
     * 数据总数
     */
    private long pageCount = 0;

    /**
     * 数据列表
     */
    private List<IndexPageItemVo> list;

}
