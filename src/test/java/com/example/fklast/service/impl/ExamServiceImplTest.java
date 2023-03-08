package com.example.fklast.service.impl;

import com.example.fklast.service.ExamService;
import com.example.fklast.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ExamServiceImplTest
{


    @Resource
    private ExamService examService;

    @Test
    void findExamById ()
    {
        Result examById = examService.findExamById("018eafc6716b488ba90c3b92731eb5b3");
        System.out.println(examById);
    }
}