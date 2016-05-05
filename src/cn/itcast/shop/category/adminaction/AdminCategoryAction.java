package cn.itcast.shop.category.adminaction;

import java.util.List;

import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.category.vo.Category;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminCategoryAction extends ActionSupport implements
		ModelDriven<Category> {
	// 模型驱动使用的类
	private Category category = new Category();

	@Override
	public Category getModel() {
		return category;
	}

	// 注入一级分类的Service
	CategoryService categoryService = new CategoryService();

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	//

	// 后台执行查询所有一级分类的方法
	public String findAll() {
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "findAll";
	}

	// 保存一级分类的方法
	public String save() {
		// 调用Service进行保存
		categoryService.save(category);
		// 页面跳转
		return "saveSuccess";
	}

	// 删除一级分类的方法
	public String delete() {
		// 接收cid，可以使用模型驱动，删除一级分类，同时删除二级分类，必须先根据id查询再进行删除
		category = categoryService.findByCid(category.getCid());
		// 删除
		categoryService.delete(category);
		return "deleteSuccess";
	}

	// 编辑一级分类的方法
	public String edit() {
		// 接收cid，可以使用模型驱动，删除一级分类，同时删除二级分类，必须先根据id查询再进行删除
		category = categoryService.findByCid(category.getCid());
		// 页面的跳转
		return "editSuccess";
	}
	
	//后台修改一级分类的方法
	public String update(){
		categoryService.update(category);
		return "updateSuccess";
	}

}
