package com.craig.challenge.giphy;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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

//    @PostMapping(path = "/like", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public UserProfile like(@RequestBody SaveDTO saveDTO, Principal principal) {
//        AppUser findById = userRepository.findByUsername(principal.getName())
//                .orElseThrow(() -> new RuntimeException("User Not Found"));
//
//        findById.getGiphyCards()
//                .putIfAbsent(saveDTO.getGiphyId(), new GiphyCard(saveDTO.getGiphyId(), new HashSet<>()));
//
//        AppUser saved = userRepository.save(findById);
//
//        return new UserProfile(saved.getId(), saved.getUsername(), saved.getGiphyCards().values());
//    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "/giphy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void like(@RequestBody GiphyCard inGiphyCard, Principal principal) {
        AppUser findById = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        GiphyCard updatedGiphyCard = new GiphyCard(inGiphyCard.getGiphyId(), inGiphyCard.getCategories());
        findById.getGiphyCards().put(updatedGiphyCard.getGiphyId(), updatedGiphyCard);

        userRepository.save(findById);
    }

}
