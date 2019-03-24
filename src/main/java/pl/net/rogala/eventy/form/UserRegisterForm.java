/**
 * Form for user registration
 */

package pl.net.rogala.eventy.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRegisterForm {

    @NotNull(message = "Adres email jest wymagany!")
    @Email(message = "Niepoprawny format adresu email!")
    private String email;

    @NotNull(message = "Hasło jest wymagane!")
    @NotBlank(message = "Hasło jest wymagane!")
    @Size(min = 8, max = 30, message = "Hasło powinno mieć od {min} do {max} znaków!")
    private String password;

    @NotBlank(message = "Nick jest wymagany!")
    @Size(max = 20, message = "Twój nick nie może mieć więcej niż {max} znaków!")
    private String nick;
}
