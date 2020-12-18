package com.example.bankacquirer.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String merchantID; // sifrovati

	@Column
	private String merchantPassword; //sifrovati
	
	@Column
	private String email;
	
	@Column
	private String name;
   
    @Column
    private boolean active;
    
    @JsonIgnore
    @OneToOne
    private Account account;
}
