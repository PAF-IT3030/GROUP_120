package com.sept.rest.webservices.restfulwebservices.post;
import java.util.Optional;


import java.net.URI;
import java.util.List;

import com.sept.rest.webservices.restfulwebservices.model.DAOUser;
import com.sept.rest.webservices.restfulwebservices.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class PostJpaResource {

	@Autowired
	private PostJpaRepository postJpaRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/jpa/users/")
	public List<DAOUser> getAllUser() {
		return userRepository.findAll();
	}
	@GetMapping("/jpa/users/posts")
	public List<Post> getAll() {
		return postJpaRepository.findAll();
	}

	@GetMapping("/jpa/users/{username}/posts")
	public List<Post> getAllTodos(@PathVariable String username){
		return postJpaRepository.findByUsername(username);
	}

	@GetMapping("/jpa/users/{username}/posts/{id}")
	public Post getTodo(@PathVariable String username, @PathVariable long id){
		return postJpaRepository.findById(id).get();
	}

	@GetMapping("/jpa/users/{username}/posts/{id}/comments")
	public List<PostComment> getComments(@PathVariable String username, @PathVariable long id){
		return postJpaRepository.findById(id).get().getComments();
	}

	@PostMapping("/jpa/users/{username}/posts/{id}/comments")
	public ResponseEntity<Void> PostMapping(@PathVariable String username, @PathVariable long id, @RequestBody PostComment comment){

		Post updatedPost = postJpaRepository.findById(id).get().addComment(comment);
		updatedPost.setUsername(username);

		postJpaRepository.save(updatedPost); // here lies the problem
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/jpa/users/{username}/posts/{postId}/comments/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable String username,
											   @PathVariable long postId,
											   @PathVariable long commentId) {
	
		Optional<Post> postOptional = postJpaRepository.findById(postId);
		if (!postOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
	
		Post post = postOptional.get();
		List<PostComment> comments = post.getComments();
	
		Optional<PostComment> commentOptional = comments.stream()
				.filter(c -> c.getId() == commentId)
				.findFirst();
	
		if (!commentOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
	
		PostComment comment = commentOptional.get();
		comments.remove(comment);
	
		postJpaRepository.save(post);
	
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/jpa/users/{username}/posts/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {
		postJpaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/jpa/users/{username}/posts/{postId}/comments/{commentId}")
	public ResponseEntity<PostComment> updateComment(@PathVariable String username,
													  @PathVariable long postId,
													  @PathVariable long commentId,
													  @RequestBody PostComment comment) {
	
		Optional<Post> postOptional = postJpaRepository.findById(postId);
		if (!postOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
	
		Post post = postOptional.get();
		List<PostComment> comments = post.getComments();
	
		Optional<PostComment> commentOptional = comments.stream()
				.filter(c -> c.getId() == commentId)
				.findFirst();
	
		if (!commentOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
	
		PostComment oldComment = commentOptional.get();
		oldComment.setUsername(comment.getUsername());
		oldComment.setDescription(comment.getDescription());
		oldComment.setTargetDate(comment.getTargetDate());
	
		postJpaRepository.save(post);
	
		return new ResponseEntity<PostComment>(oldComment, HttpStatus.OK);
	}
	
	

	@PutMapping("/jpa/users/{username}/posts/{id}")
	public ResponseEntity<Post> updateTodo(
			@PathVariable String username,
			@PathVariable long id, @RequestBody Post post){
		
		post.setUsername(username);
		post.setComments(postJpaRepository.findById(id).get().getComments());
		Post postUpdated = postJpaRepository.save(post);
		
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}
	
	@PostMapping("/jpa/users/{username}/posts")
	public ResponseEntity<Void> createTodo(
			@PathVariable String username, @RequestBody Post post){
		
		post.setUsername(username);

		Post createdPost = postJpaRepository.save(post);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdPost.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
