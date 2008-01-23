package org.springframework.security.providers.preauth;

import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.userdetails.UserDetails;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

/**
 * <p>
 * Processes a pre-authenticated authentication request. The request will
 * typically originate from a {@link org.springframework.security.ui.preauth.AbstractPreAuthenticatedProcessingFilter}
 * subclass.
 *
 * <p>
 * This authentication provider will not perform any checks on authentication
 * requests, as they should already be pre- authenticated. However, the
 * PreAuthenticatedUserDetailsService implementation may still throw for exampe
 * a UsernameNotFoundException.
 *
 * @author Ruud Senden
 * @since 2.0
 */
public class PreAuthenticatedAuthenticationProvider implements AuthenticationProvider, InitializingBean, Ordered {
    private static final Log logger = LogFactory.getLog(PreAuthenticatedAuthenticationProvider.class);

    private PreAuthenticatedUserDetailsService preAuthenticatedUserDetailsService = null;

    private int order = -1; // default: same as non-ordered

    /**
     * Check whether all required properties have been set.
     */
    public void afterPropertiesSet() {
        Assert.notNull(preAuthenticatedUserDetailsService, "A PreAuthenticatedUserDetailsService must be set");
    }

    /**
     * Authenticate the given PreAuthenticatedAuthenticationToken.
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("PreAuthenticated authentication request: " + authentication);
        }

        UserDetails ud = preAuthenticatedUserDetailsService.getUserDetails((PreAuthenticatedAuthenticationToken) authentication);

        if (ud == null) {
            return null;
        }

        PreAuthenticatedAuthenticationToken result =
                new PreAuthenticatedAuthenticationToken(ud, authentication.getCredentials(), ud.getAuthorities());
        result.setDetails(authentication.getDetails());

        return result;

    }

    /**
     * Indicate that this provider only supports PreAuthenticatedAuthenticationToken (sub)classes.
     */
    public boolean supports(Class authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Set the PreAuthenticatedUserDetailsServices to be used.
     *
     * @param aPreAuthenticatedUserDetailsService
     */
    public void setPreAuthenticatedUserDetailsService(PreAuthenticatedUserDetailsService aPreAuthenticatedUserDetailsService) {
        this.preAuthenticatedUserDetailsService = aPreAuthenticatedUserDetailsService;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int i) {
        order = i;
    }
}
