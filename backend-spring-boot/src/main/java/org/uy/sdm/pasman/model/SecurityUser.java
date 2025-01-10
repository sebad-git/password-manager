package org.uy.sdm.pasman.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.uy.sdm.pasman.dto.NewUserDto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = DataTables.USERS)
public class SecurityUser implements Serializable, UserDetails {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true, nullable = false)
	private String username;
	private String lastName;
	@Column(nullable = false)
	private String firstName;
	private String middleName;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private boolean accountNonExpired;
	@Column(nullable = false)
	private boolean accountNonLocked;
	@Column(nullable = false)
	private boolean credentialsNonExpired;
	@Column(nullable = false)
	private boolean enabled;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private LocalDate updatedDate;
	@Version
	private int version;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	public SecurityUser(NewUserDto userDto){
		this.setUsername(userDto.user());
		this.setPassword(userDto.password());
		this.setEmail(userDto.email());
		this.setFirstName(userDto.firstName());
		this.setMiddleName(userDto.middleName());
		this.setLastName(userDto.lastName());
		this.setUpdatedDate(LocalDate.now());
		this.setEnabled(true);
		this.setAccountNonLocked(true);
		this.setAccountNonExpired(true);
		this.setCredentialsNonExpired(true);
	}

}
