package cn.itcast.bos.service.qp;

import cn.itcast.bos.domain.qp.NoticeBill;

/**
 * 业务通知单 业务接口
 * 
 * @author seawind
 * 
 */
public interface NoticeBillService {

	/**
	 * 新增业务通知单
	 * 
	 * @param noticeBill
	 */
	public void saveNoticeBill(NoticeBill noticeBill);

}
