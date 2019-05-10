package cn.itcast.bos.service.impl.bc;

import java.util.List;

import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.page.PageRequestBean;
import cn.itcast.bos.page.PageResponseBean;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.bc.SubareaService;

public class SubareaServiceImpl extends BaseService implements SubareaService {

	@Override
	public void saveOrUpdate(Subarea subarea) {
		// 分区对象中， 关联区域的游离对象，只有id
		subareaDAO.saveOrUpdate(subarea);
	}

	@Override
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean) {
		return pageQuery(pageRequestBean, subareaDAO);
	}

	@Override
	public List<Subarea> findnoassociations() {
		// 分区表中 定区外键 为null
		return subareaDAO.findByNamedQuery("Subarea.findassociations");
	}

}
