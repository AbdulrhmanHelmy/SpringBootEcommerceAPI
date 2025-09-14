package com.helmy.ecommerce.Service.User;

import com.helmy.ecommerce.DTO.UserDTO;
import com.helmy.ecommerce.Model.User;

public interface IUserService {

    public String Login(UserDTO userDTO);
    public UserDTO Register(UserDTO userDTO);
    public User getCurrentUser();
}
