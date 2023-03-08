package com.example.fklast.controller.uitlController;

import com.example.fklast.utils.Result;
import com.example.fklast.utils.component.UploadFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 卢本伟牛逼
 */
@RestController
@RequestMapping ("/upload")
public class UploadFileController
{
    @Resource
    private UploadFile uploadFile;

//    /**
//     * 上传头像
//     * dir指明图片用途（头像，文章图片等）
//     */
//    @PostMapping ("/file")
//    public Result uploadFile ( @RequestBody (required = false) MultipartFile multipartFile, @RequestParam String dir ) throws Exception
//    {
//        UserDTO userDTO = UserHolder.getUser();
//        if ( ObjectUtil.isEmpty(multipartFile) )
//        {
//            return new Result(false, "文件为空");
//        }
//        return new Result(true, uploadFile.uploadFile(multipartFile, dir, userDTO), "上传成功了捏");
//    }

//    /**
//     * 视频截帧
//     */
//    @PostMapping("/img")
//    public Result videoImg(@RequestParam String videoFileName) throws Exception
//    {
//        return new Result(true,uploadFile.getPicFromVideo(videoFileName));
//    }
//

    /**
     * 视频
     */
    @PostMapping ("/file")
    public Result uploadFile ( HttpServletRequest request, HttpServletResponse response, @RequestParam (required = false) String dir ) throws Exception
    {
        return new Result(true, uploadFile.uploadFile(request, response, dir));
    }
}
