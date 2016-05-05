package cn.itcast.shop.order.action;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.cart.vo.Cart;
import cn.itcast.shop.cart.vo.CartItem;
import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.order.vo.Order;
import cn.itcast.shop.order.vo.OrderItem;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
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
	
	//接受page参数
	private Integer page;
	
	public void setPage(Integer page) {
		this.page = page;
	}

	//生成订单的方法
	public String save(){
		//调用Service完成数据库的插入操作
		//Order order = new Order();
		//设置订单的总金额，订单的总金额应该是购物车的总金额
		//购物车在session中，从session中获得购物车信息
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if(cart == null){
			this.addActionError("亲，你还没有购物！请先去购物！");
			return "msg";
		}
		//设置订单的状态
		order.setState(1);
		//设置订单的时间
//		order.setOrdertime(new Date());
		//设置订单关联的客户
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		if(existUser == null){
			this.addActionMessage("亲!您还没有登录!");
			return "login";
		}
		order.setUser(existUser);
		//设置订单项的集合
		for(CartItem cartItem : cart.getCartItems()){
			//订单项的信息从购物车中获得
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		//清空购物车
		orderService.save(order);
		//2.将订单对象显示在页面上
		//通过值栈
		cart.clearCart();
		return "saveSuccess";
	}
	
	//我的订单查询：
	public String findByUid(){
		//根据用户id查询
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		//调用service查询
		PageBean<Order> pageBean = orderService.findByPageUid(user.getUid(),page);
		//将分页数据显示到页面上
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByUidSuccess";
	}
	
	// 根据订单id查询订单:
	public String findByOid() {
		order = orderService.findByOid(order.getOid());
		return "findByOidSuccess";
	}

}
