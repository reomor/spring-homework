package task13.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import task13.annotation.FieldsConfirmation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@FieldsConfirmation(
        field = "password",
        fieldConfirmation = "passwordConfirmation",
        message = "Passwords must match"
)
public class RegistrationFormDto {

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank
    @Size(min = 4, max = 100, message = "Password size must be at least 4, less than 100 characters")
    private String password;

    @NotBlank
    @Size(min = 4, max = 100, message = "Password size must be at least 4, less than 100 characters")
    private String passwordConfirmation;
}
