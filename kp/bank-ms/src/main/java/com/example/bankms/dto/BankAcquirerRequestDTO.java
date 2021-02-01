package com.example.bankms.dto;

import com.example.bankms.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAcquirerRequestDTO {
    private double amount; 				// salje prodavac KP-u
    @NotNull
    private String merchantId;			// KP cuva kod sebe - ovo se ne menja, prodavac dobija od banke pri registraciji
    @NotNull
    private String merchantPassword;	// KP cuva kod sebe - ovo se ne menja, prodavac dobija od banke pri registraciji
    private long merchantOrderId; 		// salje prodavac KP-u - id transakcije prodavca drugaciji za svaku transakciju
    @NotNull
    private Date merchantTimestamp;		// salje prodavac KP-u - timestamp transakcije drugaciji za svaku transakciju
    @NotNull
    private String successUrl;
    @NotNull
    private String failedUrl;
    @NotNull
    private String errorUrl;
    private String currencyCode;
}
