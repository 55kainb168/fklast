package com.example.fklast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.fklast.domain.Exam;
import com.example.fklast.utils.Result;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_exam】的数据库操作Service
 * @createDate 2023-03-01 10:37:11
 */
public interface ExamService extends IService<Exam>
{


    /**
     * 添加习题组
     */
    Result insertExam ( Exam exam );

    /**
     * 习题分页列表
     * 有查询功能
     */
    Result findExamByPage ( int currentPage, int pageSize, String uid, String keyWord, String sort, boolean flag );

    /**
     * 查询单个试卷，展示试题
     */
    Result findExamById ( String eid );

    /**
     * 更新试卷
     */
    Boolean updateExam ( Exam exam );

    /**
     * 删除
     */
    Boolean deleteExam ( String eid );

    /**
     * 审核习题
     */
    Boolean checkExam ( String eid, String state );
}
