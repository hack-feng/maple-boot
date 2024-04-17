package com.maple.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.system.bean.OperateLog;
import com.maple.system.mapper.OperateLogMapper;
import com.maple.system.service.IOperateLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统管理-操作日志记录 服务实现类
 * </p>
 *
 * @author 笑小枫
 * @since 2023-01-30
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements IOperateLogService {

}
