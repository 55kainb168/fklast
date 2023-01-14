package com.example.fklast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.fklast.domain.Judge;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_judge】的数据库操作Mapper
 * @createDate 2023-01-12 21:32:57
 * {@code @Entity} com.example.fklast.domain.Judge
 */
@Mapper
public interface JudgeMapper extends BaseMapper<Judge>
{

}




