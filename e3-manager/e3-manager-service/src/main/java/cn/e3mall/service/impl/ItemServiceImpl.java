package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cm.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

/**
 * 商品管理
 * @author 11734
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	/**
	 * 根据ID查询
	 */
	public TbItem getItemById(long itemid) {
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemid);
		
		return tbItem;
	}
	/**
	 * 查询列表
	 */
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		TbItemExample example=new TbItemExample();
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		//取查询结果
		PageInfo<TbItem> pageInfo=new PageInfo<>(list);
		//获取总记录数
		
		long total = pageInfo.getTotal();
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		result.setTotal(total);
		result.setRows(list);
		//返回结果
		return result;
	}
	
}
