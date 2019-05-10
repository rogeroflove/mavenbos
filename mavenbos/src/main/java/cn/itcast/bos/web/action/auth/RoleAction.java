package cn.itcast.bos.web.action.auth;

import java.util.List;

import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 角色管理
 * 
 * @author seawind
 * 
 */
public class RoleAction extends BaseAction implements ModelDriven<Role> {

	// 模型驱动
	private Role role = new Role();

	@Override
	public Role getModel() {
		return role;
	}

	// 业务方法 ---添加 角色
	public String save() {
		// 调用业务层，添加角色，完成授权
		roleService.saveRole(role, functionIds);

		return "saveSUCCESS";
	}

	// 属性驱动
	private String functionIds;

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	// 业务方法 --- 查询所有角色
	public String list() {
		// 调用业务层
		List<Role> roles = roleService.listAll();
		// 压入值栈
		ActionContext.getContext().put("roles", roles);
		return "listSUCCESS";
	}

}
