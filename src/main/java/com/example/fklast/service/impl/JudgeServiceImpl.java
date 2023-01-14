package com.example.fklast.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fklast.domain.Judge;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.mapper.JudgeMapper;
import com.example.fklast.mapper.VideoMapper;
import com.example.fklast.service.JudgeService;
import com.example.fklast.utils.Result;
import com.example.fklast.utils.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_judge】的数据库操作Service实现
 * @createDate 2023-01-12 21:32:57
 */
@Service
public class JudgeServiceImpl extends ServiceImpl<JudgeMapper, Judge> implements JudgeService
{
    @Resource
    private JudgeMapper judgeMapper;
    @Resource
    private VideoMapper videoMapper;


    @Override
    public Result insertJudge ( Judge judge )
    {
        if ( ObjectUtil.isEmpty(videoMapper.selectById(judge.getVid())) )
        {
            return new Result(true, "视频不存在");
        }
        judge.setJid(UUID.randomUUID().toString(true));
        UserDTO userDTO = UserHolder.getUser();
        judge.setUid(judge.getUid());
        judge.setJudgeDelete("1");
        judge.setCreateTime(LocalDateTime.now());
        judge.setUpdateTime(LocalDateTime.now());
        return new Result(judgeMapper.insert(judge) > 0);
    }

    @Override
    public Boolean deleteJudge ( String jid )
    {
        return judgeMapper.deleteById(jid) > 0;
    }

    /**
     * 更改题目内容
     */
    @Override
    public Boolean updateJudge ( Judge judge )
    {
        judge.setUpdateTime(LocalDateTime.now());
        return judgeMapper.updateById(judge) > 0;
    }

    @Override
    public Result findJudgeByPage ( int currentPage, int pageSize, String uid, String keyWord, String sort )
    {
        LambdaQueryWrapper<Judge> lqw = new LambdaQueryWrapper<>();
        IPage<Judge> page = new Page<>(currentPage, pageSize);
        lqw.eq(Judge::getJudgeDelete, "1");
        judgeMapper.selectPage(page, lqw);
        //如果当前页码大于总页码，那么重新执行查询操作，使用最大页码值作为当前页码值
        if ( currentPage > page.getPages() )
        {
            page.setCurrent(page.getPages());
            judgeMapper.selectPage(page, lqw);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", currentPage);
        map.put("totalPage", page.getPages());
        map.put("total", page.getTotal());
        map.put("Judges", page.getRecords());
        return new Result(true, map);
    }
}




