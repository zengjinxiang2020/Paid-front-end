/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.activity.web.controller;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.http.HttpUtil;
import co.yixiang.annotation.AnonymousAccess;
import co.yixiang.aop.log.Log;
import co.yixiang.common.api.ApiResult;
import co.yixiang.common.web.controller.BaseController;
import co.yixiang.modules.activity.entity.YxStorePink;
import co.yixiang.modules.activity.service.YxStoreCombinationService;
import co.yixiang.modules.activity.service.YxStorePinkService;
import co.yixiang.modules.activity.web.vo.YxStoreCombinationQueryVo;
import co.yixiang.modules.shop.service.YxSystemConfigService;
import co.yixiang.modules.user.entity.YxSystemAttachment;
import co.yixiang.modules.user.service.YxSystemAttachmentService;
import co.yixiang.modules.user.service.YxUserService;
import co.yixiang.modules.user.web.vo.YxUserQueryVo;
import co.yixiang.utils.SecurityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 拼团前端控制器
 * </p>
 *
 * @author hupeng
 * @since 2019-11-19
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "拼团", tags = "拼团", description = "拼团")
public class StoreCombinationController extends BaseController {

    private final YxStoreCombinationService storeCombinationService;
    private final YxStorePinkService storePinkService;
    private final YxSystemConfigService systemConfigService;
    private final YxUserService yxUserService;
    private final YxSystemAttachmentService systemAttachmentService;

    @Value("${file.path}")
    private String path;


    /**
     * 拼团产品列表
     */
    @AnonymousAccess
    @GetMapping("/combination/list")
    @ApiOperation(value = "拼团产品列表",notes = "拼团产品列表",response = YxStoreCombinationQueryVo.class)
    public ApiResult<Object> getList(@RequestParam(value = "page",defaultValue = "1") int page,
                                     @RequestParam(value = "limit",defaultValue = "10") int limit){

        return ApiResult.ok(storeCombinationService.getList(page,limit));
    }

    /**
     * 拼团产品详情
     */
    @Log(value = "查看拼团产品",type = 1)
    @GetMapping("/combination/detail/{id}")
    @ApiOperation(value = "拼团产品详情",notes = "拼团产品详情",response = YxStoreCombinationQueryVo.class)
    public ApiResult<Object> detail(@PathVariable Integer id){
        if(ObjectUtil.isNull(id)) return ApiResult.fail("参数有误");
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(storeCombinationService.getDetail(id,uid));
    }

    /**
     * 拼团明细
     */
    @GetMapping("/combination/pink/{id}")
    @ApiOperation(value = "拼团明细",notes = "拼团明细")
    public ApiResult<Object> pink(@PathVariable Integer id){
        if(ObjectUtil.isNull(id)) return ApiResult.fail("参数有误");
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(storePinkService.pinkInfo(id,uid));
    }

