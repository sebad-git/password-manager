package org.uy.sdm.pasman.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
	private String username;
	private String lastName;
	private String firstName;
	private String middleName;
	private String email;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private String externalId;
	private String password;
	private LocalDate createdDate;
	private String displayName;
	@Version
	private int version;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}
}
