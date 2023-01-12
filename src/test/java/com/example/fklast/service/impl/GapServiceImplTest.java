package com.example.fklast.service.impl;

import com.example.fklast.domain.Gap;
import com.example.fklast.service.GapService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class GapServiceImplTest
{

    @Resource
    private GapService gapService;

    @Test
    void insertGap ()
    {
        Gap gap = new Gap();
        gap.setVid("43ea7ecd032a44169926717666df0683");
        gap.setGapContent("qweqwczx");
        gap.setGapLabel("dqw");
        gap.setGapAnswer("sadasd");
        System.out.println(gapService.insertGap(gap));
    }

    @Test
    void deleteGap ()
    {
        System.out.println(gapService.deleteGap("004885cd1ef7407e8c50d045d26ddd8f"));
    }

    @Test
    void findGapByPage ()
    {
        System.out.println(gapService.findGapByPage(6, 6, "", "", ""));
    }
}