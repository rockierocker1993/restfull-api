package id.rockierocker.service;

import id.rockierocker.dto.RegisterDto;
import id.rockierocker.entity.User;

public interface UserService {

    User register(RegisterDto registerDto);

    User updateCurrentName(String newName);

    String getCurrentName();

}
