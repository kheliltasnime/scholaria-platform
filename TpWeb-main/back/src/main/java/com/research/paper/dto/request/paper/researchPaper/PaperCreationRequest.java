package com.research.paper.dto.request.paper.researchPaper;

import com.research.paper.enumeration.paper.PaperCategory;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperCreationRequest {

    @NotBlank(message = "VALIDATION.PAPER_CREATION.TITLE.NOT_BLANK")
    @Size(min = 10, max = 200, message = "VALIDATION.PAPER_CREATION.TITLE.SIZE")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-:,;'\"]+$",
            message = "VALIDATION.PAPER_CREATION.TITLE.PATTERN")
    @Schema(example = "Machine Learning Models for Early Disease Detection in Clinical Settings")
    private String title;

    @NotBlank(message = "VALIDATION.PAPER_CREATION.ABSTRACT_TEXT.NOT_BLANK")
    @Size(min = 50, max = 5000, message = "VALIDATION.PAPER_CREATION.ABSTRACT_TEXT.SIZE")
    @Schema(example = "This study examines the correlation between remote work, individual productivity, and employee mental health. A mixed methodology was adopted, combining a quantitative questionnaire with 500 employees and ten semi-structured qualitative interviews.")
    private String abstractText;

    @Schema(example = "https://storage.scholaria.com/papers/thumbnails/paper-123.png")
    private String thumbnailUrl;

    @NotNull(message = "VALIDATION.PAPER_CREATION.CATEGORY.NOT_BLANK")
    @Enumerated(EnumType.STRING)
    @Schema(example = "COMPUTER_SCIENCE")
    private PaperCategory category;

    @NotBlank(message = "VALIDATION.PAPER_CREATION.FILE_TYPE.NOT_BLANK")
    @Pattern(regexp = "^(PDF|DOCX|DOC)$",
            message = "VALIDATION.PAPER_CREATION.FILE_TYPE.PATTERN")
    @Schema(example = "PDF", allowableValues = {"PDF", "DOCX", "DOC"})
    private String fileType;

    @NotBlank(message = "VALIDATION.PAPER_CREATION.DOCUMENT.NOT_BLANK")
    private String document;

    @NotNull(message = "VALIDATION.PAPER_CREATION.FILE_SIZE.NOT_NULL")
    @Min(value = 1, message = "VALIDATION.PAPER_CREATION.FILE_SIZE.MIN")
    @Max(value = 50, message = "VALIDATION.PAPER_CREATION.FILE_SIZE.MAX") // 50MB max
    @Schema(example = "2")
    private long fileSize;

    @NotEmpty(message = "VALIDATION.PAPER_CREATION.AUTHORS.NOT_EMPTY")
    @Size(min = 1, max = 20, message = "VALIDATION.PAPER_CREATION.AUTHORS.SIZE")
    @Schema(description = "List of author IDs (User UUIDs)")
    private Set<String> authorIds = new HashSet<>();


    @Size(max = 20, message = "VALIDATION.PAPER_CREATION.KEYWORDS.SIZE")
    @Schema(example = "[\"Machine Learning\", \"Disease Detection\", \"Healthcare AI\"]")
    private Set<String> keywords = new HashSet<>();

}
