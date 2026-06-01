package com.research.paper.impl.paper;

import com.research.paper.dto.mapper.paper.ResearchPaperMapper;
import com.research.paper.dto.request.paper.researchPaper.PaperCreationRequest;
import com.research.paper.dto.request.paper.researchPaper.PaperUpdateRequest;
import com.research.paper.dto.response.paper.ResearchPaperResponse;
import com.research.paper.enumeration.ErrorCode;
import com.research.paper.enumeration.paper.PaperStatus;
import com.research.paper.exception.BusinessException;
import com.research.paper.entity.paper.ResearchPaper;
import com.research.paper.entity.user.User;
import com.research.paper.repository.User.UserRepository;
import com.research.paper.repository.paper.ResearchPaperRepository;
import com.research.paper.service.paper.ResearchPaperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResearchPaperServiceImpl implements ResearchPaperService {
    private final UserRepository userRepository;
    private final ResearchPaperRepository researchPaperRepository;
    private final ResearchPaperMapper researchPaperMapper;
    @Override
    @Transactional
    public void addResearchPaper(PaperCreationRequest paperCreationRequest, String userId) {
        final ResearchPaper researchPaper = new ResearchPaper();
        researchPaper.setTitle(paperCreationRequest.getTitle());
        researchPaper.setAbstractText(paperCreationRequest.getAbstractText());
        researchPaper.setFileSize(paperCreationRequest.getFileSize());
        researchPaper.setFileType(paperCreationRequest.getFileType());
        researchPaper.setCategory(paperCreationRequest.getCategory());
        researchPaper.setDocument(paperCreationRequest.getDocument());
        researchPaper.setKeywords(paperCreationRequest.getKeywords());
        researchPaper.setStatus(PaperStatus.DRAFT);
        researchPaper.setThumbnail(paperCreationRequest.getThumbnailUrl());
        researchPaper.setCitationCount(0);
        researchPaper.setCommentCount(0);
        researchPaper.setDownloadCount(0);
        researchPaper.setLikeCount(0);
        Set<User> authors = new HashSet<>();
        final Optional<User> correspondingAuthor=userRepository.findById(userId);
        if(correspondingAuthor.isPresent()){
            researchPaper.setCorrespondingAuthor(correspondingAuthor.get());
            if(!paperCreationRequest.getAuthorIds().isEmpty()){
                paperCreationRequest.getAuthorIds().forEach(
                        id -> {
                            final Optional<User> author=userRepository.findById(id);
                            if(author.isPresent()){
                                authors.add(author.get());
                            }else{
                                throw new BusinessException(ErrorCode.AUTHORS_NOT_FOUND);
                            }
                        }
                );
            }else{
                throw new BusinessException(ErrorCode.AUTHORS_LIST_EMPTY);
            }
        }else{
            throw new BusinessException(ErrorCode.AUTHOR_NOT_FOUND);
        }
        researchPaper.setAuthors(authors);
        researchPaperRepository.save(researchPaper);
    }

    @Override
    @Transactional
    public void updateResearchPaper(PaperUpdateRequest paperUpdateRequest, String paperId) {
        ResearchPaper researchPaperSaved = researchPaperRepository.findById(paperId)
                .orElseThrow(()->new BusinessException(ErrorCode.PAPER_NOT_FOUND,paperId));
        this.researchPaperMapper.mergePaperInfo(researchPaperSaved,paperUpdateRequest);
        this.researchPaperRepository.save(researchPaperSaved);
    }

    @Override
    @Transactional
    public void validateResearchPaper(String paperId) {
        ResearchPaper researchPaperSaved = researchPaperRepository.findById(paperId)
                .orElseThrow(()->new BusinessException(ErrorCode.PAPER_NOT_FOUND,paperId));
        researchPaperSaved.setStatus(PaperStatus.PUBLISHED);
        researchPaperSaved.setPublicationDate(LocalDate.now());
        researchPaperRepository.save(researchPaperSaved);
    }

    @Override
    @Transactional
    public void rejectResearchPaper(String paperId) {
        ResearchPaper researchPaperSaved = researchPaperRepository.findById(paperId)
                .orElseThrow(()->new BusinessException(ErrorCode.PAPER_NOT_FOUND,paperId));
        researchPaperSaved.setStatus(PaperStatus.REJECTED);
        researchPaperRepository.save(researchPaperSaved);
    }

    @Override
    @Transactional
    public void deleteResearchPaper(String paperId) {

    }

    @Override
    @Transactional(readOnly = true)
    public List<ResearchPaperResponse> getAllResearchPaper() {
        return researchPaperRepository.findAll()
                .stream().map(researchPaperMapper :: toResearchPaperResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResearchPaperResponse getResearchPaperById(String paperId) {
        return researchPaperRepository.findById(paperId)
                .map(researchPaperMapper :: toResearchPaperResponse)
                .orElseThrow(()-> new BusinessException(ErrorCode.PAPER_NOT_FOUND,paperId));
    }
}
