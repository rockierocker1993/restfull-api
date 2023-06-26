package id.rockierocker.controller;

import id.rockierocker.dto.RegisterDto;
import id.rockierocker.dto.UpdateNameDto;
import id.rockierocker.entity.User;
import id.rockierocker.service.UserService;
import id.rockierocker.util.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "User")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterDto user, BindingResult bindingResult){
        ValidatorUtils.throwIfError(bindingResult);
        userService.register(user);
        return "success";
    }

    @GetMapping("/get-name")
    public String getName(){
        return userService.getCurrentName();
    }

    @PutMapping("/update-name")
    public String getName(@RequestBody @Valid UpdateNameDto updateNameDto, BindingResult bindingResult){
        ValidatorUtils.throwIfError(bindingResult);
        userService.updateCurrentName(updateNameDto.getName());
        return "success";
    }
}
