package com.example.fklast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.fklast.domain.Gap;
import com.example.fklast.utils.Result;

/**
 * @author 卢本伟牛逼
 * @description 针对表【fk_gap】的数据库操作Service
 * @createDate 2023-01-12 16:35:16
 */
public interface GapService extends IService<Gap>
{

    /**
     * 添加大题
     */
    Result insertGap ( Gap gap );

    /**
     * 逻辑删除大题
     */
    Boolean deleteGap ( String gid );

    /**
     * 分页展示大题
     */
    Result findGapByPage ( int currentPage, int pageSize, String uid, String keyWord, String sort );


}
