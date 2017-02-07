package it.simonecelia.cpanelmanager;

import java.util.Properties;

public enum EProp {
	URL0, REALM, PROTECTEDDIR, USER, PASSWORD, URL1, PROXYHOST, PROXED, GOTOPANEL, 
	FORMID0, FORMID1, FORMINPUTNAME0, DEBUGMODE, CHECKSSLCERTS, SHOWTRAVEL,
	CHECKIPAWSURL, ELEMENTID0, FORMINPUTNAME1, URL2, PROXYPORT;
	
    public String sValue() {
    	Properties properties = new Properties();
    	try {
            properties.load(getClass().getClassLoader().getResourceAsStream(("config.properties")));
        } 
        catch (Exception exception) {
        	exception.printStackTrace();
        }
        return properties.getProperty(this.name());
    }
    
    public int iValue() {
    	return Integer.parseInt(sValue());
    }
    
    public boolean bValue() {
    	return Boolean.getBoolean(sValue());
    }
}
