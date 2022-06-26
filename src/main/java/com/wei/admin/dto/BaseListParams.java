package com.wei.admin.dto;

import com.wei.common.Pager;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author wlp
 * @date 2022/6/24
 **/
@Data
public class BaseListParams {

    @ApiParam(value = "数据启用状态，0：全部，1：启用，2：禁用", required = false)
    @Range(min = 0, max = 2, message = "无效角色启用状态")
    private Integer enabled;

    @ApiParam(value = "查询开始时间戳秒", required = false)
    @Range(min = 0, message = "时间格式错误")
    protected Long createStartTime;

    @ApiParam(value = "查询结束时间戳秒", required = false)
    @Range(min = 0, message = "时间格式错误")
    protected Long createEndTime;

    @ApiParam(value = "分页偏移量", required = false)
    @Range(min = 1, message = "无效分页偏移量")
    protected Integer pageSize;

    @ApiParam(value = "页码", required = false)
    @Range(min = 1, message = "无效页码")
    protected Integer pageNum;

    public void handleListParams() {
        if (this.getPageNum() == null || this.getPageNum() < 1) {
            this.setPageNum(Pager.PAGE_NUM_DEFAULT);
        }
        if (this.getPageSize() == null || this.getPageSize() < 1) {
            this.setPageSize(Pager.PAGE_SIZE_DEFAULT);
        }
    }
}
