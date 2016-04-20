package cn.itcast.shop.user.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.user.service.UserService;
import cn.itcast.shop.user.vo.User;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * �û�ģ���Action����
 * @author Administrator
 *
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{
	
	private User user = new User();
	
	@Override
	public User getModel() {
		return user;
	}
	//ע��UserService
	private UserService userService;
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	
	/**
	 * ��ת��ע��ҳ���ִ�з���
	 * @return
	 */
	public String registPage(){
		
		return "registPage";
	}
	
	/**
	 * ��ת����½ҳ���ִ�з���
	 */
	public String loginPage(){
		
		return "loginPage";
	}
	
	/**
	 * ajax�����첽У���û���ִ�еķ���
	 * @throws IOException 
	 */
	public String findByName() throws IOException {
		// ����Service���в�ѯ:
		User existUser = userService.findByUsername(user.getUsername());
		// ���response����,��ҳ�����:
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		// �ж�
		if (existUser != null) {
			// ��ѯ�����û�:�û����Ѿ�����
			response.getWriter().println("<font color='red'>�û����Ѿ�����</font>");
		} else {
			// û��ѯ�����û�:�û�������ʹ��
			response.getWriter().println("<font color='green'>�û�������ʹ��</font>");
		}
		return NONE;
	}
	
	public String regist(){
		userService.save(user);
		this.addActionMessage("ע��ɹ�����ȥ���伤���");
		return "msg";
	}
}
