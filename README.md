## What is the gae-pac4j library ? [![Build Status](https://travis-ci.org/pac4j/gae-pac4j.png?branch=master)](https://travis-ci.org/pac4j/gae-pac4j)

The **gae-pac4j** library is a multi-protocols client for the Google App Engine Java implementation.

It supports these 5 protocols on client side :

1. Google App Engine User Service connexion (Google Account)
2. OAuth (1.0 & 2.0)
3. HTTP (form & basic auth authentications)
4. OpenID

It's available under the Apache 2 license and based on my [pac4j](https://github.com/leleuj/pac4j) library.


## Providers supported

<table>
<tr><th>Provider</th><th>Protocol</th><th>Maven dependency</th><th>Client class</th><th>Profile class</th></tr>
<tr><td>Gae UserService</td><td>UserService</td><td>pac4j-core</td><td>GaeUserServiceClient</td><td>GaeUserServiceProfile</td></tr>
<tr><td>DropBox</td><td>OAuth 1.0</td><td>pac4j-oauth</td><td>DropBoxClient</td><td>DropBoxProfile</td></tr>
<tr><td>Facebook</td><td>OAuth 2.0</td><td>pac4j-oauth</td><td>FacebookClient</td><td>FacebookProfile</td></tr>
<tr><td>GitHub</td><td>OAuth 2.0</td><td>pac4j-oauth</td><td>GitHubClient</td><td>GitHubProfile</td></tr>
<tr><td>Google</td><td>OAuth 2.0</td><td>pac4j-oauth</td><td>Google2Client</td><td>Google2Profile</td></tr>
<tr><td>LinkedIn</td><td>OAuth 1.0 & 2.0</td><td>pac4j-oauth</td><td>LinkedInClient & LinkedIn2Client</td><td>LinkedInProfile & LinkedIn2Profile</td></tr>
<tr><td>Twitter</td><td>OAuth 1.0</td><td>pac4j-oauth</td><td>TwitterClient</td><td>TwitterProfile</td></tr>
<tr><td>Windows Live</td><td>OAuth 2.0</td><td>pac4j-oauth</td><td>WindowsLiveClient</td><td>WindowsLiveProfile</td></tr>
<tr><td>WordPress</td><td>OAuth 2.0</td><td>pac4j-oauth</td><td>WordPressClient</td><td>WordPressProfile</td></tr>
<tr><td>Yahoo</td><td>OAuth 1.0</td><td>pac4j-oauth</td><td>YahooClient</td><td>YahooProfile</td></tr>
<tr><td>PayPal</td><td>OAuth 2.0</td><td>pac4j-oauth</td><td>PayPalClient</td><td>PayPalProfile</td></tr>
<tr><td>Vk</td><td>OAuth 2.0</td><td>pac4j-oauth</td><td>VkClient</td><td>VkProfile</td></tr>
<tr><td>Foursquare</td><td>OAuth 2.0</td><td>pac4j-oauth</td><td>FoursquareClient</td><td>FoursquareProfile</td></tr>
<tr><td>Bitbucket</td><td>OAuth 1.0</td><td>pac4j-oauth</td><td>BitbucketClient</td><td>BitbucketProfile</td></tr>
<tr><td>Web sites with basic auth authentication</td><td>HTTP</td><td>pac4j-http</td><td>BasicAuthClient</td><td>HttpProfile</td></tr>
<tr><td>Web sites with form authentication</td><td>HTTP</td><td>pac4j-http</td><td>FormClient</td><td>HttpProfile</td></tr>
<tr><td>Google</td><td>OpenID</td><td>pac4j-openid</td><td>GoogleOpenIdClient</td><td>GoogleOpenIdProfile</td></tr>
</table>


## Technical description

