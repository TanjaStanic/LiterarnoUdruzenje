package com.example.paymentinfo.utils;


import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.dto.PaymentMethodDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PaymentMethodValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return PaymentMethod.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PaymentMethodDto paymentMethod = (PaymentMethodDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "applicationName", "NotEmpty");
    }
}
