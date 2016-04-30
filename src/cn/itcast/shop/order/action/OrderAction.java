package cn.itcast.shop.order.action;

import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.order.vo.Order;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 订单管理的Action
 * @author Administrator
 *
 */
public class OrderAction extends ActionSupport implements ModelDriven<Order>{
	//模型驱动使用的对象
	private Order order = new Order();
	@Override
	public Order getModel() {
		return order;
	}
	
	//注入orderservice
	private OrderService orderService;
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	

}
