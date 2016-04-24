package cn.itcast.shop.product.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.product.dao.ProductDao;
import cn.itcast.shop.product.vo.Product;
/**
 * 商品的业务层代码
 * @author Administrator
 *
 */
@Transactional
public class ProductService {

	//注入ProductDao
	private ProductDao productDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	//首页上的热门商品查询
	public List<Product> findHot(){
		return productDao.findHot();
	}
	
	
}
