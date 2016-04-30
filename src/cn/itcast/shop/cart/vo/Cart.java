package cn.itcast.shop.cart.vo;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;




/**
 * 购物车对象
 * @author Administrator
 *
 */
public class Cart {

	//购物项集合：Map的key就是商品的pid，value：购物项
	private Map<Integer,CartItem> map = new LinkedHashMap<Integer,CartItem>();
	//购物总计
	private double total;
	
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
	
	//购物车的功能
	//1.将购物项添加到购物车
	public void addCart(CartItem cartItem){
		//判断购物车中是否已经存在该购物项
		//获得商品的id
		Integer pid = cartItem.getProduct().getPid();
		//如果存在，数量增加，总计增加
		if(map.containsKey(pid)){
			//存在
			CartItem _cartItem = map.get(pid);//获得购物车中原来
			_cartItem.setCount(_cartItem.getCount() + cartItem.getCount());
		}else{
			//不存在
			map.put(pid, cartItem);
		}
		//如果不存在，向map中添加购物项，总计增加
		total += cartItem.getSubtotal();
	}
	//2.从购物车移除购物项
	public void removeCart(Integer pid){
		//将购物项移除购物车
		CartItem cartItem = map.remove(pid);
		//总计= 总计- 移除的购物项小计
		total -= cartItem.getSubtotal();
	}
	//3.清空购物车
	public void clearCart(){
		//将所有的购物项清空
		map.clear();
		//将总计设为0
		total = 0;
	}
	
	
	public Map<Integer, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<Integer, CartItem> map) {
		this.map = map;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
