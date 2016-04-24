package cn.itcast.shop.product.action;

import org.omg.CORBA.PRIVATE_MEMBER;

import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.product.vo.Product;

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
	
	//根据商品ID进行查询
	public String findByPid(){
		//调用Service的方法完成查询
		product = productService.findByPid(product.getPid());
		return "findByPid";
	}
}
