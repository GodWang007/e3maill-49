package cn.e3mall.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 生成静态页面测试
 * @author 11734
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
@Controller
public class HtmlGenController {
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@RequestMapping("/htmlGen")
	@ResponseBody
	public String genHtml() throws Exception{
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("hello.ftl");
		//创建一个数据集
		Map data=new HashMap<>();
		data.put("hello", "hello spring freemarker");
		//输出文件目录  及 文件名
		Writer out=new FileWriter(new File("D:/temp/freemarker/out/hello.html"));
		//生成文件
		template.process(data, out);
		// 8、关闭流
		out.close();
		return "ok";
		
	}
	
}
