package cn.itcast.shop.categorysecond.vo;

import java.util.HashSet;
import java.util.Set;

import cn.itcast.shop.category.vo.Category;
import cn.itcast.shop.product.vo.Product;

/**
 * 二级分类的实体
 * @author Administrator
 *
 */
public class CategorySecond {

	private Integer csid;
	private String csnameS;
	//所属一级分类，存的是一级分类的对象
	private Category category;
	//一级分类中存放二级分类的集合
	private Set<Product> categorySeconds = new HashSet<Product>();
	public Integer getCsid() {
		return csid;
	}
	public void setCsid(Integer csid) {
		this.csid = csid;
	}
	public String getCsnameS() {
		return csnameS;
	}
	public void setCsnameS(String csnameS) {
		this.csnameS = csnameS;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Set<Product> getCategorySeconds() {
		return categorySeconds;
	}
	public void setCategorySeconds(Set<Product> categorySeconds) {
		this.categorySeconds = categorySeconds;
	}
	
	
}
