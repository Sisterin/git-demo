package com.system.entity.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 分页参数
 * @author 
 *
 */
public class PageDto<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final Integer default_page_size = 10;
	
	/**
	 * 页码，从1�?�?
	 */
	private Integer pageNum = 1;
	
	/**
	 * 每页条数
	 */
	private Integer pageSize = 10;
	
	
	/**
	 * 总数
	 */
	private Long total;
	
	/**
	 * 分页结果
	 */
	private List<T> result;

	public PageDto() {
		
	}
	
	public PageDto(List<T> result, Long total) {
		super();
		this.total = total;
		this.result = result;
	}





	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}


	

	
	
	
}
