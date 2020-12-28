package com.example.luservice.dto;

import com.example.luservice.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    @NotNull
    private Book book;
    private Long id;
    private double price;
    private Date date;
    private int quantity;

}
