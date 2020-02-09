package com.example.test.demo.services;

import com.example.test.demo.dto.PageDTO;
import com.example.test.demo.dto.QuestionDTO;
import com.example.test.demo.exception.CustomizeErrorCode;
import com.example.test.demo.exception.CustomizeException;
import com.example.test.demo.mapper.QuestionMapper;
import com.example.test.demo.mapper.UserMapper;
import com.example.test.demo.model.Question;
import com.example.test.demo.model.QuestionExample;
import com.example.test.demo.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;


    public PageDTO list(Integer page, Integer size) {
        Integer count =(int) questionMapper.countByExample(new QuestionExample());
        int totalPage;
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
//        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageDTO pageDTO=new PageDTO();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreater());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        pageDTO.setPage(count,page,size);

        return pageDTO;
    }

    public PageDTO list(Integer userID, Integer page, Integer size) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreaterEqualTo(userID);
        Integer count =(int) questionMapper.countByExample(example);
//        Integer count = questionMapper.countByUser(userID);
        int totalPage;
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;
        Integer offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreaterEqualTo(userID);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,size));

//        List<Question> questions = questionMapper.listByUser(userID,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageDTO pageDTO=new PageDTO();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreater());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        pageDTO.setPage(count,page,size);

        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
//            throw new CustomizeException("你找到问题不在了，要不要换个试试？");
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreater()));
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
//            questionMapper.update(question);
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(question, example);
            if (updated!=1) {
//                throw new CustomizeException("你找到问题不在了，要不要换个试试？");
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }

    }
}
