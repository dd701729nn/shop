package cn.itcast.shop.user.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.user.service.UserService;
import cn.itcast.shop.user.vo.User;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 用户模块的Action的泪
 * @author Administrator
 *
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{
	
	private User user = new User();
	
	@Override
	public User getModel() {
		return user;
	}
	//注入UserService
	private UserService userService;
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	
	/**
	 * 跳转到注册页面的执行方法
	 * @return
	 */
	public String registPage(){
		
		return "registPage";
	}
	
	/**
	 * 跳转到登陆页面的执行方法
	 */
	public String loginPage(){
		
		return "loginPage";
	}
	
	/**
	 * ajax进行异步校验用户名执行的方法
	 * @throws IOException 
	 */
	public String findByName() throws IOException {
		// 调用Service进行查询:
		User existUser = userService.findByUsername(user.getUsername());
		// 获得response对象,项页面输出:
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		// 判断
		if (existUser != null) {
			// 查询到该用户:用户名已经存在
			response.getWriter().println("<font color='red'>用户名已经存在</font>");
		} else {
			// 没查询到该用户:用户名可以使用
			response.getWriter().println("<font color='green'>用户名可以使用</font>");
		}
		return NONE;
	}
	
	public String regist(){
		userService.save(user);
		this.addActionMessage("注册成功，请去邮箱激活！！");
		return "msg";
	}
}
