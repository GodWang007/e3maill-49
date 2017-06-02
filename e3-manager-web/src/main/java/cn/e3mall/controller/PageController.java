package cn.e3mall.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转处理
 * @author 11734
 * 有网络传输就要实现序列化接口
 */
@Controller
public class PageController implements Serializable {
	
	@RequestMapping("/")
	public String showIndex(){
		return "index";
	}
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
}
