package cn.itcast.shop.user.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.user.vo.User;

/**
 * �û�ģ��־ò�Ĵ���
 * @author Administrator
 *
 */
public class UserDao extends HibernateDaoSupport{

	// �����β�ѯ�Ƿ��и��û�:
		public User findByUserName(String username){
			String hql = "from User where username = ?";
			List<User> list = this.getHibernateTemplate().find(hql, username);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
			return null;
		}

		public void save(User user) {
			this.getHibernateTemplate().save(user);
		}
}
