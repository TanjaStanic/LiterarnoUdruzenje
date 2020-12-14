package com.example.bankacquirer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bank {
   
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String address;
    
    @Column(unique = true)
    private String email;

    @Column(unique = true)
  //  @Size(min=10, max = 10)
    private String accountNumber;
    
    @Column(unique = true)
   // @Size(min=6, max =6)
    private String uniqueBankNumber;

    @Column
    private String phoneNumber;

    @OneToMany
    private Set<Account> accounts;
	
}
