package cn.itcast.shop.product.action;

import java.util.List;

import cn.itcast.shop.category.service.CategoryService;
import cn.itcast.shop.category.vo.Category;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.product.vo.Product;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 商品的Action对象
 * @author Administrator
 *
 */
public class ProductAction extends ActionSupport implements ModelDriven<Product>{

	//用于接受数据的模型驱动
	private Product product = new Product();

	//
	private Integer cid;
	//注入一级分类的Serivce
	private CategoryService categoryService;
	
	//接受当前的页数
	private int page;
	
	public void setPage(int page) {
		this.page = page;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	//注入商品的Service
	private ProductService productService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public Product getModel() {
		return product;
	}
	
	// 根据商品的ID进行查询商品:执行方法:
		public String findByPid() {
			// 调用Service的方法完成查询.
			product = productService.findByPid(product.getPid());
			return "findByPid";
		}

		// 根据分类的id查询商品:
		public String findByCid() {
			// List<Category> cList = categoryService.findAll();
			PageBean<Product> pageBean = productService.findByPageCid(cid, page);// 根据一级分类查询商品,带分页查询
			// 将PageBean存入到值栈中:
			ActionContext.getContext().getValueStack().set("pageBean", pageBean);
			return "findByCid";
		}

	
}
