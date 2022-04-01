package com.system.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.common.util.DateUtils;
import com.system.common.util.HttpUtil;
import com.system.common.util.TokenUtil;
import com.system.service.ChatMessageService;
import com.system.service.EaseMobService;


/**
 * 拉取环信的服务
 * @author 555
 *
 */
@Service
public class EaseMobServiceImpl implements EaseMobService{
 
 
    @Autowired
    private ChatMessageService chatMessageService;
    
    
    @Override
    public void saveChatMessages() throws Exception {
        //下载文件保存路径
        String filePath = "E:/data/";
        //未加时间戳的请求地址
        //OrgInfo.ORG_NAME  环信org_name  OrgInfo.APP_NAME 环信app_name
        String requestUrl = "http://a1.easemob.com/"+ TokenUtil.Orgname+ "/" + TokenUtil.appname + "/chatmessages/";
        //获取前一天内的时间list
        List<String> hourList = DateUtils.getOneDayHourList(DateUtils.getBeforeDayDate(new Date(), 1));
        //环信token 自己写一个工具类获取token
        String token = TokenUtil.getToken();
        //获取下载地址
        for(String hour: hourList){
            try {
                String downloadUrl = HttpUtil.getEasemobChatMessageDownloadUrl(requestUrl + hour, token);
                if(!"fail".equals(downloadUrl)){
                    //下载压缩文件到指定文件夹
                    String fileName = hour + ".gz";
                    String downLoadResult = HttpUtil.downloadFileByUrls(downloadUrl, fileName,filePath);
                    //下载成功进行解压文件和读取文件
                    if("ok".equals(downLoadResult)){
                        //解压文件
                        String outPutFilePath = unZipFile(filePath + fileName);
                        //读取文件
                        if(outPutFilePath.length() >0) {
                            String content = readFile2String(outPutFilePath);
                            //处理文本内容,写入实体
                            if(content.length() > 0) {
                                chatMessageService.handleContent(content);
                            }
                        }
                    }
                }
                //延时执行,环信下载接口有限流
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    
    

 
    /**
     * 读取文件内容
    **/
    private String readFile2String(String outPutFilePath) {
        String content = "";
        String encoding = "UTF-8";
        File file = new File(outPutFilePath);
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            content = new String(fileContent, encoding).trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
 
 
    /**
     * 解压文件并返回解压后的文件
    **/
    private String unZipFile(String filePath) {
        //解压gz压缩包
        String ouPutFile = "";
        try {
            //建立gzip压缩文件输入流
            FileInputStream fIn = new FileInputStream(filePath);
            //建立gzip解压工作流
            GZIPInputStream gzIn = new GZIPInputStream(fIn);
            //建立解压文件输出流
            ouPutFile = filePath.substring(0,filePath.lastIndexOf('.'));
            FileOutputStream fOut = new FileOutputStream(ouPutFile);
            int num;
            byte[] buf=new byte[1024];
 
 
            while ((num = gzIn.read(buf,0,buf.length)) != -1)
            {
                fOut.write(buf,0,num);
            }
            gzIn.close();
            fOut.close();
            fIn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return ouPutFile;
    }
}