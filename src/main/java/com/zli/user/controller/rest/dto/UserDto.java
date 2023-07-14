package com.zli.user.controller.rest.dto;

import com.zli.user.model.AppUser;
import com.zli.user.model.impl.AppUserImpl;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {

    public String firstname;
    public String surname;
    public String email;

    public UserDto(AppUser user) {
        firstname = user.getFirstName();
        surname = user.getSurName();
        email = user.getUserName();
    }
}