    /**
     * 拼团海报
     */
    @PostMapping("/combination/poster")
    @ApiOperation(value = "拼团海报",notes = "拼团海报")
    public ApiResult<Object> poster(@RequestBody String jsonStr){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Integer pinkId = jsonObject.getInteger("id");
        if(ObjectUtil.isNull(pinkId)) return ApiResult.fail("参数有误");

        String siteUrl = systemConfigService.getData("site_url");
        if(StrUtil.isEmpty(siteUrl)){
            return ApiResult.fail("未配置h5地址");
        }
        String apiUrl = systemConfigService.getData("api_url");
        if(StrUtil.isEmpty(apiUrl)){
            return ApiResult.fail("未配置api地址");
        }
        YxStorePink storePink = storePinkService.getPinkUserOne(pinkId);
        if(ObjectUtil.isNull(storePink)) return ApiResult.fail("拼团不存在");
        YxStoreCombinationQueryVo storeCombination = storeCombinationService.getCombinationT(storePink.getCid());
        if(ObjectUtil.isNull(storeCombination)) return ApiResult.fail("拼团产品不存在");

        int uid = SecurityUtils.getUserId().intValue();
        YxUserQueryVo userInfo = yxUserService.getYxUserById(uid);
        String userType = userInfo.getUserType();
        if(!userType.equals("routine")) userType = "H5";
        String name = pinkId+"_"+uid + "_"+userType+"_pink_share_wap.jpg";
        YxSystemAttachment attachment = systemAttachmentService.getInfo(name);
        String fileDir = path+"qrcode"+ File.separator;
        String qrcodeUrl = "";
        if(ObjectUtil.isNull(attachment)){
            //生成二维码
            //String fileDir = path+"qrcode"+File.separator;
            File file = FileUtil.mkdir(new File(fileDir));
            if(userType.equals("routine")){
                siteUrl = siteUrl+"/pink/";
                QrCodeUtil.generate(siteUrl+"?pinkId="+pinkId+"&spread="+uid, 180, 180,
                        FileUtil.file(fileDir+name));
            }else{
                QrCodeUtil.generate(siteUrl+"/activity/group_rule/"+pinkId+"?spread="+uid, 180, 180,
                        FileUtil.file(fileDir+name));
            }


            systemAttachmentService.attachmentAdd(name,String.valueOf(FileUtil.size(file)),
                    fileDir+name,"qrcode/"+name);

            qrcodeUrl = fileDir+name;
        }else{
            qrcodeUrl = attachment.getAttDir();
        }

        String spreadPicName = pinkId+"_"+uid + "_"+userType+"_pink_user_spread.jpg";
        String spreadPicPath = fileDir+spreadPicName;

        YxSystemAttachment attachmentT = systemAttachmentService.getInfo(spreadPicName);
        String spreadUrl = "";
        InputStream stream =  getClass().getClassLoader().getResourceAsStream("poster.jpg");
        InputStream streamT =  getClass().getClassLoader()
                .getResourceAsStream("simsunb.ttf");
        File newFile = new File("poster.jpg");
        File newFileT = new File("simsunb.ttf");
        try {
            FileUtils.copyInputStreamToFile(stream, newFile);
            FileUtils.copyInputStreamToFile(streamT, newFileT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ObjectUtil.isNull(attachmentT)){
            try {

                //第一步标题
                Font font =  Font.createFont(Font.TRUETYPE_FONT, newFileT);
                Font f= font.deriveFont(Font.PLAIN,40);
                //font.
                ImgUtil.pressText(//
                        newFile,
                        FileUtil.file(spreadPicPath),
                        storeCombination.getTitle(),
                        Color.BLACK,
                        f, //字体
                        0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        -480, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
                );

                Font f2= font.deriveFont(Font.PLAIN,45);
                //第2步价格
                ImgUtil.pressText(//
                        FileUtil.file(spreadPicPath),
                        FileUtil.file(spreadPicPath),
                        storePink.getTotalPrice().toString(),
                        Color.RED,
                        f2, //字体
                        -160, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        -380, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
                );

                Font f3= font.deriveFont(Font.PLAIN,30);
                //第3步几人团
                ImgUtil.pressText(//
                        FileUtil.file(spreadPicPath),
                        FileUtil.file(spreadPicPath),
                        storePink.getPeople()+"人团",
                        Color.WHITE,
                        f3, //字体
                        90, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        -385, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
                );

                //第4步介绍
                String pro = "原价￥"+storeCombination.getProductPrice()+" 还差"
                        +storePinkService.surplusPeople(storePink)+"人拼团成功";
                ImgUtil.pressText(//
                        FileUtil.file(spreadPicPath),
                        FileUtil.file(spreadPicPath),
                        pro,
                        Color.BLACK,
                        f3, //字体
                        -50, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        -300, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
                );

                //第5步商品图片
                //下载图片
                String picImage = fileDir+pinkId+"_pink_image.jpg";
                HttpUtil.downloadFile(storeCombination.getImage(),
                        FileUtil.file(picImage));

                ImgUtil.scale(
                        FileUtil.file(picImage),
                        FileUtil.file(picImage),
                        0.5f//缩放比例
                );

                ImgUtil.pressImage(
                        FileUtil.file(spreadPicPath),
                        FileUtil.file(spreadPicPath),
                        ImgUtil.read(FileUtil.file(picImage)), //水印图片
                        0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        -80, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f
                );

                ImgUtil.pressImage(
                        FileUtil.file(spreadPicPath),
                        FileUtil.file(spreadPicPath),
                        ImgUtil.read(FileUtil.file(qrcodeUrl)), //水印图片
                        0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        390, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f
                );

                systemAttachmentService.attachmentAdd(spreadPicName,
                        String.valueOf(FileUtil.size(new File(spreadPicPath))),
                        spreadPicPath,"qrcode/"+spreadPicName);

                spreadUrl = apiUrl + "/api/file/qrcode/"+spreadPicName;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            spreadUrl = apiUrl + "/api/file/" + attachmentT.getSattDir();
        }
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("url",spreadUrl);
        return ApiResult.ok(map);
    }

    /**
     * 取消开团
     */
    @Log(value = "取消开团",type = 1)
    @PostMapping("/combination/remove")
    @ApiOperation(value = "取消开团",notes = "取消开团")
    public ApiResult<Object> remove(@RequestBody String jsonStr){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Integer pinkId = jsonObject.getInteger("id");
        Integer cId = jsonObject.getInteger("cid");
        if(ObjectUtil.isNull(pinkId) || ObjectUtil.isNull(cId)) return ApiResult.fail("参数有误");

        int uid = SecurityUtils.getUserId().intValue();
        storePinkService.removePink(uid,cId,pinkId);

        return ApiResult.ok("ok");
    }




}

