package com.example.fklast.utils.component;

import cn.hutool.core.lang.UUID;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.mapper.UserMapper;
import com.example.fklast.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 卢本伟牛逼
 */
@Component
public class UploadFile
{

    @Resource
    private UserMapper userMapper;

    @Resource
    private VideoMapper videoMapper;

    @Value ("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.patternPath}")
    private String patternPath;

    @Value("${file.staticPath}")
    private String staticPath;


    /**
     * 上传头像图片
     * 回滚防止暴毙
     */
    @Transactional (rollbackFor = Exception.class)
    public Map<String, Object> uploadFile ( MultipartFile multipartFile, String dir, UserDTO userDTO ) throws IOException
    {
        //获取上传的文件或图片
        String originalFilename = multipartFile.getOriginalFilename();
        //图片文件名后缀
        assert originalFilename != null;
        String imgSuffix = originalFilename.substring(originalFilename.indexOf("."));
        //生成的唯一文件名：使用UUID统一命名
        String newFileName = UUID.randomUUID().toString(true) + imgSuffix;
        //使用用户id生成目录 ，ID目录
        String uid = userDTO.getUid();
        //磁盘目录，指定文件上传目录
        String serverPath = uploadFolder;
        //生成目录:E:/resource/用户id/文件用途（头像、文章图片）
        File targetPath = new File(serverPath + uid, dir);
        //目录不存在就创建
        if ( ! targetPath.exists() )
        {
            boolean mkdirs = targetPath.mkdirs();
        }
        //指定文件上传之后的服务器的完整的文件名
        File targetFileName = new File(targetPath, newFileName);
        //上传文件
        multipartFile.transferTo(targetFileName);
        //拼接成可访问路径返回页面，URL
        String filename = uid + "/" + dir + "/" + newFileName;
        Map<String, Object> map = new HashMap<>();
        map.put("url", staticPath + filename);
        map.put("size", multipartFile.getSize());
        map.put("imgSuffix", imgSuffix);
        map.put("filename", filename);
        map.put("rPath", uid + "/" + newFileName);
        return map;
    }


}



