export class RegisterUserDTO {

    email: String;
    name: String;
    city: String;
    country: String;
    password: String;
    passwordConfirm: String;

    constructor(email: String, name: String, city: String, country: String, password: String, passwordConfirm: String) {
        this.email = email;
        this.name = name;
        this.city = city;
        this.country = country;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
