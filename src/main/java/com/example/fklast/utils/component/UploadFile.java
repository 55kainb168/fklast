package com.example.fklast.utils.component;

import cn.hutool.core.lang.UUID;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.mapper.UserMapper;
import com.example.fklast.mapper.VideoMapper;
import com.example.fklast.utils.UserHolder;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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
    public Map<String, Object> uploadFile ( MultipartFile multipartFile, String dir, UserDTO userDTO ) throws Exception
    {
        //获取上传的文件或图片
        String originalFilename = multipartFile.getOriginalFilename();
        //图片文件名后缀
        assert originalFilename != null;
        String imgSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
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

        Map<String, Object> map = new HashMap<>();

        //如果上传的文件是视频则截取一下封面
        if ( dir.equals("vUrl") )
        {
            String coverUrl = getPicFromVideo(targetFileName, newFileName);
            map.put("vCover", coverUrl);
        }

        //拼接成可访问路径返回页面，URL
        String filename = uid + "/" + dir + "/" + newFileName;
        map.put("url", staticPath + filename);
        map.put("size", multipartFile.getSize());
        map.put("imgSuffix", imgSuffix);
        map.put("filename", filename);
        map.put("rPath", uid + "/" + newFileName);
        return map;
    }

    /**
     * 获取视频封面
     * 私有
     */
    private String getPicFromVideo ( File file, String videoFileName ) throws Exception
    {
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
        ff.start();
        int length = ff.getLengthInFrames();
        UserDTO userDTO = UserHolder.getUser();
        String dir = "vCover";
        String videoName = videoFileName.substring(0, videoFileName.lastIndexOf(".mp4"));
        String imgSuffix = ".jpg";
        String coverName = videoName + "-cover" + imgSuffix;
        Frame frame = null;
        for ( int i = 0; i < length; i++ )
        {
            frame = ff.grabFrame();
            if ( i > 5 && frame.image != null )
            {
                break;
            }
        }
        if ( coverName.indexOf('.') != - 1 )
        {
            String[] arr = coverName.split("\\.");
            if ( arr.length >= 2 )
            {
                imgSuffix = arr[1];
            }
        }
        //磁盘目录，指定文件上传目录
        //生成目录:E:/resource/用户id/文件用途（头像、文章图片）
        File targetPath = new File(uploadFolder + userDTO.getUid(), dir);
        //目录不存在就创建
        if ( ! targetPath.exists() )
        {
            boolean mkdirs = targetPath.mkdirs();
        }
        File targetFileName = new File(targetPath, coverName);
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bi = converter.getBufferedImage(frame);
        ImageIO.write(bi, imgSuffix, targetFileName);
        ff.stop();
        return staticPath + userDTO.getUid() + "/" + dir + "/" + targetFileName.getName();
    }
}



