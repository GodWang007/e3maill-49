package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

/**
 * 图片上传功能的实现
 * @author 11734
 *
 */
@Controller
public class PictureController {

	
	@Value("${image.server.url}")
	private String imageServerURL;
	
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String  fileupload(MultipartFile uploadFile){
		try {
			//获取原始文件
			String originalFilename = uploadFile.getOriginalFilename();
			//1.获取文件的扩展名
			String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
			//2.创建一个FastDFS的客户端
			FastDFSClient fastDFSClient=new FastDFSClient("classpath:conf/client.conf");
			//3.执行上传处理
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//4.拼接返回的url和ip地址,拼装成完整的url
			url=imageServerURL+url;
			//5.创建一个map集合
			Map map =new HashMap<>();
			//设置属性
			map.put("error", 0);
			map.put("url", url);
			
			//6.返回map
			return JsonUtils.objectToJson(map);
		} catch (Exception e) {
			
			e.printStackTrace();
			//5.创建一个map集合
			Map map =new HashMap<>();
			//设置属性
			map.put("error", 1);
			map.put("message", "文件上传失败");
			
			//6.返回map
			return JsonUtils.objectToJson(map);
		}
		
	}
	
	
}
