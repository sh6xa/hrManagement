package uz.pdp.demo.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
public class UserDto {
    @NotNull
    private String fullName;

    @Email
    private String email;

    @NotNull
    private String position;

    @NotNull
    private Integer roleId;
}
