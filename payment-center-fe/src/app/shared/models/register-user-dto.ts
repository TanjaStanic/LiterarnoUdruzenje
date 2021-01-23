export class RegisterUserDTO {

    email: String;
    name: String;
    taxIdentificationNumber: String;
    companyRegistrationNumber: String;
    password: String;
    passwordConfirm: String;

    constructor(email: String, name: String, taxIdentificationNumber: String, companyRegistrationNumber: String, password: String, passwordConfirm: String) {
        this.email = email;
        this.name = name;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
