package com.example.fklast.controller;


import com.example.fklast.domain.Exam;
import com.example.fklast.service.ExamService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/exam")
public class ExamController
{
    @Resource
    private ExamService examService;

    /**
     * 添加卷子
     */
    @PostMapping ("/insert")
    private Result insertExam ( @RequestBody Exam exam )
    {
        return examService.insertExam(exam);
    }

    /**
     * 分页展示题
     */
    @GetMapping (value = { "/page/{currentPage}/{pageSize}/{uid}", "/page/{currentPage}/{pageSize}" })
    public Result getExamByPage ( @PathVariable ("currentPage") int currentPage, @PathVariable ("pageSize") int pageSize,
                                  @PathVariable (value = "uid", required = false) String uid,
                                  @RequestParam (required = false) String keyWord,
                                  @RequestParam (required = false) String sort,
                                  @RequestParam (required = false) boolean flag )
    {
        return examService.findExamByPage(currentPage, pageSize, uid, keyWord, sort, flag);
    }

    /**
     * 查询单张试卷具体内容
     */
    @GetMapping ("/findOne/{eid}")
    private Result getExamById ( @PathVariable String eid )
    {
        return examService.findExamById(eid);
    }


    /**
     * 逻辑删除
     */
    @PostMapping ("/delete/{eid}")
    public Result examDelete ( @PathVariable String eid )
    {
        return new Result(examService.deleteExam(eid));
    }


    /**
     * 更新试卷
     */
    @PostMapping ("/update")
    private Result examUpdate ( @RequestBody Exam exam )
    {
        return new Result(examService.updateExam(exam));
    }


    /**
     * 审核员审核
     */
    @PostMapping ("/check/{eid}")
    public Result checkVideo ( @PathVariable String eid, @RequestParam String state )
    {
        return new Result(examService.checkExam(eid, state), state, "成功");
    }

}
