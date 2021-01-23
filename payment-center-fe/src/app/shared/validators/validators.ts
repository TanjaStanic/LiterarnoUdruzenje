import { FormGroup, ValidationErrors } from '@angular/forms';

export const checkIfPasswordMatch = (control: FormGroup): ValidationErrors | null => {

    if (control.get('password').value === control.get('passwordConfirm').value) {
        return null;
    }

    return { passwordDontMatch: true };
}