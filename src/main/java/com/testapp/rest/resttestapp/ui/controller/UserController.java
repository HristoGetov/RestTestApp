package com.testapp.rest.resttestapp.ui.controller;

import com.testapp.rest.resttestapp.ui.exceptions.UserServiceException;
import com.testapp.rest.resttestapp.ui.model.request.UpdateUserDetailsRequestModel;
import com.testapp.rest.resttestapp.ui.model.request.UserDetailsRequestModel;
import com.testapp.rest.resttestapp.ui.model.response.UserRest;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    Map<String, UserRest> users;

    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest> getUser(@PathVariable String userId){

        if (true)throw new UserServiceException("A User service exception is thrown");
    if (users.containsKey(userId)) {
        return new ResponseEntity<UserRest>(users.get(userId), HttpStatus.OK);
        }else {
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    }

    @GetMapping
    public String getUsers(@RequestParam(value="page", defaultValue = "1") int page,
                           @RequestParam(value="limit", defaultValue = "50") int limit,
                           @RequestParam(value = "sort",defaultValue = "desc",required = false) String sort){

        return "get user was called with page =  " + page + " and limit = " + limit + " and sort = " + sort;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails){

        UserRest returnValue = new UserRest();
        returnValue.setEmail(userDetails.getEmail());
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());

        String userId = UUID.randomUUID().toString();
        returnValue.setUserId(userId);

        if (users ==null) users = new HashMap<>();
        users.put(userId,returnValue);
        return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUser(@PathVariable String userId,@Valid @RequestBody UpdateUserDetailsRequestModel updateUserDetailsRequestModel){
        UserRest storedUserDetails = users.get(userId);
        storedUserDetails.setFirstName(updateUserDetailsRequestModel.getFirstName());
        storedUserDetails.setLastName(updateUserDetailsRequestModel.getLastName());
        users.put(userId,storedUserDetails);
        return storedUserDetails;
    }

    @DeleteMapping(path ="/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        users.remove(id);
        return ResponseEntity.noContent().build();
    }
}
