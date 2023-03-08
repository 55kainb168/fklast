package com.example.fklast.controller;

import com.example.fklast.domain.Judge;
import com.example.fklast.service.JudgeService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/judge")
public class JudgeController
{
    @Resource
    private JudgeService judgeService;


    /**
     * 添加选择题
     */
    @PostMapping ("/insert")
    public Result insertJudge ( @RequestBody Judge judge )
    {
        return judgeService.insertJudge(judge);
    }


    /**
     * 逻辑删除选择题
     */
    @DeleteMapping ("/delete/{jid}")
    public Result deleteJudge ( @PathVariable String jid )
    {
        return new Result(judgeService.deleteJudge(jid));
    }

    /**
     * 更新题目信息
     */
    @PostMapping ("/update")
    public Result updateJudge ( @RequestBody Judge judge )
    {
        return new Result(judgeService.updateJudge(judge));
    }

    /**
     * 分页展示选择题
     */
    @GetMapping (value = { "/page/{currentPage}/{pageSize}/{uid}", "/page/{currentPage}/{pageSize}" })
    public Result getJudgeByPage ( @PathVariable ("currentPage") int currentPage, @PathVariable ("pageSize") int pageSize, @PathVariable (value = "uid", required = false) String uid, @RequestParam (required = false) String keyWord, @RequestParam (required = false) String sort )
    {
        return judgeService.findJudgeByPage(currentPage, pageSize, uid, keyWord, sort);
    }
}