This library has [j2e-pac4j](https://github.com/leleuj/j2e-pac4j) classes

1. the **ClientConfiguration** class gathers all the clients configuration
2. the **ClientFactory** is the interface to implement to define the clients
3. the **ClientsConfigFilter** is an abstract J2E filter in charge of loading clients configuration
4. the **RequiresAuthenticationFilter** is a J2E filter to protect urls and requires authentication for them (redirection to the appropriate provider)
5. the **CallbackFilter** is a J2E filter to handle the callback of the provider after authentication to finish the authentication process
6. the **UserUtils** is an helper class to know if the user is authenticated, his profile and log out him.

and is based on the <i>pac4j-*</i> libraries.

Learn more by browsing the [j2e-pac4j Javadoc](http://www.pac4j.org/apidocs/j2e-pac4j/index.html) and the [pac4j Javadoc](http://www.pac4j.org/apidocs/pac4j/index.html).


## How to use it ?

### Add the required dependencies

If you want to use a specific client support, you need to add the appropriate Maven dependency in the *pom.xml* file :

* for OAuth support, the *pac4j-oauth* dependency is required
* for HTTP support, the *pac4j-http* dependency is required
* for OpenID support, the *pac4j-openid* dependency is required

The OpenID implementation with *pac4j-openid* use java socket, and you must have an billing enabled google app engine application for use this fonctionality.

For example, to add OAuth support, add the following XML snippet :

    <dependency>
      <groupId>org.pac4j</groupId>
      <artifactId>pac4j-oauth</artifactId>
      <version>1.6.0</version>
    </dependency>

As these snapshot dependencies are only available in the [Sonatype snapshots repository](https://oss.sonatype.org/content/repositories/snapshots/org/pac4j/), the appropriate repository must be added in the *pom.xml* file also :

    <repositories>
      <repository>
        <id>sonatype-nexus-snapshots</id>
        <name>Sonatype Nexus Snapshots</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        <releases>
          <enabled>false</enabled>
        </releases>
        <snapshots>
          <enabled>true</enabled>
        </snapshots>
      </repository>
    </repositories>

### Define the clients

All the clients used to communicate with various providers (Facebook, Twitter, a CAS server...) must be defined in a specific class implementing the *org.pac4j.j2e.configuration.ClientsFactory* interface. For example :

    public class MyClientsFactory implements ClientsFactory {
      
      @Override
      public Clients build() {
	    //GAE 
		final GaeUserServiceClient gaeClient = new GaeUserServiceClient();
		
        final FacebookClient facebookClient = new FacebookClient("fbkey", "fbsecret");
        final TwitterClient twitterClient = new TwitterClient("twkey", "twsecret");
        // HTTP
        final FormClient formClient = new FormClient("http://localhost:8080/theForm.jsp", new SimpleTestUsernamePasswordAuthenticator());
        final BasicAuthClient basicAuthClient = new BasicAuthClient(new SimpleTestUsernamePasswordAuthenticator());        
        // OpenID
        final GoogleOpenIdClient googleOpenIdClient = new GoogleOpenIdClient();
        final Clients clients = new Clients("http://localhost:8080/callback", gaeClient, facebookClient, twitterClient, formClient, basicAuthClient, googleOpenIdClient);
        return clients;
      }
    }
    
### Define the "callback filter"

To handle callback from providers, you need to define the appropriate J2E filter and its mapping :

    <filter>
      <filter-name>CallbackFilter</filter-name>
      <filter-class>org.pac4j.gae.filter.CallbackFilter</filter-class>
      <init-param>
      	<param-name>clientsFactory</param-name>
      	<param-value>org.leleuj.config.MyClientsFactory</param-value>
      </init-param>
      <init-param>
      	<param-name>defaultUrl</param-name>
      	<param-value>/</param-value>
      </init-param>
    </filter>
    <filter-mapping>
      <filter-name>CallbackFilter</filter-name>
      <url-pattern>/callback</url-pattern>
      <dispatcher>REQUEST</dispatcher>
    </filter-mapping>

### Protect the urls

You can protect your urls and force the user to be authenticated by a client by using the appropriate filter and mapping. Key parameters are all the clients and the specific client (*clientName*) used by this filter.  
For example, for Gae :

    <filter>
      <filter-name>GAEFilter</filter-name>
      <filter-class>org.pac4j.j2e.filter.RequiresAuthenticationFilter</filter-class>
      <init-param>
       	<param-name>clientsFactory</param-name>
       	<param-value>org.leleuj.config.MyClientsFactory</param-value>
      </init-param>
      <init-param>
       	<param-name>clientName</param-name>
       	<param-value>GaeUserServiceClient</param-value>
      </init-param>
    </filter>
	<filter-mapping>
      <filter-name>GAEFilter</filter-name>
      <url-pattern>/admin/*</url-pattern>
      <dispatcher>REQUEST</dispatcher>
    </filter-mapping>

### Get redirection urls

You can also explicitely compute a redirection url to a provider for authentication by using the *getRedirectionUrl* method and the *ClientsConfiguration* class. For example with Facebook :

    <%
	  WebContext context = new J2EContext(request, response);
	  Clients client = ClientsConfiguration.getClients();
	  GaeUserServiceClient gaeClient = client.findClient(GaeUserServiceClient.class);
	  String redirectionUrl = Client.getRedirectionUrl(context, false, false);
	%>

### Get the user profile

After successful authentication, you can test if the user is authenticated using ```UserUtils.isAuthenticated()``` or get the user profile using ```UserUtils.getUserProfile()```.

The profile returned is a *CommonProfile*, from which you can retrieve the most common properties that all profiles share. 
But you can also cast the user profile to the appropriate profile according to the provider used for authentication.
For example, after a Facebook authentication :
 
    // gae profile
    GaeUserServiceProfile gaeProfile = (GaeUserServiceProfile) commonProfile;

### Demo

A demo with GAE, Facebook, Twitter, form authentication and basic auth authentication providers is available with [gae-pac4j-demo](https://github.com/pac4j/gae-pac4j-demo).


## Versions

The current version **1.0.0-SNAPSHOT** is under development. It's available on the [Sonatype snapshots repository](https://oss.sonatype.org/content/repositories/snapshots/org/pac4j) as a Maven dependency :

The last released version is the **1.0.2** :

    <dependency>
        <groupId>org.pac4j</groupId>
        <artifactId>gae-pac4j</artifactId>
        <version>1.0.0</version>
    </dependency>

See the [release notes](https://github.com/pac4j/gae-pac4j/wiki/Release-Notes).


## Contact

If you have any question, please use the following mailing lists :
- [pac4j users](https://groups.google.com/forum/?hl=en#!forum/pac4j-users)
- [pac4j developers](https://groups.google.com/forum/?hl=en#!forum/pac4j-dev)
