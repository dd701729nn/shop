package cn.itcast.shop.order.service;

import cn.itcast.shop.order.dao.OrderDao;

/**
 * Order业务层代码
 * @author Administrator
 *
 */
public class OrderService {

	//注入dao
	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	
}
