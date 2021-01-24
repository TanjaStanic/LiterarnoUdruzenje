package com.example.paymentinfo.domain;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client implements Serializable, UserDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private String taxIdentificationNumber; // pib
	@Column
	private String companyRegistrationNumber; // maticni broj
	@Column
	private String email;
	@Column
	private String name;
	@Column
	private String password;		// hesirati
    @Column
    private boolean active;
	@Column
    private boolean enabled;
	@Column
	private String token;
    @Column
	private Timestamp lastPasswordResetDate;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "client_payment_method",
			joinColumns = { @JoinColumn(name = "fk_client") },
			inverseJoinColumns = { @JoinColumn(name = "fk_payment_method") })
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(role);
	}

	@ManyToOne
	private Role role;

	public void addPaymentMethod(PaymentMethod paymentMethod) {
		paymentMethods.add(paymentMethod);
		paymentMethod.getClients().add(this);
	}

	public void removePaymentMethod(PaymentMethod paymentMethod) {
		paymentMethods.remove(paymentMethod);
		paymentMethod.getClients().remove(this);
	}

}
