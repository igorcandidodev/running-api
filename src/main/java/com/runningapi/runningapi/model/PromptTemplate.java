package com.runningapi.runningapi.model;

import com.runningapi.runningapi.dto.PromptTemplateDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "PROMPT_TEMPLATES")
public class PromptTemplate implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String systemPrompt;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String userPrompt;

    public PromptTemplate(PromptTemplateDto promptTemplateDto) {
        this.name = promptTemplateDto.name();
        this.userPrompt = promptTemplateDto.userPrompt();
        this.systemPrompt = promptTemplateDto.systemPrompt();
    }

    public PromptTemplate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }
}
