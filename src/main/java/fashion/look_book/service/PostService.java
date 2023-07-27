package fashion.look_book.service;

import fashion.look_book.domain.*;
import fashion.look_book.repository.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;

    @Transactional
    public Long savePost(Post post) {
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public Long updatePost(Long postId, String title, String content) {
        Post findPost = postRepository.findOne(postId);
        findPost.update_post(title, content);

        return findPost.getId();
    }

    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }

    public List<Post> findAllPost() {
        return postRepository.findAllPost();
    }

    @Transactional
    public void delete_Post (Long postId) {
        postRepository.deletePost(postId);
        postImgRepository.postImgDelete(postId);
    }

    public List<Post> MyPagePost(Long memberId) {
        List<Post> posts = postRepository.MyPagePost(memberId);
        return posts;
    }
}
