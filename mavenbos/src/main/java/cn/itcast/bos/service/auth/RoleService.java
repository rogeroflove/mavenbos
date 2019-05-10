package cn.itcast.bos.service.auth;

import java.util.List;

import cn.itcast.bos.domain.auth.Role;

/**
 * 角色管理 业务接口
 * 
 * @author seawind
 * 
 */
public interface RoleService {

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @param functionIds
	 */
	public void saveRole(Role role, String functionIds);

	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	public List<Role> listAll();

}
