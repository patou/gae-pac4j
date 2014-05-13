package org.pac4j.gae.client;

import org.pac4j.core.credentials.Credentials;

import com.google.appengine.api.users.User;

public class GaeUserCredentials extends Credentials {
	private static final long serialVersionUID = -135519596194113906L;
	
	private User user;

	public GaeUserCredentials() {
		setClientName("GaeUserClient");
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}
