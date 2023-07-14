package com.zli.user.model;

public interface AppUser {

    Long getId();
    String getUserName(); //his email
    String getFirstName();
    String getSurName();
    String getPassword();
    Role getRole();
}
