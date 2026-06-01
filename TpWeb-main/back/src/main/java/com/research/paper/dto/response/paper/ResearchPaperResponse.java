package com.research.paper.dto.response.paper;

import com.research.paper.enumeration.paper.PaperCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResearchPaperResponse {
    private String title;
    private String abstractText;
    private String thumbnailUrl;
    @Enumerated(EnumType.STRING)
    private PaperCategory category;
    private String fileType;
    private String document;
    private long fileSize;
    private Set<String> authorIds = new HashSet<>();
    private Set<String> keywords = new HashSet<>();
    private int commentCount;
    private int likesCount;
    private int downloadsCount;
    private int citations;
}
