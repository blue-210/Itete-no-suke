package app.itetenosuke.domain.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignupForm {
	@NotBlank
	@Length(max=254)
	@Email
	private String email;
	
	@NotBlank
	@Length(min=5, max=50)
	@Pattern(regexp="[0-9a-zA-Z¥-]{5,50}")
	private String userName;
	
	@NotBlank
	@Length(min=8, max=72)
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z¥-]{8,72}")
	private String password;
}
