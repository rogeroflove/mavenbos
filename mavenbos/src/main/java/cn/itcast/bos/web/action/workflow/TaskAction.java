package cn.itcast.bos.web.action.workflow;

import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.domain.zm.InStore;
import cn.itcast.bos.domain.zm.OutStore;
import cn.itcast.bos.domain.zm.ReceiveGoodsInfo;
import cn.itcast.bos.domain.zm.TransferInfo;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 进行任务相关操作
 * 
 * @author seawind
 * 
 */
public class TaskAction extends BaseAction {

	// 业务方法 ---- 查看当前用户的组任务
	public String findgrouptask() {

		// 查询组任务，使用 TaskService
		TaskService taskService = processEngine.getTaskService();
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		List<Task> tasks = taskService.findGroupTasks(user.getId());

		// 将任务列表放入值栈
		ActionContext.getContext().put("tasks", tasks);

		return "findgrouptaskSUCCESS";
	}

	// 业务方法 --- 拾取组任务
	public String takeTask() {
		// 调用 TaskService 方法 进行组任务拾取
		TaskService taskService = processEngine.getTaskService();
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		taskService.takeTask(taskId, user.getId());

		return "takeTaskSUCCESS";
	}

	// 属性驱动
	private String taskId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	// 业务方法 --- 个人任务查询
	public String findpersonaltask() {
		// 使用TaskService 查询个人任务
		TaskService taskService = processEngine.getTaskService();
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		List<Task> tasks = taskService.findPersonalTasks(user.getId());

		// 将结果压入值栈
		ActionContext.getContext().put("tasks", tasks);

		return "findpersonaltask";
	}

	// 业务方法 --- 办理 中转环节任务
	public String saveTransferinfo() {
		// 将请求数据 封装到 model
		TransferInfo transferInfo = new TransferInfo();
		transferInfo.setInfo(info);
		transferInfo.setArrive(arrive);
		transferInfo.setUpdateTime(new Date());

		// 调用业务层
		bosTaskService.completeTransferInfoTask(transferInfo, taskId);

		return "saveTransferinfoSUCCESS";
	}

	private String info;
	private String arrive;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	// 业务方法 -- 办理入库任务
	public String instorecomplete() {
		// 将业务数据封装 PO对象
		InStore inStore = new InStore();
		inStore.setInfo(info);
		inStore.setUpdateTime(new Date());

		// 调用业务层
		bosTaskService.completeInStoreTask(inStore, taskId);

		return "instorecompleteSUCCESS";
	}

	// 业务方法 -- 办理出库任务
	public String outstorecomplete() {
		// 将业务数据 封装 PO对象
		OutStore outStore = new OutStore();
		outStore.setInfo(info);
		outStore.setUpdateTime(new Date());

		// 调用业务层
		bosTaskService.completeOutStoreTask(outStore, taskId);

		return "outstorecompleteSUCCESS";
	}

	// 业务方法 -- 办理配送签收任务
	public String receiveinfocomplete() {
		// 将业务数据封装 PO对象
		ReceiveGoodsInfo receiveGoodsInfo = new ReceiveGoodsInfo();
		receiveGoodsInfo.setInfo(info);
		receiveGoodsInfo.setUpdateTime(new Date());

		// 调用业务层
		bosTaskService.completeReceiveGoodInfoTask(receiveGoodsInfo, taskId);

		return "receiveinfocompleteSUCCESS";
	}
}
