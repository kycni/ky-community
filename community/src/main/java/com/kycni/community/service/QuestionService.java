package com.kycni.community.service;

import com.kycni.community.exception.CustomizeErrorCode;
import com.kycni.community.exception.CustomizeException;
import com.kycni.community.mapper.QuestionMapper;
import com.kycni.community.mapper.UserMapper;
import com.kycni.community.dto.PaginationDTO;
import com.kycni.community.dto.QuestionDTO;
import com.kycni.community.model.Question;
import com.kycni.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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

    /**
     * index问题列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link PaginationDTO}
     */
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
        return getPaginationDTO(paginationDTO, questionList);

    }

    /**
     * profile问题列表
     *
     * @param userId 用户id
     * @param page   页面
     * @param size   大小
     * @return {@link PaginationDTO}
     */
    public PaginationDTO list(Long userId, Integer page, Integer size) {

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
        return getPaginationDTO(paginationDTO, questionList);

    }

    /**
     * 指定用户问题信息
     *
     * @param id id
     * @return {@link QuestionDTO}
     */
    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FIND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     * 创建或更新问题信息
     *
     * @param question 问题
     */
    public void createOrUpdate(Question question) {
        /*不存在问题*/
        if (question.getId() == null) {
            // 创建一个问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setViewCount(0);
            /*Selective不会存入Null，会使用数据库默认值*/
            questionMapper.insert(question);
        } else {
            // 更新
            /*创建并初始化数据库中的问题*/
            Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());
            /*在用户查看界面的时候，问题被更改，则刷新显示问题不存在*/
            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            if (dbQuestion.getCreator().longValue() != question.getCreator().longValue()) {
                throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
            }
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            questionMapper.update(question);
        }
    }

    /**
     * 浏览数+1
     *
     * @param id id
     */
    public void incView(Long id) {
        /*创建一个问题对象*/
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionMapper.incView(question);
    }

    /**
     * 得到分页dto
     *
     * @param paginationDTO 分页dto
     * @param questionList  问题列表
     * @return {@link PaginationDTO}
     */
    private PaginationDTO getPaginationDTO(PaginationDTO paginationDTO, List<Question> questionList) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.findById(question.getCreator());
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;
    }
}

