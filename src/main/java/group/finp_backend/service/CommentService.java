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
@Transactional
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;


    private final PostRepository postRepository;


    private final UserRepository userRepository;

    public CommentDto createComment(Long userId, Long postId, CommentDto commentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        return CommentDto.fromEntity(savedComment);
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(CommentDto::fromEntity)
                .toList();
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
