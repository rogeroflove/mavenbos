package cn.itcast.bos.service.workflow;

import cn.itcast.bos.domain.zm.InStore;
import cn.itcast.bos.domain.zm.OutStore;
import cn.itcast.bos.domain.zm.ReceiveGoodsInfo;
import cn.itcast.bos.domain.zm.TransferInfo;

/**
 * 建立 任务办理 业务接口
 * 
 * @author seawind
 * 
 */
public interface BosTaskService {

	/**
	 * 办理 中转环节的任务
	 * 
	 * @param transferInfo
	 */
	public void completeTransferInfoTask(TransferInfo transferInfo, String taskId);

	/**
	 * 办理 入库任务
	 * 
	 * @param inStore
	 * @param taskId
	 */
	public void completeInStoreTask(InStore inStore, String taskId);

	/**
	 * 办理出库任务
	 * 
	 * @param outStore
	 * @param taskId
	 */
	public void completeOutStoreTask(OutStore outStore, String taskId);

	/**
	 * 办理配送签收任务
	 * 
	 * @param receiveGoodsInfo
	 * @param taskId
	 */
	public void completeReceiveGoodInfoTask(ReceiveGoodsInfo receiveGoodsInfo, String taskId);

}
