package uz.pdp.appsendemailmessage.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/10/2021
 */
@Data
public class RegisterReceiveModel {

    @NotNull
    @Size(min = 3, max = 50)
    private String firstname;

    @NotNull
    @Size(min = 5, max = 50)
    private String lastname;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}
