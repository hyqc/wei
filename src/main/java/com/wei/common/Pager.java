package com.wei.common;

import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据封装类
 *
 * @author Administrator
 */
public class Pager<T> {

    /**
     * 默认页码
     */
    public static final int PAGE_NUM_DEFAULT = 1;
    /**
     * 默认偏移量
     */
    public static final int PAGE_SIZE_DEFAULT = 20;
    /**
     * 默认查询总数
     */
    public static final long TOTAL_DEFAULT = 0;

    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 总查询数量
     */
    private Long total;
    /**
     * 分页数据
     */
    private List<T> rows;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> Pager<T> restPage(List<T> rows) {
        Pager<T> result = new Pager<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(rows);
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    /**
     * 将SpringData分页后的list转为分页信息
     */
    public static <T> Pager<T> restPage(Page<T> pageInfo) {
        Pager<T> result = new Pager<T>();
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setRows(pageInfo.getContent());
        return result;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum == null ? PAGE_NUM_DEFAULT : pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null ? PAGE_SIZE_DEFAULT : pageSize;
    }

    public List<T> getList() {
        return rows;
    }

    public void setRows(List<T> list) {
        this.rows = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total == null ? TOTAL_DEFAULT : total;
    }

    public Pager() {
        this.pageNum = PAGE_NUM_DEFAULT;
        this.pageSize = PAGE_SIZE_DEFAULT;
        this.total = TOTAL_DEFAULT;
        this.rows = new ArrayList<>();
    }
}
