package com.atguigu.mapper;

import com.atguigu.entity.MemberCenter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author yz
 * @since 2022-07-08
 */
public interface MemberCenterMapper extends BaseMapper<MemberCenter> {

    Integer queryRegisterNum(String day);
}
