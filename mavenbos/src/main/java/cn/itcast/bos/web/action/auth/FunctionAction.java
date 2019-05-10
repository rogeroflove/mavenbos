package cn.itcast.bos.web.action.auth;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cn.itcast.bos.domain.auth.Function;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 功能权限管理
 * 
 * @author seawind
 * 
 */
public class FunctionAction extends BaseAction implements ModelDriven<Function> {

	// 模型驱动
	private Function function = new Function();

	@Override
	public Function getModel() {
		return function;
	}

	// 业务方法 ---- 查询ajax json列表
	public String ajaxlist() {
		// 调用业务层 ，查询所有function
		List<Function> functions = functionService.listAll();
		// 将结果数据转换为 json 返回
		ActionContext.getContext().put("functions", functions);
		return "ajaxlistSUCCESS";
	}

	// 业务方法 -- 保存权限
	public String save() {
		functionService.saveFunction(function);
		return "saveSUCCESS";
	}

	// 业务方法 --- 所有权限列表
	public String list() {
		// 调用业务层 ，查询所有function
		List<Function> functions = functionService.listAll();
		// 放入值栈
		ActionContext.getContext().put("functions", functions);
		return "listSUCCESS";
	}

	// 业务方法 --- 查询授权树
	public String treedata() {
		// 查询树，进行排序
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
		// 按照zindex 升序
		detachedCriteria.addOrder(Order.asc("zindex"));

		// 调用业务层 ，查询所有function
		List<Function> functions = functionService.findTreeData(detachedCriteria);
		// 放入值栈
		ActionContext.getContext().put("functions", functions);
		return "treedataSUCCESS";
	}

	// 业务方法 --- 查询动态菜单
	public String menu() {
		// 复杂查询 使用 QBC
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);

		// 查询当前用户具有权限菜单
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if (!user.getUsername().equals("admin")) {// 如果用户是admin 需要显示所有功能
			// 多表关联，每关联一个表，创建一个别名
			detachedCriteria.createAlias("roles", "r");
			detachedCriteria.createAlias("r.users", "u");
			detachedCriteria.add(Restrictions.eq("u.id", user.getId()));
		}
		// 查询generateMenu 为 1 的功能
		detachedCriteria.add(Restrictions.eq("generateMenu", "1"));
		// 按照zindex 排序
		detachedCriteria.addOrder(Order.asc("zindex"));

		// 调用业务层
		List<Function> functions = functionService.findTreeData(detachedCriteria);
		// 压入值栈
		ActionContext.getContext().put("functions", functions);

		return "menuSUCCESS";
	}
}
