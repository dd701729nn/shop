package cn.itcast.shop.categorysecond.adminaction;

import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.categorysecond.service.CategorySecondService;
import cn.itcast.shop.categorysecond.vo.CategorySecond;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 后台分类管理的Action
 * @author Administrator
 *
 */
public class AdminCategorySecondAction extends ActionSupport implements ModelDriven<CategorySecond>{

	//模型驱动使用的对象
	private CategorySecond categorySecond = new CategorySecond();
	@Override
	public CategorySecond getModel() {
		return categorySecond;
	}
	
	//注入二级分类servic
	private CategorySecondService categorySecondService;
	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	//接受page
	private Integer page;
	public void setPage(Integer page) {
		this.page = page;
	}
	
	//查询二级分类的方法
	public String findAll(){
		PageBean<CategorySecond> pageBean = categorySecondService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

}
