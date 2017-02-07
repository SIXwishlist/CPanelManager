package it.simonecelia.cpanelmanager;

import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

public final class UserAgentX extends UserAgent{
	private boolean debugMode = false;
	
	public UserAgentX() {
		super();
		try {
			this.addBasicAuthenticator(EProp.REALM.sValue(), EProp.PROTECTEDDIR.sValue(), EProp.USER.sValue(), EProp.PASSWORD.sValue());
			this.settings.checkSSLCerts = EProp.CHECKSSLCERTS.bValue();
			this.settings.showTravel = EProp.SHOWTRAVEL.bValue();
			this.settings.showHeaders = EProp.DEBUGMODE.bValue();
			this.settings.showWarnings = EProp.DEBUGMODE.bValue();
			
			if (EProp.PROXED.bValue()) {
				this.setProxyHost(EProp.PROXYHOST.sValue());
				this.setProxyPort(EProp.PROXYPORT.iValue());
			}
			
			this.debugMode = EProp.PROXYHOST.bValue();
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	public void visitX(String url) throws ResponseException {
		this.visit(url);
		this.log();
	}
	
	public void log() {
		if (this.debugMode)
			System.out.println(this.doc.innerHTML());
	}
}
