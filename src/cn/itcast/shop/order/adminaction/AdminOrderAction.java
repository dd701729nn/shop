package cn.itcast.shop.order.adminaction;

import java.util.List;

import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.order.vo.Order;
import cn.itcast.shop.order.vo.OrderItem;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminOrderAction extends ActionSupport implements ModelDriven<Order>{

	private Order order = new Order();
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	public Order getModel() {
		return order;
	}
	
	private OrderService orderService;
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	private Integer page;
	public void setPage(Integer page) {
		this.page = page;
	}
	
	//带分页查询的方法
	public String findAll(){
		//分页查询
		PageBean<Order> pageBean = orderService.findByPageUid(page);
		//通过值栈保存数据到页面
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		//页面的跳转
		return "findAll";
	}
	
	//根据订单id查询订单项
	public String findOrderItem(){
		List<OrderItem> list = orderService.findOrderItem(order.getOid());
		//通过值栈显示到页面上
		ActionContext.getContext().getValueStack().set("list", list);
		return "findOrderItem";
	}
	
	//后台修改订单状态
	public String updateState(){
		//根据订单id查询订单
		Order currOrder = orderService.findByOid(order.getOid());
		//修改订单状态
		currOrder.setState(3);
		orderService.update(currOrder);
		//页面跳转
		return "updateStateSuccess";
	}

}
