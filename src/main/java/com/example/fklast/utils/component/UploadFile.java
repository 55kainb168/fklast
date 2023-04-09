package com.example.fklast.utils.component;

import cn.hutool.core.lang.UUID;
import com.example.fklast.dto.UserDTO;
import com.example.fklast.mapper.UserMapper;
import com.example.fklast.mapper.VideoMapper;
import com.example.fklast.utils.UserHolder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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


    @Value ("${file.staticPath}")
    private String staticPath;

//
//    /**
//     * 上传头像图片
//     * 回滚防止暴毙
//     */
//    @Transactional (rollbackFor = Exception.class)
//    public Map<String, Object> uploadFile ( MultipartFile multipartFile, String dir, UserDTO userDTO ) throws Exception
//    {
//        //获取上传的文件或图片
//        String originalFilename = multipartFile.getOriginalFilename();
//        //图片文件名后缀
//        assert originalFilename != null;
//        String imgSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        //判断是否为视频文件后缀
//        if (! (imgSuffix.equals(".mp4") || imgSuffix.equals(".avi") || imgSuffix.equals(".flv") || imgSuffix.equals(".wmv") || imgSuffix.equals(".mov")
//                || imgSuffix.equals(".webm") || imgSuffix.equals(".mpeg4") || imgSuffix.equals(".ts")
//                || imgSuffix.equals(".mpg") || imgSuffix.equals(".rm") || imgSuffix.equals(".rmvb")
//                || imgSuffix.equals(".m4v") || imgSuffix.equals(".mkv")) )
//        {
//            return null;
//        }
//        //生成的唯一文件名：使用UUID统一命名
//        String newFileName = UUID.randomUUID().toString(true) + imgSuffix;
//        //使用用户id生成目录 ，ID目录
//        String uid = userDTO.getUid();
//        //磁盘目录，指定文件上传目录
//        String serverPath = uploadFolder;
//        //生成目录:E:/resource/用户id/文件用途（头像、文章图片）
//        File targetPath = new File(serverPath + uid, dir);
//        //目录不存在就创建
//        if ( ! targetPath.exists() )
//        {
//            boolean mkdirs = targetPath.mkdirs();
//        }
//        //指定文件上传之后的服务器的完整的文件名
//        File targetFileName = new File(targetPath, newFileName);
//        //上传文件
//        multipartFile.transferTo(targetFileName);
//
//        Map<String, Object> map = new HashMap<>();
//
//        //如果上传的文件是视频则截取一下封面
//        if ( dir.equals("vUrl") )
//        {
//            String coverUrl = getPicFromVideo(targetFileName, newFileName);
//            map.put("vCover", coverUrl);
//        }
//
//        //拼接成可访问路径返回页面，URL
//        String filename = uid + "/" + dir + "/" + newFileName;
//        map.put("url", staticPath + filename);
//        map.put("size", multipartFile.getSize());
//        map.put("imgSuffix", imgSuffix);
//        map.put("filename", filename);
//        map.put("rPath", uid + "/" + newFileName);
//        return map;
//    }

    /**
     * 上传头像图片
     * 回滚防止暴毙
     */
    @Transactional (rollbackFor = Exception.class)
    public Map<String, Object> uploadFile ( HttpServletRequest request, HttpServletResponse response, String dir ) throws Exception
    {
        dir = "vUrl";
        response.setCharacterEncoding("utf-8");
        UserDTO userDTO = UserHolder.getUser();
        String uid = userDTO.getUid();
        String temp = "temp";
        String tempPath = uploadFolder + uid + "/" + temp;
        Integer schunk = null;
        Integer schunks = null;
        String name = null;
        BufferedOutputStream os = null;
        Map<String, Object> map = new HashMap<>();
        try
        {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024);
            factory.setRepository(new File(tempPath));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(10L * 1024L * 1024L * 1024L);
            upload.setSizeMax(5L * 1024L * 1024L * 1024L);
            List<FileItem> items = upload.parseRequest(request);
            for ( FileItem item : items )
            {
                if ( item.isFormField() )
                {
                    if ( "chunk".equals(item.getFieldName()) )
                    {
                        schunk = Integer.parseInt(item.getString("utf-8"));
                    }
                    if ( "chunks".equals(item.getFieldName()) )
                    {
                        schunks = Integer.parseInt(item.getString("utf-8"));
                    }
                    if ( "name".equals(item.getFieldName()) )
                    {
                        name = item.getString("utf-8");
                    }
                }
            }
            for ( FileItem item : items )
            {
                //判断文件对象
                if ( ! item.isFormField() )
                {
                    String tempFileName = name;
                    if ( Strings.isNotBlank(name) )
                    {
                        if ( schunk != null )
                        {
                            tempFileName = schunk + "-" + name;
                        }
                        File tempFile = new File(tempPath, tempFileName);
                        //断点续传
                        if ( ! tempFile.exists() )
                        {
                            item.write(tempFile);
                        }
                    }
                }
            }
            if ( schunk != null && schunk.intValue() == schunks.intValue() - 1 )
            {
                //需要写入的最终文件
                String imgSuffix = name.substring(name.lastIndexOf("."));
                //判断是否为视频文件后缀
                if ( ! ( imgSuffix.equals(".mp4") || imgSuffix.equals(".avi") || imgSuffix.equals(".flv") || imgSuffix.equals(".wmv") || imgSuffix.equals(".mov")
                        || imgSuffix.equals(".webm") || imgSuffix.equals(".mpeg4") || imgSuffix.equals(".ts")
                        || imgSuffix.equals(".mpg") || imgSuffix.equals(".rm") || imgSuffix.equals(".rmvb")
                        || imgSuffix.equals(".m4v") || imgSuffix.equals(".mkv") ) )
                {
                    return null;
                }
                //生成的唯一文件名：使用UUID统一命名
                String newFileName = UUID.randomUUID().toString(true) + imgSuffix;
                //使用用户id生成目录 ，ID目录
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
                String filename = uid + "/" + dir + "/" + newFileName;
                map.put("url", staticPath + filename);
                map.put("rPath", uid + "/" + newFileName);
                map.put("filename", filename);
                map.put("imgSuffix", imgSuffix);
                os = new BufferedOutputStream(new FileOutputStream(targetFileName));
                for ( int i = 0; i < schunks; i++ )
                {
                    File tempFile = new File(tempPath, i + "-" + name);
                    while ( ! tempFile.exists() )
                    {
                        Thread.sleep(100);
                    }
                    byte[] bytes = FileUtils.readFileToByteArray(tempFile);
                    os.write(bytes);
                    os.flush();
                    tempFile.delete();
                }
                os.flush();
                //如果上传的文件是视频则截取一下封面
                String coverUrl = getPicFromVideo(targetFileName, newFileName);
                map.put("vCover", coverUrl);
            }
            //拼接成可访问路径返回页面，URL
            return map;
        }
        finally
        {
            try
            {
                if ( os != null )
                {
                    os.close();
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
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



