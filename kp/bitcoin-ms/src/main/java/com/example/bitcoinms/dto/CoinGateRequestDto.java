package com.example.bitcoinms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoinGateRequestDto {
    private String order_id;

    private Double price_amount;

    private String price_currency;

    private String receive_currency;

    private String title;

    private String description;

    private String callback_url;

    private String cancel_url;

    private String success_url;

    private String token;
}
