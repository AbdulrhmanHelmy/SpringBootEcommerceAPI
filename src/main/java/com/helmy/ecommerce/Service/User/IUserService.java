package com.helmy.ecommerce.Service.User;

import com.helmy.ecommerce.DTO.UserDTO;

public interface IUserService {

    public String Login(UserDTO userDTO);
    public UserDTO Register(UserDTO userDTO);
}
