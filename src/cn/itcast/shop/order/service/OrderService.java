package cn.itcast.shop.order.service;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.order.dao.OrderDao;
import cn.itcast.shop.order.vo.Order;

/**
 * Order业务层代码
 * @author Administrator
 *
 */
@Transactional
public class OrderService {

	//注入dao
	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void save(Order order) {
		orderDao.save(order);
		
	}
	
	
}
