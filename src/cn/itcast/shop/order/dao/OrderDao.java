package cn.itcast.shop.order.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.order.vo.Order;

/**
 * 订单模块Dao层的代码
 * @author Administrator
 *
 */
public class OrderDao extends HibernateDaoSupport{

	public void save(Order order) {
		this.getHibernateTemplate().save(order);
		
	}

}
