package com.dbi.shyam.email;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.dbi.shyam.utils.AppUtil;
import com.dbi.shyam.utils.Constants;

public class EmailTemplate {
	private String templateId;
	private String template;
	private Map<String, String> replacementParams;

	public EmailTemplate(String templateId, ResourceLoader resourceLoader) {
		this.templateId = templateId;
		try {
			this.template = loadTemplate(templateId, resourceLoader);
		} catch (Exception e) {
			this.template = "Congratulations!!!";
		}
	}

	private String loadTemplate(String templateId, ResourceLoader resourceLoader) throws Exception {
		Resource resource = resourceLoader.getResource("classpath:email-templates/" + templateId);
		File file = resource.getFile();
		String content = Constants.BLANK.getValue();
		try {
			content = new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			throw new Exception("Could not read template with ID = " + templateId);
		}
		return content;
	}

	public String getTemplate(Map<String, String> replacements) {
		String cTemplate = this.getTemplate();

		if (!AppUtil.isObjectEmpty(cTemplate)) {
			for (Map.Entry<String, String> entry : replacements.entrySet()) {
				cTemplate = cTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
			}
		}

		return cTemplate;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, String> getReplacementParams() {
		return replacementParams;
	}

	public void setReplacementParams(Map<String, String> replacementParams) {
		this.replacementParams = replacementParams;
	}

}