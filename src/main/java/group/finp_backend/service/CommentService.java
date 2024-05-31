package group.finp_backend.service;

import group.finp_backend.dto.CommentDto;
import group.finp_backend.entity.Comment;
import group.finp_backend.entity.Post;
import group.finp_backend.entity.User;
import group.finp_backend.repository.CommentRepository;
import group.finp_backend.repository.PostRepository;
import group.finp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<CommentDto> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CommentDto createComment(CommentDto commentDto) {
        log.info("댓글 생성 서비스 호출됨 - 게시글 ID: {}", commentDto.getPostId());
        log.info("댓글 생성 서비스 호출됨 - 사용자 이름: {}", commentDto.getUsername());

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());

        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        log.info("게시글 찾음 - ID: {}", post.getId());

        User user = userRepository.findByUsername(commentDto.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        log.info("사용자 찾음 - 이름: {}", user.getUsername());

        comment.setPost(post);
        comment.setUser(user);

        return convertToDto(commentRepository.save(comment));
    }

    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        comment.setContent(commentDto.getContent());
        return convertToDto(commentRepository.save(comment));
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }

    private CommentDto convertToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .username(comment.getUser().getUsername())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .likeCount(comment.getLikeCount())
                .build();
    }
}