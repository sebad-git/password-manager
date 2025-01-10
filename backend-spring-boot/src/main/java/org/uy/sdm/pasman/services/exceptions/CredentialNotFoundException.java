package org.uy.sdm.pasman.services.exceptions;

public class CredentialNotFoundException extends RuntimeException {

	public CredentialNotFoundException(){
		super("Credential not found.");
	}
}
