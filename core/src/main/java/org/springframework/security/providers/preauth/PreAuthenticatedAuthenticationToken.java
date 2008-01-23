package org.springframework.security.providers.preauth;

import org.springframework.security.providers.AbstractAuthenticationToken;
import org.springframework.security.GrantedAuthority;


/**
 * {@link org.springframework.security.Authentication} implementation for pre-authenticated
 * authentication.
 *
 * @author Ruud Senden
 * @since 2.0
 */
public class PreAuthenticatedAuthenticationToken extends AbstractAuthenticationToken {
    private Object principal;

    private Object credentials;

    /**
     * Constructor used for an authentication request. The {@link
     * org.springframework.security.Authentication#isAuthenticated()} will return
     * <code>false</code>.
     *
     * @TODO Should we have only a single credentials parameter here? For
     *       example for X509 the certificate is used as credentials, while
     *       currently a J2EE username is specified as a principal but could as
     *       well be set as credentials.
     *
     * @param aPrincipal
     *            The pre-authenticated principal
     * @param aCredentials
     *            The pre-authenticated credentials
     */
    public PreAuthenticatedAuthenticationToken(Object aPrincipal, Object aCredentials) {
        super(null);
        this.principal = aPrincipal;
        this.credentials = aCredentials;
    }

    /**
     * Constructor used for an authentication response. The {@link
     * org.springframework.security.Authentication#isAuthenticated()} will return
     * <code>true</code>.
     *
     * @param aPrincipal
     *            The authenticated principal
     * @param anAuthorities
     *            The granted authorities
     */
    public PreAuthenticatedAuthenticationToken(Object aPrincipal, Object aCredentials, GrantedAuthority[] anAuthorities) {
        super(anAuthorities);
        this.principal = aPrincipal;
        this.credentials = aCredentials;
        setAuthenticated(true);
    }

    /**
     * Get the credentials
     */
    public Object getCredentials() {
        return this.credentials;
    }

    /**
     * Get the principal
     */
    public Object getPrincipal() {
        return this.principal;
    }

}
