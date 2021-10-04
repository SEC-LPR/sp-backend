package com.sec.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDto {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;
}
