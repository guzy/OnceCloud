/*
 * Copyright (c) Citrix Systems, Inc.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of version 2 of the GNU General Public License as published
 * by the Free Software Foundation, with the additional linking exception as
 * follows:
 * 
 *   Linking this library statically or dynamically with other modules is
 *   making a combined work based on this library. Thus, the terms and
 *   conditions of the GNU General Public License cover the whole combination.
 * 
 *   As a special exception, the copyright holders of this library give you
 *   permission to link this library with independent modules to produce an
 *   executable, regardless of the license terms of these independent modules,
 *   and to copy and distribute the resulting executable under terms of your
 *   choice, provided that you also meet, for each linked independent module,
 *   the terms and conditions of the license of that module. An independent
 *   module is a module which is not derived from or based on this library. If
 *   you modify this library, you may extend this exception to your version of
 *   the library, but you are not obligated to do so. If you do not wish to do
 *   so, delete this exception statement from your version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.Xen.API;

import java.net.URL;
import java.util.Map;
import java.util.TimeZone;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcHttpClientConfig;

import org.Xen.API.Types.BadServerResponse;
import org.Xen.API.Types.SessionAuthenticationFailed;
import org.Xen.API.Types.XenAPIException;

/**
 * Represents a connection to a XenServer. Creating a new instance of this class initialises a new XmlRpcClient that is
 * then used by all method calls: each method call in xenapi takes a Connection as a parameter, composes an XMLRPC
 * method call, and dispatches it on the Connection's client via the dispatch method.
 */
public class Connection
{
    /**
     * The version of the bindings that this class belongs to.
     */
    public static final String BINDINGS_VERSION = "6.0.0-1";
    
    /**
     * true if the connection is to the Rio edition of XenServer. Certain function calls are not allowed.
     * 
     * @deprecated Use getAPIVersion() instead.
     */
    @Deprecated
    public Boolean rioConnection = false;

    private APIVersion apiVersion;

    /**
     * Updated when Session.login_with_password() is called.
     */
    public APIVersion getAPIVersion()
    {
        return apiVersion;
    }

    /**
     * The opaque reference to the session used by this connection
     */
    private String sessionReference;

    /**
     * As seen by the xmlrpc library. From our point of view it's a server.
     */
    private final XmlRpcClient client;

    private final boolean deprecatedConstructorUsed;

    /**
     * Creates a connection to a particular server using a given username and password. This object can then be passed
     * in to any other API calls.
     * 
     * This constructor calls Session.loginWithPassword, passing itself as the first parameter.
     * 
     * When this constructor is used, a call to dispose() (also called in the Connection's finalizer) will attempt a
     * Session.logout on this connection.
     * 
     * @deprecated Use a constructor that takes a URL as the first parameter instead.
     */
    @Deprecated
    public Connection(String client, String username, String password) throws java.net.MalformedURLException,
        XmlRpcException, BadServerResponse, SessionAuthenticationFailed, XenAPIException
    {
        deprecatedConstructorUsed = true;

        // To login normally we call login_with_password(username, password, "1.X").  On rio this call fails and we
        // should use login_with_password(username,password) instead, and note that we are talking to a rio host so that we
        // can refuse to make certain miami-specific calls
        final String ApiVersion = APIVersion.latest().toString();
        this.client = getClientFromURL(new URL(client));
        try
        {
            //first try to login the modern way
            this.sessionReference = loginWithPassword(this.client, username, password, ApiVersion);
        } catch (BadServerResponse e)
        {
            //oops, something went wrong
            String[] errDesc = e.errorDescription;
            //was the problem that the host was running rio? If so it will have complained that it got three parameters
            //instead of two. Let us carefully verify the details of this complaint
            if (0 == errDesc[0].compareTo("MESSAGE_PARAMETER_COUNT_MISMATCH")
                    && 0 == errDesc[1].compareTo("session.login_with_password")
                    && 0 == errDesc[2].compareTo("2")
                    && 0 == errDesc[3].compareTo("3"))
            {
                //and if so, we can have another go, using the older login method, and see how that goes.
                this.sessionReference = loginWithPassword(this.client, username, password);
                //success!. Note that we are talking to an old host on this connection
                this.rioConnection = true;
            } else
            {
                //Hmm... Can't solve this here. Let upstairs know about the problem.
                throw e;
            }
        }

    }

    /**
     * Creates a connection to a particular server using a given username and password. This object can then be passed
     * in to any other API calls.
     * 
     * Note this constructor does NOT call Session.loginWithPassword; the programmer is responsible for calling it,
     * passing the Connection as a parameter. No attempt to connect to the server is made until login is called.
     * 
     * When this constructor is used, a call to dispose() will do nothing. The programmer is responsible for manually
     * logging out the Session.
     */
    public Connection(URL url)
    {
        deprecatedConstructorUsed = false;

        this.client = getClientFromURL(url);
    }

    /**
     * Creates a connection to a particular server using a given username and password. This object can then be passed
     * in to any other API calls.
     * 
     * The additional sessionReference parameter must be a reference to a logged-in Session. Any method calls on this
     * Connection will use it. This constructor does not call Session.loginWithPassword, and dispose() on the resulting
     * Connection object does not call Session.logout. The programmer is responsible for ensuring the Session is logged
     * in and out correctly.
     */
    public Connection(URL url, String sessionReference)
    {
        deprecatedConstructorUsed = false;

        this.client = getClientFromURL(url);
        this.sessionReference = sessionReference;
    }

    protected void finalize() throws Throwable
    {
        dispose();
        super.finalize();
    }

