package org.uy.sdm.pasman.exceptions;

public class CredentialNotFoundException extends RuntimeException {

	public CredentialNotFoundException(){
		super("Credential not found.");
	}
}
