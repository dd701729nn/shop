package cn.itcast.shop.order.action;

import java.io.IOException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.cart.vo.Cart;
import cn.itcast.shop.cart.vo.CartItem;
import cn.itcast.shop.order.service.OrderService;
import cn.itcast.shop.order.vo.Order;
import cn.itcast.shop.order.vo.OrderItem;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.PageBean;
import cn.itcast.shop.utils.PaymentUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 订单管理的Action
 * 
 * @author Administrator
 * 
 */
public class OrderAction extends ActionSupport implements ModelDriven<Order> {
	// 模型驱动使用的对象
	private Order order = new Order();

	@Override
	public Order getModel() {
		return order;
	}

	// 注入orderservice
	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	// 接受page参数
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	// 接收支付通道
	private String pd_FrpId;

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}

	// 接收付款成功后响应的数据
	private String r6_Order;
	private String r3_Amt;

	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}

	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}

	// 生成订单的方法
	public String save() {
		// 1.保存数据到数据库
		// 订单数据补全
		// 设置订单的时间
		// order.setOrdertime(new Date());
		// 设置订单的状态
		order.setState(1);// 1.未付款 2.已付款，未发货 3.已发货，为确认 4.交易完成
		// 总计的数据是购物车总的数据
		Cart cart = (Cart) ServletActionContext.getRequest().getSession()
				.getAttribute("cart");
		if (cart == null) {
			this.addActionError("亲，你还没有购物！请先去购物！");
			return "msg";
		}
		order.setTotal(cart.getTotal());
		// 设置订单中的订单项
		for (CartItem cartItem : cart.getCartItems()) {
			// 订单项的信息从购物车中获得
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);

			order.getOrderItems().add(orderItem);
		}
		// 设置订单关联的客户
		User existUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("existUser");
		if (existUser == null) {
			this.addActionError("亲!您还没有登录!");
			return "login";
		}
		order.setUser(existUser);
		orderService.save(order);
		// 清空购物车
		cart.clearCart();
		// 2.将订单对象显示在页面上
		// 通过值栈方式显示：因为Order显示的对象就是模型驱动的使用的对象
		return "saveSuccess";
	}

	// 我的订单查询：
	public String findByUid() {
		// 根据用户id查询
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("existUser");
		// 调用service查询
		PageBean<Order> pageBean = orderService.findByPageUid(user.getUid(),
				page);
		// 将分页数据显示到页面上
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByUidSuccess";
	}

	// 根据订单id查询订单:
	public String findByOid() {
		order = orderService.findByOid(order.getOid());
		return "findByOidSuccess";
	}

	// 为订单付款的方法
	public String payOrder() throws IOException {
		// 修改订单
		String p0_Cmd = "Buy"; // 业务类型:
		String p1_MerId = "10001126856";// 商户编号:
		String p2_Order = order.getOid().toString();// 订单编号:
		String p3_Amt = "0.01"; // 付款金额:
		String p4_Cur = "CNY"; // 交易币种:
		String p5_Pid = ""; // 商品名称:
		String p6_Pcat = ""; // 商品种类:
		String p7_Pdesc = ""; // 商品描述:
		String p8_Url = "http://localhost:8080/shop/order_callBack.action"; // 商户接收支付成功数据的地址:
		String p9_SAF = ""; // 送货地址:
		String pa_MP = ""; // 商户扩展信息:
		String pd_FrpId = this.pd_FrpId;// 支付通道编码:
		String pr_NeedResponse = "1"; // 应答机制:
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // 秘钥
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue); // hmac
		// 向易宝发送请求:
		StringBuffer sb = new StringBuffer(
				"https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);

		// 重定向:向易宝出发:
		ServletActionContext.getResponse().sendRedirect(sb.toString());
		return NONE;
	}

	// 付款成功后的转向页面
	public String callBack() {
		// 修改订单状态：已经付款
		Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
		currOrder.setState(2);
		orderService.update(currOrder);
		// 在页面上显示付款成功的信息
		this.addActionMessage("支付成功!订单编号为: " + r6_Order + " 付款金额为: " + r3_Amt);
		return "msg";
	}

	// 修改订单的状态:
	public String updateState() {
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setState(4);
		orderService.update(currOrder);
		return "updateStateSuccess";
	}

}
