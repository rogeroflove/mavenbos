package cn.itcast.bos.service.impl.auth;

import java.util.List;

import org.jbpm.api.IdentityService;

import cn.itcast.bos.domain.auth.Function;
import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.service.auth.RoleService;
import cn.itcast.bos.service.base.BaseService;

public class RoleServiceImpl extends BaseService implements RoleService {

	@Override
	public void saveRole(Role role, String functionIds) {
		// 将role信息保存角色表
		roleDAO.save(role); // 持久态
		// 建立 role 和 function联系，向role_function 中间表插入数据
		if (functionIds != null) {
			String[] ids = functionIds.split(",");
			for (String id : ids) {
				Function function = funtionDAO.findById(id); // 功能权限
				role.getFunctions().add(function); // 多对多关联，向中间表插入数据
			}
		}
		// 建立JBPM 系统 组信息
		IdentityService identityService = processEngine.getIdentityService();
		identityService.createGroup(role.getName());
	}

	@Override
	public List<Role> listAll() {
		return roleDAO.findAll();
	}
}