    /**
     * Nothrow guarantee.
     */
    public void dispose()
    {
        if (!deprecatedConstructorUsed)
        {
            // We only need to do the Session.logout if they used the old deprecated constructor.
            return;
        }

        try
        {
            if (sessionReference != null)
            {
                String method_call = "session.logout";
                Object[] method_params = { Marshalling.toXMLRPC(this.sessionReference) };
                client.execute(method_call, method_params);
                sessionReference = null;
            }
        }
        catch (XmlRpcException exn)
        {
        }
    }

    /**
     * @deprecated The programmer is now responsible for calling login/logout themselves.
     */
    @SuppressWarnings("rawtypes")
	@Deprecated
    private static String loginWithPassword(XmlRpcClient client, String username, String password)
            throws BadServerResponse, XmlRpcException, SessionAuthenticationFailed
    {
        String method_call = "session.login_with_password";
        Object[] method_params = { Marshalling.toXMLRPC(username), Marshalling.toXMLRPC(password) };
        Map response = (Map) client.execute(method_call, method_params);
        if (response.get("Status").equals("Success"))
        {
            return (String) response.get("Value");
        } else if (response.get("Status").equals("Failure"))
        {
            Object[] error = (Object[]) response.get("ErrorDescription");
            if (error[0].equals("SESSION_AUTHENTICATION_FAILED"))
            {
                throw new SessionAuthenticationFailed();
            }
        }
        throw new BadServerResponse(response);
    }

    /**
     * @deprecated The programmer is now responsible for calling login/logout themselves.
     */
    @SuppressWarnings("rawtypes")
	@Deprecated
    private static String loginWithPassword(XmlRpcClient client, String username, String password, String ApiVersion)
            throws BadServerResponse, XmlRpcException, SessionAuthenticationFailed
    {
        String method_call = "session.login_with_password";
        Object[] method_params = { Marshalling.toXMLRPC(username), Marshalling.toXMLRPC(password),
                Marshalling.toXMLRPC(ApiVersion) };
        Map response = (Map) client.execute(method_call, method_params);
        if (response.get("Status").equals("Success"))
        {
            return (String) response.get("Value");
        } else if (response.get("Status").equals("Failure"))
        {
            Object[] error = (Object[]) response.get("ErrorDescription");
            if (error[0].equals("SESSION_AUTHENTICATION_FAILED"))
            {
                throw new SessionAuthenticationFailed();
            }
        }
        throw new BadServerResponse(response);
    }

    private XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

    public XmlRpcClientConfigImpl getConfig()
    {
	return config;
    }
    private XmlRpcClient getClientFromURL(URL url)
    {
        config.setTimeZone(TimeZone.getTimeZone("UTC"));
        config.setServerURL(url);
        config.setConnectionTimeout(3000);
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        return client;
    }

    /*
     * Because the binding calls are constructing their own parameter lists, they need to be able to get to 
     * the session reference directly. This is all rather ugly and needs redone
     * Changed to public to allow easier integration with HTTP-level streaming interface,
     * see CA-15447
     */
    public String getSessionReference()
    {
        return this.sessionReference;
    }

    /**
     * The (auto-generated parts of) the bindings dispatch XMLRPC calls on this Connection's client through this method.
     */
    @SuppressWarnings("rawtypes")
	Map dispatch(String method_call, Object[] method_params) throws XmlRpcException, XenAPIException
    {
        Map response = (Map) client.execute(method_call, method_params);

        if (!deprecatedConstructorUsed)
        {
            // We are using the new-style constructor which doesn't perform login.
            // Set this Connection's Session reference from the value returned on the wire.
            if (method_call.equals("session.login_with_password") &&
                response.get("Status").equals("Success"))
            {
                // Store the Session reference and ask the server what the
		// API version it's using is.
                Session session = Types.toSession(response.get("Value"));
		sessionReference = session.ref;
//                setAPIVersion(session);
            }
            else if (method_call.equals("session.slave_local_login_with_password") &&
                     response.get("Status").equals("Success"))
            {
                // Store the Session reference and assume API version 1.2.
                sessionReference = Types.toSession(response.get("Value")).ref;
                apiVersion = APIVersion.API_1_2;
            }
            else if (method_call.equals("session.logout"))
            {
                // Work around a bug in XenServer 5.0 and below.
                // session.login_with_password should have rejected us with
                // HOST_IS_SLAVE, but instead we don't find out until later.
                // We don't want to leak the session, so we need to log out
                // this session from the master instead.
                if (response.get("Status").equals("Failure"))
                {
                    Object[] error = (Object[]) response.get("ErrorDescription");
                    if (error.length == 2 && error[0].equals("HOST_IS_SLAVE"))
                    {
                        try
                        {
                            URL client_url =
                                ((XmlRpcHttpClientConfig)client.getClientConfig()).getServerURL();
                            Connection tmp_conn =
                                new Connection(new URL(client_url.getProtocol(),
                                                       (String)error[1],
                                                       client_url.getPort(),
                                                       client_url.getFile()));
                            tmp_conn.sessionReference = sessionReference;
                            try
                            {
                                Session.logout(tmp_conn);
                            }
                            finally
                            {
                                tmp_conn.dispose();
                            }
                        }
                        catch (Exception exn2)
                        {
                            // Ignore -- we're going to throw HostIsSlave anyway.
                        }
                    }
                }

                // Clear the stored Session reference.
                this.sessionReference = null;
            }
        }

        return Types.checkResponse(response);
    }


//    private void setAPIVersion(Session session) throws XenAPIException, XmlRpcException
//    {
//        try
//        {
//            long major = session.getThisHost(this).getAPIVersionMajor(this);
//            long minor = session.getThisHost(this).getAPIVersionMinor(this);
//            apiVersion = APIVersion.fromMajorMinor(major, minor);
//        }
//        catch (BadServerResponse exn)
//        {
//            apiVersion = APIVersion.API_1_1;
//        }
//    }
}
