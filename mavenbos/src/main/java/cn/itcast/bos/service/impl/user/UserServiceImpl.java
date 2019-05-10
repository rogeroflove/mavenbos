package cn.itcast.bos.service.impl.user;

import java.util.List;

import org.hibernate.Hibernate;
import org.jbpm.api.IdentityService;
import org.jbpm.api.identity.Group;

import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.user.UserService;
import cn.itcast.bos.utils.MD5Utils;

public class UserServiceImpl extends BaseService implements UserService {

	@Override
	public User login(User user) {
		List<User> list = userDAO.findByNamedQuery("User.login", user.getUsername(), MD5Utils.md5(user.getPassword()));
		User loginUser = list.isEmpty() ? null : list.get(0);
		if (loginUser != null && loginUser.getRole() != null) {
			// 手动对用户管理 权限信息初始化
			Hibernate.initialize(loginUser.getRole().getFunctions());
		}
		return loginUser;
	}

	@Override
	public void editPassword(User user) {
		// 如果修改用户所有内容，直接调用 update
		// 如果修改用户某个属性，先查询，再修改
		User exist = userDAO.findById(user.getId()); // 持久态
		exist.setPassword(MD5Utils.md5(user.getPassword()));
	}

	@Override
	public void saveUser(User user) {
		// 防止 Role的id 为空串
		if (user.getRole() != null && user.getRole().getId() != null && user.getRole().getId().trim().length() == 0) {
			user.setRole(null);
		}
		// 对密码 进行 md5 加密
		user.setPassword(MD5Utils.md5(user.getPassword()));
		userDAO.save(user);

		// 在添加用户的同时，向 JBPM系统插入一个用户
		IdentityService identityService = processEngine.getIdentityService();
		identityService.createUser(user.getId(), user.getUsername(), user.getUsername()); // 建立JBPM用户
		if (user.getRole() != null) {
			// 在添加用户时，建立了和角色关系
			Role role = roleDAO.findById(user.getRole().getId());
			// 建立关系，JBPM 组id 使用 角色name属性
			identityService.createMembership(user.getId(), role.getName());
		}
	}

	@Override
	public List<User> listAll() {
		return userDAO.findAll();
	}

	@Override
	public void grantRole(User user) {
		User exist = userDAO.findById(user.getId());
		exist.setRole(user.getRole()); // 关联角色 自动更新

		// 建立 JBPM 用户和组关系 一个用户只属于一个组
		// 先删除 这个用户和原来组关系，建立新关系
		IdentityService identityService = processEngine.getIdentityService();
		// 获得用户原来的组
		List<Group> list = identityService.findGroupsByUser(exist.getId());
		for (Group group : list) {
			identityService.deleteMembership(exist.getId(), group.getId(), null);
		}
		// 建立新关系
		Role role = roleDAO.findById(user.getRole().getId());
		identityService.createMembership(exist.getId(), role.getName());
	}

}
