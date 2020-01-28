package oss.pilot.template;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TemplateMapper {
	
	public List<TemplateDto> selectTemplate(TemplateDto template);

	public List<TemplateDto> selectTemplateHistory(TemplateDto template);

	public int selectAirCnt(Map<String, String> params);

	public void insertTemplate(TemplateDto template);

	public void insertTemplateDetail(TemplateDetailDto templateDetail);

	public void insertTemplateHistory(TemplateDto template);

	public void insertTemplateDetailHistory(TemplateDetailDto templateDetail);

	public int updateTemplate(TemplateDto template);

	public void updateTemplateDisableByAirCd(TemplateDto template);

	public void deleteTemplateDetail(TemplateDto template);

	public List<TemplateDetailDto> selectTemplateDetail(TemplateDto template);

	public List<TemplateDetailDto> selectTemplateDetailHistory(TemplateDto template);

	public List<TemplateDetailDto> selectTemplateDetailDef();
}
