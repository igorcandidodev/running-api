package com.runningapi.runningapi.service;

import com.runningapi.runningapi.dto.PromptTemplateDto;
import com.runningapi.runningapi.dto.PromptVariablesDto;
import com.runningapi.runningapi.exceptions.PromptTemplateNotFound;
import com.runningapi.runningapi.model.PromptTemplate;
import com.runningapi.runningapi.repository.PromptTemplateRepository;
import com.runningapi.runningapi.utils.PromptTemplateUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PromptTemplateService {

    private final PromptTemplateRepository promptTemplateRepository;

    public PromptTemplateService(PromptTemplateRepository promptTemplateRepository) {
        this.promptTemplateRepository = promptTemplateRepository;
    }

    public PromptTemplate createPromptTemplate(PromptTemplateDto promptTemplateDto) {
        var newPromptTemplate = new PromptTemplate(promptTemplateDto);
        return promptTemplateRepository.save(newPromptTemplate);
    }

    public PromptTemplate updatePromptTemplate(Long id, PromptTemplateDto promptTemplateDto) {
        Optional<PromptTemplate> optionalPromptTemplate = promptTemplateRepository.findById(id);
        if (optionalPromptTemplate.isPresent()) {
            var promptTemplate = optionalPromptTemplate.get();
            if (!promptTemplate.getName().equals(promptTemplateDto.name())) {
                promptTemplate.setName(promptTemplateDto.name());
            }
            if (!promptTemplate.getUserPrompt().equals(promptTemplateDto.userPrompt())) {
                promptTemplate.setUserPrompt(promptTemplateDto.userPrompt());
            }
            return promptTemplateRepository.save(promptTemplate);
        } else {
            throw new PromptTemplateNotFound(id);
        }
    }

    public PromptTemplate getPromptTemplateById(Long id) {
        Optional<PromptTemplate> optionalPromptTemplate = promptTemplateRepository.findById(id);
        if (optionalPromptTemplate.isPresent()) {
            return optionalPromptTemplate.get();
        }
        throw new PromptTemplateNotFound(id);
    }

    public List<PromptTemplate> getAllPromptsTemplates() {
        return promptTemplateRepository.findAll();
    }

    public void deletePromptTemplate(Long id) {
        promptTemplateRepository.deleteById(id);
    }

    public String getUpdatedUserPrompt(Long id, PromptVariablesDto promptVariablesDto) {
        Optional<PromptTemplate> optionalPromptTemplate = promptTemplateRepository.findById(id);
        if (optionalPromptTemplate.isPresent()) {
            PromptTemplate promptTemplate = optionalPromptTemplate.get();
            String userPrompt = promptTemplate.getUserPrompt();
            Map<String, Object> variables = promptVariablesDto.promptVariables();
            return PromptTemplateUtil.replacePlaceholders(userPrompt, variables);
        } else {
            throw new PromptTemplateNotFound(id);
        }
    }

    public String getSystemPrompt(Long promptId) {
        PromptTemplate promptTemplate = promptTemplateRepository.findById(promptId)
                .orElseThrow(() -> new PromptTemplateNotFound(promptId));
        return promptTemplate.getSystemPrompt();
    }
}
