package oss.pilot.template;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;


@RestController
//@RequestMapping(value = "${rest.basePath}/template")
@RequestMapping(value = "/rest/secure/template")
@Slf4j
public class TemplateController {
	
	
	@Autowired
	private TemplateService templateService;

	@Autowired
	MessageSource messageSource;
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<TemplateDto>> getTemplate(@RequestParam Map<String, String> params) {
		TemplateDto template = new TemplateDto();
		
		//template.setAirCd(params.get("airCd"));
		//template.setUseYn(params.get("useYn"));

		List<TemplateDto> templates = templateService.getTemplate(template);
		if (templates == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(templates, HttpStatus.OK);
	}

	@GetMapping(value = "/history", produces = "application/json")
	public ResponseEntity<List<TemplateDto>> getTemplateHistory(@RequestParam Map<String, String> params) {
		
		TemplateDto template = new TemplateDto();
		//template.setAirCd(params.get("airCd"));
		//template.setTemplateSeq(Integer.valueOf(params.get("templateSeq")));

		List<TemplateDto> templates = templateService.getTemplateHistory(template);
		
		if (templates == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(templates, HttpStatus.OK);
	}

	@GetMapping(value = "/cnt", produces = "application/json")
	public ResponseEntity<Integer> getAirCnt(@RequestParam Map<String, String> params) {
		int cnt = templateService.getAirCnt(params);
		if (cnt == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cnt, HttpStatus.OK);
	}

	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Void> setTemplate(@RequestBody TemplateDto template, final UriComponentsBuilder ucBuilder) {

		try {
			//if(!StringUtils.isEmpty(template) && !StringUtils.isEmpty(template.getTemplateSeq())) {
			//	templateService.setTemplate(template);
			//}
		} catch (Exception e) {
			log.error("Set template item fail", e);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		// add new user information String at buildAndExpand if return some information
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("${rest.basePath}/template/template").buildAndExpand("").toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Void> addTemplate(@RequestBody TemplateDto template, final UriComponentsBuilder ucBuilder) {
		try {
			//if(!StringUtils.isEmpty(template) && !StringUtils.isEmpty(template.getAirCd())) {
			//	templateService.addTemplate(template);
			//}
		} catch (Exception e) {
			log.error("Add template item fail", e);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		// add new user information String at buildAndExpand if return some information
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("${rest.basePath}/template/template").buildAndExpand("").toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@GetMapping(value = "/detail", produces = "application/json")
	public ResponseEntity<List<TemplateDetailDto>> getTemplateDetail(@RequestParam Map<String, String> params) {
		TemplateDto template = new TemplateDto();
		//template.setTemplateSeq(Integer.valueOf(params.get("templateSeq")));
		//template.setAirCd(params.get("airCd"));

		List<TemplateDetailDto> templateDetailList = templateService.getTemplateDetail(template);
		if (templateDetailList == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(templateDetailList, HttpStatus.OK);
	}

	@GetMapping(value = "/detailHistory", produces = "application/json")
	public ResponseEntity<List<TemplateDetailDto>> getTemplateDetailHistory(@RequestParam Map<String, String> params) {
		TemplateDto template = new TemplateDto();
		//template.setTemplateSeq(Integer.valueOf(params.get("templateSeq")));
		//template.setHstSeqNo(Integer.valueOf(params.get("hstSeqNo")));
		//template.setAirCd(params.get("airCd"));

		List<TemplateDetailDto> templateDetailList = templateService.getTemplateDetailHistory(template);
		
		if (templateDetailList == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(templateDetailList, HttpStatus.OK);
	}

	@GetMapping(value = "/detailDef", produces = "application/json")
	public ResponseEntity<List<TemplateDetailDto>> getTemplateDetailDef(@RequestParam Map<String, String> params) {
		List<TemplateDetailDto> templateDetailList = templateService.getTemplateDetailDef();
		if (templateDetailList == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(templateDetailList, HttpStatus.OK);
	}

}
