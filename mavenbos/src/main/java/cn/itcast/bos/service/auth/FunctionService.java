package cn.itcast.bos.service.auth;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.auth.Function;

/**
 * 功能权限管理业务接口
 * 
 * @author seawind
 * 
 */
public interface FunctionService {

	/**
	 * 查询所有功能列表
	 * 
	 * @return
	 */
	public List<Function> listAll();

	/**
	 * 保存权限信息
	 * 
	 * @param function
	 */
	public void saveFunction(Function function);

	/**
	 * 查询树节点数据
	 * 
	 * @return
	 */
	public List<Function> findTreeData(DetachedCriteria detachedCriteria);

}
