package com.cleysonph.bookstoremanager.users.builder;

import java.time.LocalDate;

import com.cleysonph.bookstoremanager.user.dto.UserDTO;
import com.cleysonph.bookstoremanager.user.enums.Gender;

import lombok.Builder;

@Builder
public class UserDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Cleyson Lima";

    @Builder.Default
    private Integer age = 24;

    @Builder.Default
    private Gender gender = Gender.MALE;

    @Builder.Default
    private String email = "cleyson@teste.com";

    @Builder.Default
    private String username = "cleysonph";

    @Builder.Default
    private String password = "senha@123";

    @Builder.Default
    private LocalDate birthDate = LocalDate.of(1996, 6, 8);

    public UserDTO buildUserDTO() {
        return new UserDTO(id, name, age, gender, email, username, password, birthDate);
    }

}
