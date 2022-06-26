package com.wei.admin.service;

import com.wei.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wlp
 * @date 2022/6/24
 **/
@Slf4j
@Service
public class BaseService {
    protected Result returnEditResult(Integer total) {
        if (total == 0) {
            return Result.nothingUpdated();
        }
        return Result.success("更新成功");
    }

    protected Result returnEnableResult(boolean enabled) {
        if (enabled) {
            return Result.success("启用成功");
        }
        return Result.success("禁用成功");
    }
}
