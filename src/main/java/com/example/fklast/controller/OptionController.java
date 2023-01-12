package com.example.fklast.controller;

import com.example.fklast.domain.Option;
import com.example.fklast.service.OptionService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/option")
public class OptionController
{
    @Resource
    private OptionService optionService;

    /**
     * 添加选择题
     */
    @PostMapping ("/insert")
    public Result insertOption ( @RequestBody Option option )
    {
        return new Result(optionService.insertOption(option));
    }


    /**
     * 逻辑删除选择题
     */
    @DeleteMapping ("/delete")
    public Result deleteOption ( @RequestParam String oid )
    {
        return new Result(optionService.deleteOption(oid));
    }


    /**
     * 分页展示选择题
     */
    @GetMapping (value = { "/page/{currentPage}/{pageSize}/{uid}", "/page/{currentPage}/{pageSize}" })
    public Result getArticleByUserID ( @PathVariable ("currentPage") int currentPage, @PathVariable ("pageSize") int pageSize, @PathVariable (value = "uid", required = false) String uid, @RequestParam (required = false) String keyWord, @RequestParam (required = false) String sort )
    {
        return optionService.findOptionByPage(currentPage, pageSize, uid, keyWord, sort);
    }


}
