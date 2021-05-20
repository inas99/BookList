package com.task1.task1.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString()
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

}
