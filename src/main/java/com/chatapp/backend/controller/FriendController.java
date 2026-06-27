package com.chatapp.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.backend.model.Friend;
import com.chatapp.backend.model.FriendRequest;
import com.chatapp.backend.repository.FriendRepository;
import com.chatapp.backend.repository.FriendRequestRepository;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin("*")
public class FriendController {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendRepository friendRepository;

    // Send Friend Request
    @PostMapping("/send")
    public String sendRequest(@RequestBody FriendRequest request) {

        request.setStatus("PENDING");
        friendRequestRepository.save(request);

        return "Friend Request Sent";
    }

    // View Requests
    @GetMapping("/requests/{receiverId}")
    public List<FriendRequest> getRequests(@PathVariable String receiverId) {

        return friendRequestRepository.findByReceiverId(receiverId);
    }

    // View All Requests
    @GetMapping("/all")
    public List<FriendRequest> getAllRequests() {

        return friendRequestRepository.findAll();
    }

    // Accept Friend Request
    @PutMapping("/accept/{requestId}")
    public String acceptRequest(@PathVariable String requestId) {

        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);

        if (request == null) {
            return "Request Not Found";
        }

        request.setStatus("ACCEPTED");
        friendRequestRepository.save(request);

        String senderId = request.getSenderId();
        String receiverId = request.getReceiverId();

        boolean alreadyFriends =
                friendRepository.existsByUser1AndUser2(senderId, receiverId)
                        || friendRepository.existsByUser1AndUser2(receiverId, senderId);

        if (!alreadyFriends) {

            Friend friend = new Friend();
            friend.setUser1(senderId);
            friend.setUser2(receiverId);

            friendRepository.save(friend);
        }

        return "Friend Request Accepted";
    }

    // Reject Friend Request
    @PutMapping("/reject/{requestId}")
    public String rejectRequest(@PathVariable String requestId) {

        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);

        if (request == null) {
            return "Request Not Found";
        }

        request.setStatus("REJECTED");
        friendRequestRepository.save(request);

        return "Friend Request Rejected";
    }

    // Get Friends List
    @GetMapping("/list/{userId}")
    public List<Friend> getFriends(@PathVariable String userId) {

        return friendRepository.findByUser1OrUser2(userId, userId);
    }
}