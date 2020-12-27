export class ResetPassword {
    email: String;
    newPassword: String;

    constructor(email: String, newPassword: String) {
        this.email = email;
        this.newPassword = newPassword;
    }
}
