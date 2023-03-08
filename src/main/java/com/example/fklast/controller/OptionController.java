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
        return optionService.insertOption(option);
    }


    /**
     * 逻辑删除选择题
     */
    @DeleteMapping ("/delete/{oid}")
    public Result deleteOption ( @PathVariable String oid )
    {
        return new Result(optionService.deleteOption(oid));
    }

    /**
     * 更改题目内容
     */
    @PostMapping ("/update")
    public Result updateOption ( @RequestBody Option option )
    {
        return new Result(optionService.updateOption(option));
    }


    /**
     * 分页展示选择题
     */
    @GetMapping (value = { "/page/{currentPage}/{pageSize}/{uid}", "/page/{currentPage}/{pageSize}" })
    public Result getOptionByPage ( @PathVariable ("currentPage") int currentPage, @PathVariable ("pageSize") int pageSize, @PathVariable (value = "uid", required = false) String uid, @RequestParam (required = false) String keyWord, @RequestParam (required = false) String sort )
    {
        return optionService.findOptionByPage(currentPage, pageSize, uid, keyWord, sort);
    }


}
