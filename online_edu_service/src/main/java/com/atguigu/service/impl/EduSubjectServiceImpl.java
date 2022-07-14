package com.atguigu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.SubjectListener;
import com.atguigu.config.MyException;
import com.atguigu.entity.EduSubject;
import com.atguigu.entity.SubjectExcel;
import com.atguigu.mapper.EduSubjectMapper;
import com.atguigu.response.SubjectVO;
import com.atguigu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author yz
 * @since 2022-07-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //这里必须通过注入的方式去,不能在下面new,不然找不到
    @Autowired
    private SubjectListener subjectListener;

    /**
     * 上传
     * @param file
     * @throws IOException
     */
    @Override
    public void uploadSubject(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        EasyExcel.read(inputStream, SubjectExcel.class, subjectListener).doReadAll();
    }

    /**
     * 保存
     * @param parentSubjectName
     * @param parentId
     * @return
     */
    @Override
    public EduSubject existSubject(String parentSubjectName, String parentId) {
        //创建查询条件
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        //用parent_id和parentId做比较,添加两个条件
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.eq("title", parentSubjectName);
        //根据查询的条件返回一个对象,返回
        EduSubject existSubject = baseMapper.selectOne(queryWrapper);
        return existSubject;
    }

    /**
     *区分一级分类还是二级分类
     * @return
     */
    @Override
    public List<SubjectVO> getAllSubject() {
        //a.设计一个返回给前端的对象
        //b.查询所有的课程分类
        List<EduSubject> allSubjectList = baseMapper.selectList(null);
        //创建一个一级分类的集合
        List<SubjectVO> parentSubjectVoList = new ArrayList<>();
        //c.查询所有的一级分类(找组长)
        for (EduSubject subject : allSubjectList) {
            //判断标准 parentId=0
            if(subject.getParentId().equals("0")){
                SubjectVO subjectVO = new SubjectVO();
                subjectVO.setTitle(subject.getTitle());
                subjectVO.setId(subject.getId());
                parentSubjectVoList.add(subjectVO);
            }
        }
        //d.把一级分类放到一个角落(map)
        Map<String, SubjectVO> parentSubjectMap = new HashMap<>();
        for (SubjectVO parentSubjectVO : parentSubjectVoList) {
            // key一级分类的id value	一级分类对象本身
            parentSubjectMap.put(parentSubjectVO.getId(),parentSubjectVO);
        }
        //e.查询二级分类对象(找组员)
        for (EduSubject childSubject : allSubjectList) {
            //判断标准 parentId!=0
            if(!childSubject.getParentId().equals("0")){
                //拿到二级分类里面的parentId(组号) 从map中找到二级分类的一级分类
                SubjectVO parentSubjectVO = parentSubjectMap.get(childSubject.getParentId());
                SubjectVO childSubjectVO = new SubjectVO();
                childSubjectVO.setId(childSubject.getId());
                childSubjectVO.setTitle(childSubject.getTitle());
                //得到一级分类的children 把二级分类塞到里面去 成为它的子节点
                parentSubjectVO.getChildren().add(childSubjectVO);
            }
        }
        //f.返回所有的一级分类
        return parentSubjectVoList;
    }

    /**
     * 删除节点
     * @param id
     * @return
     */
    @Override
    public boolean deleteSubjectById(String id) {
        //拿到这个id去查询它下面是否有子节点
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        //判断该count==0 代表没有子节点 可以删除
        if(count==0){
            int rows = baseMapper.deleteById(id);
            return rows>0;
        }else{
            throw new MyException(20001,"该节点存在子节点");
        }
    }

    /**
     * 保存一级分类菜单
     * @param subject
     * @return
     */
    @Override
    public boolean saveParentSubject(EduSubject subject) {
        //判断该一级分类是否存在
        EduSubject parentSubject = existSubject(subject.getTitle(), "0");
        if(parentSubject==null){
            parentSubject=new EduSubject();
            parentSubject.setTitle(subject.getTitle());
            parentSubject.setParentId("0");
            int rows = baseMapper.insert(parentSubject);
            return rows>0;
        }
        return false;
    }

    @Override
    public boolean saveChildSubject(EduSubject subject) {
        //判断该一级分类是否存在
        EduSubject childSubject = existSubject(subject.getTitle(), subject.getParentId());
        if(childSubject==null){
            int rows = baseMapper.insert(subject);
            return rows>0;
        }
        return false;
    }
}
