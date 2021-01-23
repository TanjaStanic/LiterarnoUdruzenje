package com.example.paymentinfo.utils;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.dto.ClientRegistrationDto;
import com.example.paymentinfo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClientValidator implements Validator {
    @Autowired
    private ClientService clientService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    public static final Pattern EMAIL_VERIFICATION =
            Pattern.compile("[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}", Pattern.CASE_INSENSITIVE);

    public static final Pattern PASSWORD_VERIFICATION =
            Pattern.compile("^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=!~&*])"
                    + "(?=\\S+$).{8,20}$");

    @Override
    public void validate(Object o, Errors errors) {
        ClientRegistrationDto client = (ClientRegistrationDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taxIdentificationNumber", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyRegistrationNumber", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");

        if (!validateString(EMAIL_VERIFICATION, client.getEmail())) {
            errors.rejectValue("email", "Invalid.userForm.email");
        }
        if (clientService.findByEmail(client.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (client.getPassword().length() < 8 || client.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!client.getPasswordConfirm().equals(client.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

        if (!validateString(PASSWORD_VERIFICATION, client.getPassword())) {
            errors.rejectValue("password", "Invalid.userForm.password");
        }
    }

    public static boolean validateString(Pattern pattern, String stringToValidate) {
        Matcher matcher = pattern.matcher(stringToValidate);
        return matcher.find();
    }
}