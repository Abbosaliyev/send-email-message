package uz.pdp.appsendemailmessage.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/10/2021
 */
@Data
public class LoginReceiveModel {

    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;
}
