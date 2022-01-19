package com.kycni.community.service;

import com.kycni.community.dto.PaginationDto;
import com.kycni.community.dto.QuestionDto;
import com.kycni.community.dao.QuestionMapper;
import com.kycni.community.dao.UserMapper;
import com.kycni.community.model.Question;
import com.kycni.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kycni
 * @date 2022/1/18 9:06
 */
@Service
public class QuestionService {
    /**
     * 同时操作两个数据库表,Service层封装通用的业务逻辑，加一层Service更好
     * 封装每个model与业务相关的通用数据接口，比如：查询订单。
     */
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDto indexList(Integer page, Integer size) {
        
        /*创建类的对象*/
        PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = questionMapper.count();
        /*应用类的分页方法*/
        paginationDto.setPagination(totalCount, page, size);
        
        /*设置当前页*/
        if (page < 1) {
            page = 1;
        }
        
        if (page > paginationDto.getTotalPage()) {
            page = paginationDto.getTotalPage();
        }

        //设置偏移量: size*(page-1): 跳过的数据行
        Integer offset = size * (page - 1);
        /*获取整体列表数组    显示逻辑数据显示的起始和结束,首页第一个数据,最后一个数据*/
        List<Question> questions = questionMapper.list(offset, size);
        
        /*
        获取列表中的对象  包含question和user表中信息  将对象加入整体列表数组
         */
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            //调用方法初始化引用对象
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        
        paginationDto.setQuestions(questionDtos);
        return paginationDto;
    }
}

