package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
	private long recourdCount;
	private long totalPages;
	private List<SearchItem> itemList;
	public long getRecourdCount() {
		return recourdCount;
	}
	public long getTotalPages() {
		return totalPages;
	}
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setRecourdCount(long recourdCount) {
		this.recourdCount = recourdCount;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	
	
}
