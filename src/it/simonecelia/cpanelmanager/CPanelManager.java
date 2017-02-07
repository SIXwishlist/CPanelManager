package it.simonecelia.cpanelmanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public final class CPanelManager {
	
	private enum Protocol {
		SSH, TS;
	}

	public static final void main(String[] args) {
		Protocol[] protocols = Protocol.values();
		InetAddress protocolIP = null;
		InetAddress localExtermalIP = null;
		
		System.out.print("Getting home IP...");
		localExtermalIP = getLocalExternalIP();
		System.out.println(" Done: " + localExtermalIP.getHostAddress());
		
		for (int i = 0; i < protocols.length; i++) {
			System.out.print("Getting " + protocols[i].name() + " IP ...");
			protocolIP = getCPanelNrowIP(i);
			System.out.println(" Done: " + protocolIP.getHostAddress());
			if (protocolIP.getHostAddress() == null || (protocolIP.getHostAddress().compareToIgnoreCase(localExtermalIP.getHostAddress()) != 0)) {
				System.out.print("IPs are differents! setting new one...");
				setCPanelNrowIP(localExtermalIP, i);
				System.out.println(" Done.");
			}
			else {
				System.out.println("IPs are the same, nothing to do.");
			}
		}
	}

	/*
	 * using aws to get external ip address
	 */
	private static InetAddress getLocalExternalIP() {
		InetAddress inetAddress = null;
		try {
			URL url = new URL(EProp.CHECKIPAWSURL.sValue());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
			inetAddress = InetAddress.getByName(bufferedReader.readLine());
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		return inetAddress;
	}
  
  	/*
	 * @param offset of rows after default ones
	 */
	private static void setCPanelNrowIP(InetAddress inetAddress, int offset) {
		try {
			UserAgentX userAgent = goToPanel();
			userAgent.visitX(EProp.URL2.sValue());
			userAgent.doc.getForm(EProp.FORMID1.iValue() + offset).submit();
			userAgent.doc.getForm(EProp.FORMID1.iValue() + offset).set(EProp.FORMINPUTNAME0.sValue(), inetAddress.getHostAddress()).submit();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/*
	 * @param offset of rows after default ones
	 */
	private static InetAddress getCPanelNrowIP(int offset) {
		try {
			return InetAddress.getByName(goToPanel().doc.getForm(EProp.FORMID1.iValue() + offset).getElement().getElement(EProp.ELEMENTID0.iValue()).getAt(EProp.FORMINPUTNAME1.sValue()));
		}
		catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	private static UserAgentX goToPanel() {
		UserAgentX userAgent = new UserAgentX();
		try {
			userAgent.visitX(EProp.URL0.sValue());
			userAgent.visitX(EProp.URL1.sValue());
			userAgent.doc.getForm(EProp.FORMID0.iValue()).submit(EProp.GOTOPANEL.sValue());
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		return userAgent;
	}
}
