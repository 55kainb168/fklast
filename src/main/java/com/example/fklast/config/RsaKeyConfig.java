package com.example.fklast.config;

import com.example.fklast.utils.RsaUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;


/**
 * @author 卢本伟牛逼
 */
@Data
@Configuration
@ConfigurationProperties ("rsa.key")
public class RsaKeyConfig
{
    @Value ("${rsa.key.pubKeyFile}")
    private String pubKeyFile;
    @Value("${rsa.key.priKeyFile}")
    private String priKeyFile;
    private PublicKey publicKey;

    private PrivateKey privateKey;

    @PostConstruct
    public void createRsaKey() throws Exception
    {
        publicKey = RsaUtils.getPublicKey(pubKeyFile);
        privateKey = RsaUtils.getPrivateKey(priKeyFile);
    }
}
