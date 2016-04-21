package cn.itcast.shop.user.service;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.user.dao.UserDao;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.MailUtils;
import cn.itcast.shop.utils.UUIDUtils;

@Transactional
public class UserService {

		// ע��UserDao
		private UserDao userDao;

		public void setUserDao(UserDao userDao) {
			this.userDao = userDao;
		}
		
		// ���û�����ѯ�û��ķ���:
		public User findByUsername(String username){
			return userDao.findByUserName(username);
		}
		
		//ҵ�������û�ע�����
		public void save(User user){
			//�����ݴ��뵽���ݿ�
			user.setState(0);//0�����û�δ���� 1�������û��Ѿ�����
			String code = UUIDUtils.getUUID() + UUIDUtils.getUUID();
			user.setCode(code);
			userDao.save(user);
			MailUtils.sendMail(user.getEmail(), code);
		}

		//ҵ�����ݼ������ѯ�û�
		public User findByCode(String code) {
			
			return userDao.findByCode(code);
		}

		public void update(User existUser) {
			userDao.update(existUser);
			
		}
		
}
