package com.atguigu.Handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Date:2022/6/23
 *
 * @author:yz
 */
@Component
public class MybatisHandler implements MetaObjectHandler {

    /**
     * String fieldName, 设置的字段对应的属性
     * Object fieldVal,设置字段的值
     * MetaObject metaObject,元数据
     * @param metaObject
     */

    /**
     * 更新时的填充策略
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //String fieldName, Object fieldVal, MetaObject metaObject

        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }



    /**
     * 修改时的填充策略
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        /**
         * String fieldName, 设置的字段对应的属性
         * Object fieldVal,设置字段的值
         * MetaObject metaObject,元数据
         * @param metaObject
         */
        //this.setFieldValByName("creatTime",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
