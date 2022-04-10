package sugimomoto.springsecurity2.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import sugimomoto.springsecurity2.validator.UniqueLogin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UniqueLogin
    @Size(min = 2, max = 20)
    private String username;

    @Size(min = 4, max = 255)
    private String password;


    @NotBlank
    @Email
    private String email;

    private int gender;
    private boolean admin;
    private String role;
    private boolean active = true;
}
