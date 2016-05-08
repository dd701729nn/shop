package cn.itcast.shop.product.adminaction;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.categorysecond.service.CategorySecondService;
import cn.itcast.shop.categorysecond.vo.CategorySecond;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.product.vo.Product;
import cn.itcast.shop.utils.PageBean;

import com.mysql.jdbc.Field;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminProductAction extends ActionSupport implements
		ModelDriven<Product> {

	// 模型驱动使用对象
	private Product product = new Product();

	@Override
	public Product getModel() {
		return product;
	}

	// 注入商品的Service
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	// 注入二级分类的service
	private CategorySecondService categorySecondService;

	public void setCategorySecondService(
			CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	// 接受page参数
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	// 文件上传需要的参数
	private File upload;
	private String uploadFileName;
	private String uploadContextType;

	// 带分页查询商品的方法
	public String findAll() {
		// 调用service完成查询操作
		PageBean<Product> pageBean = productService.findByPageCid(page);
		// 将数据返回到页面上
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

	// 跳转到添加页面
	public String addPage() {
		// 查询所有二级分类的集合
		List<CategorySecond> csList = categorySecondService.findAll();
		// 保存到值栈中
		ActionContext.getContext().getValueStack().set("csList", csList);
		// 跳转页面
		return "addPageSuccess";
	}

	// 保存商品的方法
	public String save() throws IOException {
		// 调用service完成保存操作
//		product.setPdate(new Date());
		if (upload != null) {
			// 将商品图片上传到服务器上.
			// 获得上传图片的服务器端路径.
			String path = ServletActionContext.getServletContext().getRealPath(
					"/products");
			// 创建文件类型对象:
			File diskFile = new File(path + "//" + uploadFileName);
			// 文件上传:
			FileUtils.copyFile(upload, diskFile);
			product.setImage("products/" + uploadFileName);
		}
		// 将数据保存到数据库
		productService.save(product);
		// 页面跳转
		return "saveSuccess";
	}

	// 删除商品的方法
	public String delete() {
		// 先查询在删除
		product = productService.findByPid(product.getPid());
		// 删除上传的图片
		String path = product.getImage();
		if (path != null) {
			String realPath = ServletActionContext.getServletContext()
					.getRealPath("/" + path);
			File file = new File(realPath);
			file.delete();
		}
		productService.delete(product);
		return "deleteSuccess";
	}

	// 编辑商品的方法
	public String edit() {
		// 根据商品id查询该商品
		product = productService.findByPid(product.getPid());
		// 查询所有二级分类的集合
		List<CategorySecond> csList = categorySecondService.findAll();
		// 将数据保存到页面中
		ActionContext.getContext().getValueStack().set("csList", csList);
		return "editSuccess";
	}

	// 修改商品的方法
	public String update() throws IOException {
//		product.setPdate(new Date());
		// 文件的上传
		if (upload != null) {
			//将原来的图片删除
			String old = product.getImage();
			File file = new File(ServletActionContext.getServletContext()
					.getRealPath("/" + old));
			file.delete();
			// 将商品图片上传到服务器上.
			// 获得上传图片的服务器端路径.
			String path = ServletActionContext.getServletContext().getRealPath(
					"/products");
			// 创建文件类型对象:
			File diskFile = new File(path + "//" + uploadFileName);
			// 文件上传:
			FileUtils.copyFile(upload, diskFile);
			product.setImage("products/" + uploadFileName);
		}
		// 修改商品的数据到数据库
		productService.update(product);
		//页面的跳转
		return "updateSuccess";
	}
}
