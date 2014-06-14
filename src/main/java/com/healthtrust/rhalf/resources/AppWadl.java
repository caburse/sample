package com.healthtrust.rhalf.resources;

import java.io.StringWriter;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Provides the human-friendly WADL via built-in Jersey tooling + an external xsl.  
 * The resourcedoc.xml file must be generated from an Ant build prior to invoking this resource.
 */
@Path("/wadl")
public final class AppWadl {

	private @Context UriInfo uriInfo;
	public UriInfo getUriInfo() {
		return uriInfo;
	}
	public void setUriInfo(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	}
	/**
	 * If successful, returns the wadl in html format.
	 * 
	 * @response.representation.200.mediaType text/html
	 * @response.representation.200.doc Returns wadl in html format.
	 * 
	 */
	@GET
	@Produces("text/html")
	public String getWadlAsHtml() {
		try
	    {
			java.io.StringWriter sw = new StringWriter();
		    javax.xml.transform.TransformerFactory tFactory = 
		                javax.xml.transform.TransformerFactory.newInstance();
		    
		    // Get the XML input document and the stylesheet, both in the servlet
		    // engine document directory.		
		    
		    javax.xml.transform.Source xmlSource = 
	                new javax.xml.transform.stream.StreamSource
	                             (new java.net.URL(uriInfo.getBaseUri()+"application.wadl").openStream());
		      
		    
		    URL url = Thread.currentThread().getContextClassLoader().getResource("wadl-1.2.xsl");		      
		    javax.xml.transform.Source xslSource = 
		                new javax.xml.transform.stream.StreamSource((url).openStream());
		    		      
		    // Generate the transformer.
		    javax.xml.transform.Transformer transformer = 
		                             tFactory.newTransformer(xslSource);
		    // Perform the transformation, sending the output to the response.
		    transformer.transform(xmlSource, 
		                           new javax.xml.transform.stream.StreamResult(sw));
		    return sw.toString();
	    }
	    // If an Exception occurs, return the error to the client.
	    catch (Exception e)
	    {
	      e.printStackTrace(System.err);
		  return "<errors>" +
		  		"<error>Could not produce wadl.</error><exception-message>"+
		  		e.getLocalizedMessage()+
		  		"</exception-message></errors>";
	    }

	}
}
