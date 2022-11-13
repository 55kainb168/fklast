package com.example.fklast.config;

import com.example.fklast.utils.RsaUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class RsaKeyConfigTest
{
    private String pubKeyFile = "E:\\fuckingkeys\\id_key_rsa.pub";
    private String priKeyFile = "E:\\fuckingkeys\\id_key_rsa";

    @Test
    public void  generateKey() throws  Exception
    {
        RsaUtils.generateKey(pubKeyFile,priKeyFile,"lbwnb",2048);
    }

}