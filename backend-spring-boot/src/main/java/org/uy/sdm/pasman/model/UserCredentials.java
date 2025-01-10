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
import org.uy.sdm.pasman.dto.NewUserCredentialDto;

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
	private String name;
	private String url;

	@Version
	private int version;
}
