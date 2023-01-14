package com.example.fklast.service.impl;

import com.example.fklast.domain.Option;
import com.example.fklast.service.OptionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class OptionServiceImplTest
{

    @Resource
    private OptionService optionService;

    @Test
    void insertOption ()
    {
        Option option = new Option();
        option.setOptionContent("dwsdasd");
        option.setOptionLabel("");
        option.setOptionOne("1");
        option.setOptionTwo("2");
        option.setOptionThree("3");
        option.setOptionFour("4");
        option.setOptionAnswer("A");
        option.setOptionAnalysis("asdasd");


    }

    @Test
    void deleteOption ()
    {
        System.out.println(optionService.deleteOption("10b1f3dca8b74895b97b5ed90e4d8386"));
    }

    @Test
    void findOptionByPage ()
    {
        System.out.println(optionService.findOptionByPage(6, 6, "", "", ""));
    }

}