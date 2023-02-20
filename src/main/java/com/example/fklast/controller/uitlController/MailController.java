package com.example.fklast.controller.uitlController;

import com.example.fklast.service.SmsCodeService;
import com.example.fklast.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author 卢本伟牛逼
 */
@RestController
@RequestMapping ("/mail")
public class MailController
{
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private SmsCodeService smsCodeService;

    @Value ("${fromMail.sender}")
    private String sender;

    @GetMapping
    public Result simpleMail ( @RequestParam String email ) throws Exception
    {
        //做正则检查
        Pattern pattern = compile("^\\s*\\w+(?:\\.?[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        if ( ! pattern.matcher(email).matches() )
        {
            return new Result(false, "请输入正确的邮箱");
        }
        String code = smsCodeService.sendCodeToSms(email);
        //内容
        String content = "<!DOCTYPE html>\n" + "<html lang=\"zh-cmn-hans\">\n" + "  <head>\n" + "    <style>\n" + "      body,\n" + "      html {\n" + "        margin: 0;\n" + "        padding: 0;\n" + "      }\n" + "      * {\n" + "        box-sizing: border-box;\n" + "      }\n" + "      body {\n" + "        display: flex;\n" + "        justify-content: center;\n" + "        align-items: center;\n" + "        min-height: 100vh;\n" + "      }\n" + "      .container {\n" + "        display: flex;\n" + "        justify-content: center;\n" + "        align-items: center;\n" + "        position: relative;\n" + "        width: 340px;\n" + "        height: 250px;\n" + "      }\n" + "      .container .bg-color {\n" + "        position: absolute;\n" + "        width: 100%;\n" + "        height: 100%;\n" + "        filter: blur(70px);\n" + "        opacity: 0.82;\n" + "      }\n" + "      .container .bg-color span {\n" + "        position: absolute;\n" + "        top: 50%;\n" + "        left: 15%;\n" + "        width: 300px;\n" + "        height: 300px;\n" + "        border-radius: 50%;\n" + "        transform-origin: 125px 0px;\n" + "      }\n" + "      .container .bg-color span:nth-of-type(1) {\n" + "        background-color: #ec4e8a;\n" + "        box-shadow: 0 0 100px #ec4e8a;\n" + "        transform: rotate(0);\n" + "      }\n" + "      .container .bg-color span:nth-of-type(2) {\n" + "        background-color: #eed045;\n" + "        box-shadow: 0 0 100px #eed045;\n" + "        transform: rotate(90deg);\n" + "      }\n" + "      .container .bg-color span:nth-of-type(3) {\n" + "        background-color: #96c24e;\n" + "        box-shadow: 0 0 100px #96c24e;\n" + "        transform: rotate(180deg);\n" + "      }\n" + "      .container .bg-color span:nth-of-type(4) {\n" + "        background-color: #29b7cb;\n" + "        box-shadow: 0 0 100px #29b7cb;\n" + "        transform: rotate(270deg);\n" + "      }\n" + "      .container .message {\n" + "        width: 100%;\n" + "        height: 100%;\n" + "        padding: 12px;\n" + "        font-size: 20px;\n" + "        font-weight: 600;\n" + "        color: #303133;\n" + "        background-color: rgba(255, 255, 255, 0.5);\n" + "        border-radius: 10px;\n" + "        z-index: 1;\n" + "      }\n" + "      .container .message #code {\n" + "        margin: 48px 0;\n" + "        text-align: center;\n" + "        font-size: 26px;\n" + "        letter-spacing: 2px;\n" + "      }\n" + "      #tip {\n" + "        font-size: 18px;\n" + "        color: #606266;\n" + "        font-style: italic;\n" + "      }\n" + "    </style>\n" + "  </head>\n" + "  <body>\n" + "    <div class=\"container\">\n" + "      <div class=\"message\">\n" + "        <span>邮箱验证码：</span>\n" + "        <p id=\"code\">" + code + "</p>\n" + "        <span id=\"tip\">提示: 请勿将验证码泄露给他人</span>\n" + "      </div>\n" + "      <div class=\"bg-color\">\n" + "        <span></span>\n" + "        <span></span>\n" + "        <span></span>\n" + "        <span></span>\n" + "      </div>\n" + "    </div>\n" + "\n" + "    <script></script>\n" + "  </body>\n" + "</html>\n";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(email);
        //主题
        helper.setSubject("验证码(计算机组成原理课程培训学习平台)");
        //正文
        helper.setText(content, true);
        javaMailSender.send(message);
        return new Result(true, "邮件已经发出去了捏^_^!");
    }
}
