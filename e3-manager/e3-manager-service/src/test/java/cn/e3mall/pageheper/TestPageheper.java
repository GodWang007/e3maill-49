package cn.e3mall.pageheper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;;;


public class TestPageheper {

	@Test
	public void testPageheper() throws Exception{
		//设置分页信息
		PageHelper.startPage(1,10);
		//执行查询
		//初始化spring容器
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		
		//从容器中获得mapper代理对象
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		//执行查询
		TbItemExample example=new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取分页结果
		
		PageInfo<TbItem> pageInfo=new PageInfo<>(list);
		System.out.println("查询总记录数:"+pageInfo.getTotal());
		System.out.println("总页数:"+pageInfo.getPages());
		System.out.println("list中的记录数:"+list.size());
		
	}

}
