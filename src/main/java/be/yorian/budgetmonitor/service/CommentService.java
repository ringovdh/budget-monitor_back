package be.yorian.budgetmonitor.service;

import be.yorian.budgetmonitor.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CommentService {
	
	public List<Comment> getComments();
	public Page<Comment> getCommentsBySearchterm(String searchterm, int page, int size);
	public Optional<Comment> getCommentById(Long comment_id);
	public void saveComment(Comment comment);
	public void deleteComment(Long comment_id);
	public void updateComment(Long commentId, Comment comment);
}
