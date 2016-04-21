package cn.itcast.shop.user.service;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.user.dao.UserDao;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.MailUitls;
import cn.itcast.shop.utils.UUIDUtils;

/**
 * 鐢ㄦ埛鍚嶆ā鍧椾笟鍔″眰浠ｇ爜
 * @author 浼犳櫤.閮槈
 *
 */
@Transactional
public class UserService {
	// 娉ㄥ叆UserDao
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	// 鎸夌敤鎴峰悕鏌ヨ鐢ㄦ埛鐨勬柟娉�:
	public User findByUsername(String username){
		return userDao.findByUsername(username);
	}

	// 涓氬姟灞傚畬鎴愮敤鎴锋敞鍐屼唬鐮�:
	public void save(User user) {
		// 灏嗘暟鎹瓨鍏ュ埌鏁版嵁搴�
		user.setState(0); // 0:浠ｈ〃鐢ㄦ埛鏈縺娲�.  1:浠ｈ〃鐢ㄦ埛宸茬粡婵�娲�.
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
		userDao.save(user);
		// 鍙戦�佹縺娲婚偖浠�;
		MailUitls.sendMail(user.getEmail(), code);
	}

	// 涓氬姟灞傛牴鎹縺娲荤爜鏌ヨ鐢ㄦ埛
	public User findByCode(String code) {
		return userDao.findByCode(code);
	}

	// 淇敼鐢ㄦ埛鐨勭姸鎬佺殑鏂规硶
	public void update(User existUser) {
		userDao.update(existUser);
	}

	// 鐢ㄦ埛鐧诲綍鐨勬柟娉�
	public User login(User user) {
		return userDao.login(user);
	}
}
