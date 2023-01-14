package com.example.fklast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.fklast.domain.Judge;
import com.example.fklast.utils.Result;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_judge】的数据库操作Service
 * @createDate 2023-01-12 21:32:57
 */
public interface JudgeService extends IService<Judge>
{


    /**
     * 添加填空题
     */
    Result insertJudge ( Judge judge );

    /**
     * 逻辑删除填空题
     */
    Boolean deleteJudge ( String jid );

    /**
     * 更改题目内容
     */
    Boolean updateJudge ( Judge judge );

    /**
     * 分页展示填空题
     */
    Result findJudgeByPage ( int currentPage, int pageSize, String uid, String keyWord, String sort );


}
