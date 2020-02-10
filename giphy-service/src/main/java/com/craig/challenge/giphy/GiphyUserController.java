package com.craig.challenge.giphy;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class GiphyUserController {
    private final UserRepository userRepository;

    public GiphyUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User save(Principal principal) {
        // TODO throw not found
        return userRepository.findById(principal.getName())
                .orElse(null);
    }

//    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public User save(@RequestBody SaveDTO saveDTO, Principal principal) {
//        User findById = userRepository.findById(principal.getName())
//                .orElse(new User(principal.getName(), new ArrayList<>()));
//
//        findById.getGiphyIds().add(saveDTO.getGiphyId());
//        return userRepository.save(findById);
//    }
}
