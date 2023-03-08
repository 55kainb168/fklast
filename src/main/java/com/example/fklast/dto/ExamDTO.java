package com.example.fklast.dto;

import com.example.fklast.domain.Gap;
import com.example.fklast.domain.Judge;
import com.example.fklast.domain.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO implements Serializable
{
    /**
     * 试卷id
     */
    private String eid;

    /**
     * 跟随的视频id·
     */
    private String vid;

    /**
     * 上传人id
     */
    private String eUid;

    /**
     * 上传人用户名
     */
    private String eUsername;

    /**
     * 习题组标题
     */
    private String eTitle;

    /**
     * 课程名字
     */
    private String course;

    /**
     * 选择题数
     */
    private Integer optionCount;

    /**
     * 所有选择题的id
     */
    private String optionIds;

    /**
     * 所有选择题
     */
    private List<Option> options;

    /**
     * 判断题数
     */
    private Integer judgeCount;

    /**
     * 所有判断题目id
     */
    private String judgeIds;

    /**
     * 所有判断题目
     */
    private List<Judge> judges;

    /**
     * 大题数量
     */
    private Integer gapCount;

    /**
     * 所有大题id
     */
    private String gapIds;

    /**
     * 所有大题
     */
    private List<Gap> gaps;

    /**
     * 审核状态 (待审核，通过)
     */
    private String eState;

    /**
     * 是否删除 （1正常，0删除）
     */
    private String eDelete;

}
