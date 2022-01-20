package com.kycni.community.service;

import com.kycni.community.mapper.QuestionMapper;
import com.kycni.community.mapper.UserMapper;
import com.kycni.community.dto.PaginationDTO;
import com.kycni.community.dto.QuestionDTO;
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
    
    public PaginationDTO list(Integer page, Integer size) {
        
        /*创建类的对象*/
        PaginationDTO paginationDTO = new PaginationDTO();
        int totalPage;
        Integer totalCount = questionMapper.count();
        
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        
        /*应用类的分页方法*/
        paginationDTO.setPagination(totalPage, page);
        
        /*设置当前页*/
        if (page < 1) {
            page = 1;
        }
        
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        /*设置偏移量: size*(page-1): 跳过的数据行*/
        Integer offset = size * (page - 1);
        
        /*获取问题集合  显示逻辑数据显示的起始和结束,首页第一个数据对象,最后一个数组*/
        List<Question> questionList = questionMapper.list(offset, size);
        
        /*获取主页列表（包含问题集和问题对应的用户信息）*/
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        
        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;
        
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {

        /*创建类的对象*/
        PaginationDTO paginationDTO = new PaginationDTO();
        int totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        /*应用类的分页方法*/
        paginationDTO.setPagination(totalPage, page);

        /*设置当前页*/
        if (page < 1) {
            page = 1;
        }

        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        /*设置偏移量: size*(page-1): 跳过的数据行*/
        Integer offset = size * (page - 1);

        /*获取问题集合  显示逻辑数据显示的起始和结束,首页第一个数据对象,最后一个数组*/
        List<Question> questionList = questionMapper.listByUserId(userId, offset, size);

        /*获取主页列表（包含问题集和问题对应的用户信息）*/
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;

    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else {
            // 更新
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}

