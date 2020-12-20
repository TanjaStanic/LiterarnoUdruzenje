package upp.la.dto;

import upp.la.model.Role;

import java.util.HashSet;
import java.util.Set;

public class RegisterDto {

  private String username;

  private String password;

  private String firstName;

  private String lastName;

  private String email;

  private String city;

  private String country;

    private Set<RoleDto> roles = new HashSet<RoleDto>();
}
