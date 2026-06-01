package com.research.paper.impl.feedback;

import com.research.paper.dto.mapper.feedback.CommentMapper;
import com.research.paper.dto.request.feedback.comment.CommentCreationRequest;
import com.research.paper.dto.request.feedback.comment.CommentUpdateRequest;
import com.research.paper.dto.response.feedback.CommentResponse;
import com.research.paper.enumeration.ErrorCode;
import com.research.paper.exception.BusinessException;
import com.research.paper.entity.feedback.Comment;
import com.research.paper.entity.paper.ResearchPaper;
import com.research.paper.entity.user.User;
import com.research.paper.repository.User.UserRepository;
import com.research.paper.repository.feedback.CommentRepository;
import com.research.paper.repository.paper.ResearchPaperRepository;
import com.research.paper.service.feedback.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ResearchPaperRepository researchPaperRepository;
    private final CommentMapper commentMapper;
    @Override
    public void addComment(CommentCreationRequest commentCreationRequest, String userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND,userId));
        ResearchPaper researchPaper = this.researchPaperRepository.findById(commentCreationRequest.getResearchPaperId())
                .orElseThrow(()->new BusinessException(ErrorCode.PAPER_NOT_FOUND,commentCreationRequest.getResearchPaperId()));
        Comment comment = commentMapper.toComment(commentCreationRequest,true);
        comment.setUser(user);
        comment.setPaper(researchPaper);
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(CommentUpdateRequest commentUpdateRequest, String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new BusinessException(ErrorCode.COMMENT_NOT_FOUND,commentId));
        commentMapper.mergeCommentInfo(comment,commentUpdateRequest);
        commentRepository.save(comment);
    }

    @Override
    public void replyToComment(CommentCreationRequest commentCreationRequest, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND,userId));
        Comment parent = commentRepository.findById(commentCreationRequest.getParentCommentId())
                .orElseThrow(()->new BusinessException(ErrorCode.COMMENT_NOT_FOUND,commentCreationRequest.getParentCommentId()));
        if(!parent.getPaper().getId().equals(commentCreationRequest.getResearchPaperId())){
            throw new BusinessException(ErrorCode.ERROR_COMMENT_MISMATCH);
        }
        Comment reply = commentMapper.toComment(commentCreationRequest,false);
        reply.setUser(user);
        reply.setParentComment(parent);
        reply.setPaper(researchPaperRepository.findById(commentCreationRequest.getResearchPaperId())
                .orElseThrow(()->new BusinessException(ErrorCode.PAPER_NOT_FOUND,commentCreationRequest.getParentCommentId())));
        commentRepository.save(reply);
    }

    @Override
    public void signalComment(String commentId) {

    }

    @Override
    public CommentResponse likeComment(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new BusinessException(ErrorCode.COMMENT_NOT_FOUND,commentId));
        comment.setLikes(comment.getLikes()+1);
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public void deleteComment(String commentId) {

    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll()
                .stream().map(commentMapper :: toCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponse getCommentById(String commentId) {
        return commentRepository.findById(commentId)
                .map(commentMapper :: toCommentResponse)
                .orElseThrow(()-> new BusinessException(ErrorCode.COMMENT_NOT_FOUND,commentId));
    }
}
