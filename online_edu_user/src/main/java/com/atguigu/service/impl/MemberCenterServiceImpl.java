package com.atguigu.service.impl;

import com.atguigu.entity.MemberCenter;
import com.atguigu.mapper.MemberCenterMapper;
import com.atguigu.service.MemberCenterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yz
 * @since 2022-07-08
 */
@Service
public class MemberCenterServiceImpl extends ServiceImpl<MemberCenterMapper, MemberCenter> implements MemberCenterService {

    @Override
    public Integer queryRegisterNum(String day) {
        return baseMapper.queryRegisterNum(day);
    }
}
