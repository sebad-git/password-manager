package org.uy.sdm.pasman.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = DataTables.USER_CREDENTIALS)
public class UserCredentials implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(length = 4096, nullable = false)
	private String userName;
	@Column(length = 4096, nullable = false)
	private String userPassword;
	@Column(nullable = false)
	private String vulnerability;
	@OneToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id", referencedColumnName = "id", nullable = false)
	private AccountType accountType;

	@Version
	private int version;
}
