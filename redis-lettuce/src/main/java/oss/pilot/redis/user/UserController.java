package oss.pilot.redis.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aa/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add/{id}/{name}")
    public User add(@PathVariable("id") final String id, @PathVariable("name") final  String name ){
        userRepository.save(new User(id, name, 20000L));

        return userRepository.findById(id);
    }

    @GetMapping("/update/{id}/{name}")
    public User update(@PathVariable("id") final String id, @PathVariable("name") final  String name ){
        userRepository.save(new User(id, name, 1000L));

        return userRepository.findById(id);
    }

    @GetMapping("/all")
    public Map<String, User> all(){

        return userRepository.findAll();
    }
}