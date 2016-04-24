package cn.itcast.shop.product.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.product.vo.Product;
/**
 * 商品持久层代码
 * @author Administrator
 *
 */
public class ProductDao extends HibernateDaoSupport{

	public List<Product> findHot() {
		//使用离线条件查询
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
		detachedCriteria.add(Restrictions.eq("is_hot", 1));
		//倒序排序输出
		detachedCriteria.addOrder(Order.desc("pdate"));
		//执行查询
		List<Product> list = this.getHibernateTemplate().findByCriteria(detachedCriteria, 0, 10);
		
		return list;
	}

}
