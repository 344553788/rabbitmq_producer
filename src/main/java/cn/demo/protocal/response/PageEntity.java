package cn.demo.protocal.response;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageEntity<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6074946885792377364L;

	private long total;
	
	private List<T> list;
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	public static <T> PageEntity<T>  convert(Page<T> page){
		PageEntity<T> pageEntity = new PageEntity<T>();
		pageEntity.setTotal(page.getTotalElements());
		pageEntity.setList(page.getContent());
		return pageEntity;
	}

}
