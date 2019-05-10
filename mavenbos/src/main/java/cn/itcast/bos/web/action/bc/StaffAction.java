package cn.itcast.bos.web.action.bc;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 取派员 信息管理
 * 
 * @author seawind
 * 
 */
public class StaffAction extends BaseAction implements ModelDriven<Staff> {

	// 模型驱动
	private Staff staff = new Staff();

	@Override
	public Staff getModel() {
		return staff;
	}

	// 业务方法 --- 保存或修改 取派员
	public String saveOrUpdate() {
		// staff对象中 关联 游离状态 Standard对象 （只有id）
		// 调用业务层
		staffService.saveOrUpdate(staff);

		return "saveOrUpdateSUCCESS";
	}

	// 业务方法 --- 分页列表查询
	public String pageQuery() {
		// 条件对象
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		PageRequestBean pageRequestBean = initPageRequestBean(detachedCriteria);

		// 调用业务层，进行查询 结果PageResponseBean
		PageResponseBean pageResponseBean = staffService.pageQuery(pageRequestBean);

		// 将结果 转换 json
		ActionContext.getContext().put("pageResponseBean", pageResponseBean);

		return "pageQuerySUCCESS";
	}

	// 业务方法 --- 批量删除
	public String delBatch() {
		// 获得要作废 取派员 id
		String[] ids = staff.getId().split(", ");

		// 调用业务层，作废
		staffService.delBatch(ids);

		return "delBatchSUCCESS";
	}

	// 业务方法 --- 查询取派员 json列表
	public String ajaxlist() {
		// 调用业务层 ，查询未作废取派员
		List<Staff> staffs = staffService.findAllNoDeleteStaffs();

		// 转换为json
		ActionContext.getContext().put("staffs", staffs);

		return "ajaxlistSUCCESS";
	}

}
