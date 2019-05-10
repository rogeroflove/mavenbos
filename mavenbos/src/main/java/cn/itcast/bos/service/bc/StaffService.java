package cn.itcast.bos.service.bc;

import java.util.List;

import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.page.PageQuery;

/**
 * 取派员管理 业务接口
 * 
 * @author seawind
 * 
 */
public interface StaffService extends PageQuery {

	/**
	 * 添加或者修改 取派员
	 * 
	 * @param staff
	 */
	public void saveOrUpdate(Staff staff);

	/**
	 * 取派员 作废
	 * 
	 * @param ids
	 */
	public void delBatch(String[] ids);

	/**
	 * 查询未作废的取派员
	 * 
	 * @return
	 */
	public List<Staff> findAllNoDeleteStaffs();
}
