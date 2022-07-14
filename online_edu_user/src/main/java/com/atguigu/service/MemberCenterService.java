package com.atguigu.service;

import com.atguigu.entity.MemberCenter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yz
 * @since 2022-07-08
 */
public interface MemberCenterService extends IService<MemberCenter> {

    Integer queryRegisterNum(String day);
}
