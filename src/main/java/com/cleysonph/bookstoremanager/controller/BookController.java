package com.cleysonph.bookstoremanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @ApiOperation(value = "Return a example hello world")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Succss method return")
    })
    @GetMapping
    public String hello() {
        return "Hello Bookstore Manager, I am running an example with PR!!";
    }

}
