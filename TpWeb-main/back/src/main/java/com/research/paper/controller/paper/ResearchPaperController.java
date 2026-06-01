package com.research.paper.controller.paper;

import com.research.paper.common.Utility;
import com.research.paper.dto.request.paper.researchPaper.PaperCreationRequest;
import com.research.paper.dto.request.paper.researchPaper.PaperUpdateRequest;
import com.research.paper.dto.request.user.ProfileUpdateRequest;
import com.research.paper.dto.response.paper.ResearchPaperResponse;
import com.research.paper.impl.paper.ResearchPaperServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/papers")
@Tag(name = "Research Paper",description = "Research Paper API")
public class ResearchPaperController {
    private final ResearchPaperServiceImpl researchPaperService;
    private final Utility utility;
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void addResearchPaper(
            @RequestBody
            @Valid
            PaperCreationRequest request,
            final Authentication principal){
        this.researchPaperService.addResearchPaper(request,utility.getUserId(principal));
    }
    @PatchMapping("{paper_id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateResearchPaper(
            @RequestBody
            @Valid
            PaperUpdateRequest request,
            @PathVariable(name = "paper_id")
            String paperId){
        this.researchPaperService.updateResearchPaper(request,paperId);
    }
    @PatchMapping("/validate/{paper_id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void validateResearchPaper(
            @PathVariable(name = "paper_id")
            String paperId){
        this.researchPaperService.validateResearchPaper(paperId);
    }
    @PatchMapping("/reject/{paper_id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateResearchPaper(
            @PathVariable(name = "paper_id")
            String paperId){
        this.researchPaperService.rejectResearchPaper(paperId);
    }
    @GetMapping("{paperId}")
    public ResponseEntity<ResearchPaperResponse> getResearchPaperById(
            @PathVariable
            String paperId
    ){
        return ResponseEntity.ok(this.researchPaperService.getResearchPaperById(paperId));
    }
    @GetMapping("")
    public ResponseEntity<List<ResearchPaperResponse>> getAllResearchPaper(){
        return ResponseEntity.ok(this.researchPaperService.getAllResearchPaper());
    }

}
