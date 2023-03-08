package com.example.fklast.controller;


import com.example.fklast.domain.Gap;
import com.example.fklast.service.GapService;
import com.example.fklast.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping ("/gap")
public class GapController
{
    @Resource
    private GapService gapService;


    /**
     * 添加选择题
     */
    @PostMapping ("/insert")
    public Result insertOption ( @RequestBody Gap gap )
    {
        return gapService.insertGap(gap);
    }


    /**
     * 逻辑删除选择题
     */
    @DeleteMapping ("/delete/{gid}")
    public Result deleteOption ( @PathVariable String gid )
    {
        return new Result(gapService.deleteGap(gid));
    }


    /**
     * 更新题目信息
     */
    @PostMapping ("/update")
    public Result updateGap ( @RequestBody Gap gap )
    {
        return new Result(gapService.updateGap(gap));
    }


    /**
     * 分页展示选择题
     */
    @GetMapping (value = { "/page/{currentPage}/{pageSize}/{uid}", "/page/{currentPage}/{pageSize}" })
    public Result getGapByPage ( @PathVariable ("currentPage") int currentPage, @PathVariable ("pageSize") int pageSize, @PathVariable (value = "uid", required = false) String uid, @RequestParam (required = false) String keyWord, @RequestParam (required = false) String sort )
    {
        return gapService.findGapByPage(currentPage, pageSize, uid, keyWord, sort);
    }


}
