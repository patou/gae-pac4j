package org.pac4j.gae.client;

import org.pac4j.core.profile.AttributesDefinition;
import org.pac4j.core.profile.converter.Converters;

/**
 * This class defines the attributes of the {@link GaeUserServiceProfile}.
 * 
 * @author Patrice de Saint Steban
 * @since 1.0.0
 */
public class GaeUserServiceAttributesDefinition extends AttributesDefinition {
    
    public static final String EMAIL = "email";
    public static final String DISPLAYNAME = "display_name";
    
    public static final GaeUserServiceAttributesDefinition gaeUserServiceAttibuteDefinition = new GaeUserServiceAttributesDefinition();
    
    public GaeUserServiceAttributesDefinition() {
        addAttribute(DISPLAYNAME, Converters.stringConverter);
        addAttribute(EMAIL, Converters.stringConverter);
    }
}