package be.yorian.domain.controller;

import be.yorian.domain.entity.Comment;
import be.yorian.domain.response.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CommentController {

    List<Comment> getComments();
    ResponseEntity<CustomResponse> getCommentBySearchterm(Optional<String> searchterm,
                                                          Optional<Integer> page,
                                                          Optional<Integer> size);
    Optional<Comment> getCommentById(Long comment_id);
    void createComment(Comment comment);
    void updateComment(Long comment_id,
                       Comment comment);
    void deleteComment(Long comment_id);
}
