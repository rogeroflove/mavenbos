package cn.itcast.bos.web.action.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 用户管理
 * 
 * @author seawind
 * 
 */
public class UserAction extends BaseAction implements ModelDriven<User> {

	// 模型驱动
	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	// 业务方法 --- 修改密码
	public String editpassword() {
		// model 封装 用户新密码
		User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		user.setId(loginUser.getId());

		// 调用业务层
		try {
			userService.editPassword(user);
			// 修改成功
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "success");
			map.put("msg", "修改密码成功");
			ActionContext.getContext().put("map", map);
		} catch (Exception e) {
			// 修改失败
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "failure");
			map.put("msg", "修改密码失败," + e.getMessage());
			ActionContext.getContext().put("map", map);
		}

		return "editpasswordSUCCESS";
	}

	// 业务方法 --- 添加用户
	public String save() {
		// 调用业务层 保存用户
		userService.saveUser(user);
		return "saveSUCCESS";
	}

	// 业务方法 --- 查询所有用户
	public String list() {
		// 调用业务层 ，查询所有用户列表
		List<User> users = userService.listAll();
		// 放入值栈
		ActionContext.getContext().put("users", users);

		return "listSUCCESS";
	}

	// 业务方法 --- 授予角色
	public String grantRole() {
		// 调用业务层
		userService.grantRole(user);

		return "grantRoleSUCCESS";
	}
}
