package cn.itcast.shop.user.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.shop.user.vo.User;

/**
 * 用户模块持久层的代码
 * @author Administrator
 *
 */
public class UserDao extends HibernateDaoSupport{

	// 按名次查询是否有该用户:
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
