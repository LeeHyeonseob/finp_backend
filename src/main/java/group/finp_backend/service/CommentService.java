package group.finp_backend.service;

import group.finp_backend.dto.comment.CommentDto;
import group.finp_backend.entity.Comment;
import group.finp_backend.entity.Post;
import group.finp_backend.entity.User;
import group.finp_backend.repository.CommentRepository;
import group.finp_backend.repository.PostRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentDto createComment(CommentDto commentDto){
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + commentDto.getPostId()));
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + commentDto.getUserId()));

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment  = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPostId(Long postId){
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public CommentDto updateComment(Long commentId, CommentDto commentDto){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        comment.setContent(commentDto.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    public void deleteComment(Long commentId){
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

    private CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .postId(comment.getPost().getId())
                .build();
    }
}
