package cn.itcast.bos.service.bc;

import java.util.List;

import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.page.PageQuery;

/**
 * 分区管理 业务接口
 * 
 * @author seawind
 * 
 */
public interface SubareaService extends PageQuery {

	/**
	 * 添加或者修改分区
	 * 
	 * @param subarea
	 */
	public void saveOrUpdate(Subarea subarea);

	/**
	 * 查询 所有未关联定区的分区
	 * 
	 * @return
	 */
	public List<Subarea> findnoassociations();

}
