package com.example.fklast.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fklast.domain.Gap;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.mapper.GapMapper;
import com.example.fklast.service.GapService;
import com.example.fklast.utils.Result;
import com.example.fklast.utils.UserHolder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_gap】的数据库操作Service实现
 * @createDate 2023-01-12 16:35:16
 */
@Service
public class GapServiceImpl extends ServiceImpl<GapMapper, Gap> implements GapService
{

    @Resource
    private GapMapper gapMapper;



    @Override
    public Result insertGap ( Gap gap )
    {
        String gid = UUID.randomUUID().toString(true);
        gap.setGid(gid);
        UserDTO userDTO = UserHolder.getUser();
        gap.setUid(userDTO.getUid());
        gap.setCreateTime(LocalDateTime.now());
        gap.setUpdateTime(LocalDateTime.now());
        gap.setGapDelete("1");
        return new Result(gapMapper.insert(gap) > 0, gid, "");
    }

    @Override
    public Boolean deleteGap ( String gid )
    {
        return gapMapper.deleteById(gid) > 0;
    }


    /**
     * 更改题目内容
     */
    @Override
    public Boolean updateGap ( Gap gap )
    {
        gap.setUpdateTime(LocalDateTime.now());
        return gapMapper.updateById(gap) > 0;
    }

    @Override
    public Result findGapByPage ( int currentPage, int pageSize, String uid, String keyWord, String sort )
    {
        LambdaQueryWrapper<Gap> lqw = new LambdaQueryWrapper<>();
        IPage<Gap> page = new Page<>(currentPage, pageSize);
        lqw.eq(Gap::getGapDelete, "1");
        if ( Strings.isNotBlank(uid) )
        {
            lqw.eq(Gap::getUid, uid);
        }
        gapMapper.selectPage(page, lqw);
        //如果当前页码大于总页码，那么重新执行查询操作，使用最大页码值作为当前页码值
        if ( currentPage > page.getPages() )
        {
            page.setCurrent(page.getPages());
            gapMapper.selectPage(page, lqw);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", currentPage);
        map.put("totalPage", page.getPages());
        map.put("total", page.getTotal());
        map.put("Gaps", page.getRecords());
        return new Result(true, map);
    }
}




