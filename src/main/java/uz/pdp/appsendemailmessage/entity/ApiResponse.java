package uz.pdp.appsendemailmessage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/10/2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {

    private String massage;

    private boolean state;

    private Object object;

    public ApiResponse(String massage, boolean state) {
        this.massage = massage;
        this.state = state;
    }
}
