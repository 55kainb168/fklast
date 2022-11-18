package com.example.fklast.controller.uitlController;

import cn.hutool.core.util.ObjectUtil;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.utils.Result;
import com.example.fklast.utils.component.UploadFile;
import com.example.fklast.utils.UserHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author 卢本伟牛逼
 */
@RestController
@RequestMapping("/upload")
public class UploadFileController
{
    @Resource
    private UploadFile uploadFile;

    /**
     * 上传头像
     * dir指明图片用途（头像，文章图片等）
     */
    @PostMapping ("/file")
    public Result uploadFile ( @RequestBody (required = false) MultipartFile multipartFile, @RequestParam String dir ) throws IOException
    {
        UserDTO userDTO = UserHolder.getUser();
        if ( ObjectUtil.isEmpty(multipartFile) )
        {
            return new Result(false, "文件为空");
        }
        return new Result(true, uploadFile.uploadFile(multipartFile, dir, userDTO), "上传成功了捏");
    }


}
