package kg.neobis.neo_land.controller;

import kg.neobis.neo_land.dto.UserDto;
import kg.neobis.neo_land.service.UserService;
import io.swagger.annotations.Api;
import kg.neobis.neo_land.config.SwaggerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = SwaggerConfig.USER)
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/findAll")
    public List<UserDto> findAll(){
        return userService.findAllUser();
    }

    @GetMapping("/findById")
    public UserDto findById(@RequestParam Long id){
        return userService.getUserById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
