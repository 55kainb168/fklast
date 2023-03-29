package com.example.fklast.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fklast.domain.Exam;
import com.example.fklast.domain.Gap;
import com.example.fklast.domain.Judge;
import com.example.fklast.domain.Option;
import com.example.fklast.dto.ExamDTO;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.mapper.*;
import com.example.fklast.service.ExamService;
import com.example.fklast.utils.Result;
import com.example.fklast.utils.UserHolder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_exam】的数据库操作Service实现
 * @createDate 2023-03-01 10:37:11
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam>
        implements ExamService
{

    @Resource
    private ExamMapper examMapper;

    @Resource
    private OptionMapper optionMapper;

    @Resource
    private JudgeMapper judgeMapper;

    @Resource
    private GapMapper gapMapper;

    @Resource
    private VideoMapper videoMapper;

    /**
     * 添加习题组
     */
    @Override
    public Result insertExam ( Exam exam )
    {
        if ( Strings.isNotBlank(exam.getVid()) && ObjectUtil.isEmpty(videoMapper.selectById(exam.getVid())) )
        {
            return new Result(true, "视频不存在");
        }
        UserDTO userDTO = UserHolder.getUser();
        exam.setEid(UUID.fastUUID().toString(true));
        if ( Strings.isBlank(exam.getOptionIds()) && Strings.isBlank(exam.getJudgeIds()) && Strings.isBlank(exam.getGapIds()) )
        {
            exam.setOptionCount(0);
            exam.setJudgeCount(0);
            exam.setGapCount(0);
        }
        exam.setEUid(userDTO.getUid());
        exam.setEUsername(userDTO.getUsername());
        exam.setEState("待审核");
        exam.setEDelete("1");
        exam.setCreatTime(LocalDateTime.now());
        exam.setUpdateTime(LocalDateTime.now());
        return new Result(examMapper.insert(exam) > 0);
    }


    /**
     * 习题分页列表
     * 有查询功能
     */
    @Override
    public Result findExamByPage ( int currentPage, int pageSize, String uid, String keyWord, String sort, boolean flag )
    {
        LambdaQueryWrapper<Exam> lqw = new LambdaQueryWrapper<>();
        IPage<Exam> page = new Page<>(currentPage, pageSize);
        lqw.eq(Exam::getEDelete, "1");
        if ( ! flag )
        {
            lqw.eq(Exam::getEState, "通过");
        }
        if ( Strings.isNotBlank(uid) )
        {
            lqw.eq(Exam::getEUid, uid);
        }
        //查询条件
        if ( Strings.isNotBlank(keyWord) && ! keyWord.equals("undefined") )
        {
            lqw.and(i -> i.like(Strings.isNotBlank(keyWord), Exam::getETitle, keyWord).or().
                    like(Strings.isNotBlank(keyWord), Exam::getCourse, keyWord).or()
                    .like(Strings.isNotBlank(keyWord), Exam::getVid, keyWord));
//            lqw.like(Strings.isNotBlank(keyWord), Exam::getETitle, keyWord);
//            lqw.or();
//            lqw.like(Strings.isNotBlank(keyWord), Exam::getCourse, keyWord);
//            lqw.or();
//            lqw.like(Strings.isNotBlank(keyWord), Exam::getVid, keyWord);
        }
        examMapper.selectPage(page, lqw);
        //如果当前页码大于总页码，那么重新执行查询操作，使用最大页码值作为当前页码值
        if ( currentPage > page.getPages() )
        {
            page.setCurrent(page.getPages());
            examMapper.selectPage(page, lqw);
        }
        for ( Exam record : page.getRecords() )
        {
            if ( record.getEDelete().equals("1") )
            {
                record.setEDelete("未删除");
            }
            else
            {
                record.setEDelete("已删除");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", currentPage);
        map.put("totalPage", page.getPages());
        map.put("total", page.getTotal());
        map.put("exams", page.getRecords());
        return new Result(true, map);
    }


    /**
     * 查询单个试卷，展示试题
     */
    @Override
    public Result findExamById ( String eid )
    {
        Exam exam = examMapper.selectById(eid);
        List<String> optionIds = new ArrayList<>();
        List<String> judgeIds = new ArrayList<>();
        List<String> gapIds = new ArrayList<>();
        StringBuffer id = new StringBuffer();
        ExamDTO examDTO = BeanUtil.copyProperties(exam, ExamDTO.class);
        if ( Strings.isNotBlank(exam.getOptionIds()) )
        {
            char[] optionIdArray = exam.getOptionIds().toCharArray();
            for ( char c : optionIdArray )
            {
                if ( c == '-' )
                {
                    optionIds.add(id.toString());
                    id.setLength(0);
                }
                else
                {
                    id.append(c);
                }
            }
        }
        id.setLength(0);
        if ( Strings.isNotBlank(exam.getJudgeIds()) )
        {
            char[] judgeIdArray = exam.getJudgeIds().toCharArray();
            for ( char c : judgeIdArray )
            {
                if ( c == '-' )
                {
                    judgeIds.add(id.toString());
                    id.setLength(0);
                }
                else
                {
                    id.append(c);
                }
            }
        }
        id.setLength(0);
        if ( Strings.isNotBlank(exam.getGapIds()) )
        {
            char[] gapIdArray = exam.getGapIds().toCharArray();
            for ( char c : gapIdArray )
            {
                if ( c == '-' )
                {
                    gapIds.add(id.toString());
                    id.setLength(0);
                }
                else
                {
                    id.append(c);
                }
            }
        }

        if ( CollUtil.isNotEmpty(optionIds) )
        {
            List<Option> options = optionMapper.selectBatchIds(optionIds);
            examDTO.setOptions(options);
        }
        if ( CollUtil.isNotEmpty(judgeIds) )
        {
            List<Judge> judges = judgeMapper.selectBatchIds(judgeIds);
            examDTO.setJudges(judges);
        }
        if ( CollUtil.isNotEmpty(gapIds) )
        {
            List<Gap> gaps = gapMapper.selectBatchIds(gapIds);
            examDTO.setGaps(gaps);
        }
        return new Result(true, examDTO);
    }

    /**
     * 更新试卷
     */
    @Override
    public Boolean updateExam ( Exam exam )
    {
        exam.setUpdateTime(LocalDateTime.now());
        return examMapper.updateById(exam) > 0;
    }


    /**
     * 删除
     */
    @Override
    public Boolean deleteExam ( String eid )
    {
        return examMapper.deleteById(eid) > 0;
    }

    @Override
    public Boolean checkExam ( String eid, String state )
    {
        UserDTO userDTO = UserHolder.getUser();
        Exam exam = examMapper.selectById(eid);
        exam.setAuditUid(userDTO.getUid());
        exam.setAuditUsername(userDTO.getUsername());
        exam.setAuditTime(LocalDateTime.now());
        exam.setEState(state);
        return examMapper.updateById(exam) > 0;
    }

}




