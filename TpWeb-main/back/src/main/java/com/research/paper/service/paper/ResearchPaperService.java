package com.research.paper.service.paper;

import com.research.paper.dto.request.paper.researchPaper.PaperCreationRequest;
import com.research.paper.dto.request.paper.researchPaper.PaperUpdateRequest;
import com.research.paper.dto.response.paper.ResearchPaperResponse;

import java.util.List;

public interface ResearchPaperService {
    void addResearchPaper(PaperCreationRequest paperCreationRequest , String userId);
    void updateResearchPaper(PaperUpdateRequest paperUpdateRequest , String paperId);
    void validateResearchPaper(String paperId);
    void rejectResearchPaper(String paperId);
    void deleteResearchPaper(String paperId);
    List<ResearchPaperResponse> getAllResearchPaper();
    ResearchPaperResponse getResearchPaperById(String paperId);
}
