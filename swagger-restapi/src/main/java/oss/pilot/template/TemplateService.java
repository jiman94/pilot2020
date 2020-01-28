package oss.pilot.template;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "PilotTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class })
@Service
public class TemplateService {

	
	@Autowired
	private TemplateMapper templateMapper;

	public List<TemplateDto> getTemplate(TemplateDto template) {
		return templateMapper.selectTemplate(template);
	}

	public List<TemplateDto> getTemplateHistory(TemplateDto template) {
		return templateMapper.selectTemplateHistory(template);
	}

	public int addTemplate(TemplateDto template) {
		
		
		templateMapper.insertTemplate(template);
		// 
		
		templateMapper.insertTemplateHistory(template);
		
		/*
		if (!StringUtils.isEmpty(template.getTemplateSeq())) {
			List<TemplateDetailDto> templateDetailList = template.getDetailList();
			for (TemplateDetailDto templateDetail : templateDetailList) {
				
				templateMapper.insertTemplateDetailHistory(templateDetail);
			}
		} else {
			return 0;
		}
		*/
		
		return 1;
	}

	public int getAirCnt(Map<String, String> params) {
		return templateMapper.selectAirCnt(params);
	}

	public int setTemplate(TemplateDto template) {
		
		templateMapper.updateTemplate(template);
		// 
//		template.setHstFlagCd("U");
		templateMapper.insertTemplateHistory(template);
	//	List<TemplateDetailDto> templateDetailList = template.getDetailList();
		templateMapper.deleteTemplateDetail(template);
		/*
		for (TemplateDetailDto templateDetail : templateDetailList) {
			//templateDetail.setAirCd(template.getAirCd());
			//templateDetail.setInsertUserId(template.getInsertUserId());
			//templateMapper.insertTemplateDetail(templateDetail);
			//templateDetail.setHstFlagCd("U");
			//templateDetail.setHstSeq(template.getHstSeqNo());
			templateMapper.insertTemplateDetailHistory(templateDetail);
		}
		*/
		return 1;
	}

	public List<TemplateDetailDto> getTemplateDetail(TemplateDto template) {
		return templateMapper.selectTemplateDetail(template);
	}
	
	public List<TemplateDetailDto> getTemplateDetailHistory(TemplateDto template) {
		return templateMapper.selectTemplateDetailHistory(template);
	}

	public List<TemplateDetailDto> getTemplateDetailDef() {
		return templateMapper.selectTemplateDetailDef();
	}

}
