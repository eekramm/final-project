package org.wecancodeit.finalproject.controllers;

import javax.annotation.Resource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecancodeit.finalproject.models.CleanUp;
import org.wecancodeit.finalproject.models.CleanUpComment;
import org.wecancodeit.finalproject.models.Comment;
import org.wecancodeit.finalproject.models.User;
import org.wecancodeit.finalproject.repositories.CommentRepository;
import org.wecancodeit.finalproject.repositories.PostCleanUpRepository;
import org.wecancodeit.finalproject.repositories.PreCleanUpRepository;
import org.wecancodeit.finalproject.repositories.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController {
	
	@Resource
	PostCleanUpRepository postCleanUpRepo;
	@Resource
	PreCleanUpRepository preCleanUpRepo;
	
	@Resource
	CommentRepository commentRepo;
	@Resource
	UserRepository userRepo;
	
	@GetMapping("/{id}")
	public Comment getSingleComment(@PathVariable Long id) {
		return commentRepo.findById(id).get();
	}

	@PostMapping("/add/postcleanupcomment")
	public CleanUp addPostCleanUpComment(@RequestBody String body)throws JSONException {
	JSONObject newCleanUpComment = new JSONObject(body);
	String content = newCleanUpComment.getString("cleanUpCommentContent");
	User user = userRepo.findById(Long.parseLong(newCleanUpComment.getString("cleanUpCommentUser"))).get();
	user.increasePointCount(5);
	userRepo.save(user);
	CleanUp cleanUp = postCleanUpRepo.findById(Long.parseLong(newCleanUpComment.getString("cleanUpId"))).get();
	commentRepo.save(new CleanUpComment(content, user, cleanUp));	
		return cleanUp;
	}
	
	@PostMapping("/add/precleanupcomment")
	public CleanUp addPreCleanUpComment(@RequestBody String body)throws JSONException {
	JSONObject newCleanUpComment = new JSONObject(body);
	String content = newCleanUpComment.getString("cleanUpCommentContent");
	User user = userRepo.findById(Long.parseLong(newCleanUpComment.getString("cleanUpCommentUser"))).get();
	user.increasePointCount(5);
	userRepo.save(user);
	CleanUp cleanUp = preCleanUpRepo.findById(Long.parseLong(newCleanUpComment.getString("cleanUpId"))).get();
	commentRepo.save(new CleanUpComment(content, user, cleanUp));	
		return cleanUp;
	}
}


