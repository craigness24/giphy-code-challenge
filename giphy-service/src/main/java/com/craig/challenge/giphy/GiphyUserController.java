package com.craig.challenge.giphy;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;

/**
 * Intended to be the user actions:
 * Liking gifs
 * Adding Categories
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiphyUserController {
    private final UserRepository userRepository;

    public GiphyUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public UserProfile get(Principal principal) {
        AppUser findById = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        return new UserProfile(findById.getId(), findById.getUsername(), findById.getGiphyCards().values());
    }

    @PostMapping(path = "/like", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserProfile like(@RequestBody SaveDTO saveDTO, Principal principal) {
        AppUser findById = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        findById.getGiphyCards()
                .putIfAbsent(saveDTO.getGiphyId(), new GiphyCard(saveDTO.getGiphyId(), new HashSet<>()));

        AppUser saved = userRepository.save(findById);

        return new UserProfile(saved.getId(), saved.getUsername(), saved.getGiphyCards().values());
    }
}
