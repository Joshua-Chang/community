package com.example.test.demo.services;

import com.example.test.demo.dto.PageDTO;
import com.example.test.demo.dto.QuestionDTO;
import com.example.test.demo.mapper.QuestionMapper;
import com.example.test.demo.mapper.UserMapper;
import com.example.test.demo.model.Question;
import com.example.test.demo.model.User;
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
        Integer count = questionMapper.count();
        int totalPage;
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageDTO pageDTO=new PageDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreater());
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
        Integer count = questionMapper.countByUser(userID);
        int totalPage;
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listByUser(userID,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageDTO pageDTO=new PageDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreater());
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
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(userMapper.findById(question.getCreater()));
        return questionDTO;
    }
}
