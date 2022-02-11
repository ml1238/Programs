package edu.nmsu.cs.webserver;

/**
 * Web worker: an object of this class executes in its own new thread to receive and respond to a

 * single HTTP request. After the constructor the object executes on its "run" method, and leaves
 * when it is done.
 *
 * One WebWorker object is only responsible for one client connection. This code uses Java threads
 * to parallelize the handling of clients: each WebWorker runs in its own thread. This means that
 * you can essentially just think about what is happening on one client at a time, ignoring the fact
 * that the entirety of the webserver execution might be handling other clients, too.
 *
 * This WebWorker class (i.e., an object of this class) is where all the client interaction is done.
 * The "run()" method is the beginning -- think of it as the "main()" for a client interaction. It
 * does three things in a row, invoking three methods in this class: it reads the incoming HTTP
 * request; it writes out an HTTP header to begin its response, and then it writes out some HTML
 * content for the response content. HTTP requests and responses are just lines of text (in a very
 * particular format).
 * 
 * @author Jon Cook, Ph.D.
 *
 **/

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.io.File;

public class WebWorker implements Runnable
{
	private static boolean exist = false; // global variable that checks if the requested line exists as a file
	private Socket socket; 

	/**
	 * Constructor: must have a valid open socket
	 **/
	public WebWorker(Socket s)
	{
		socket = s;
	}

	/**
	 * Worker thread starting point. Each worker handles just one HTTP request and then returns, which
	 * destroys the thread. This method assumes that whoever created the worker created it with a
	 * valid open socket object.
	 **/
	public void run()
	{
		System.err.println("Handling connection...");
		try
		{
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			// There is always a good way and a bad way to do this
			String destination = readHTTPRequest(is);					// ALL THE WORK
			writeHTTPHeader(os, "text/html");		// FOR ASSIGNMENT 1
			
			// if destination is default, write default message
			if ( exist && destination.length() == 0 )
				writeContent(os);
			else if ( exist && destination.length() > 0 )
				writeCustom(os);
			else if ( !exist )
				write404(os, destination);
			
			os.flush(); // this is to ensure everything is converted to bits/bytes
			socket.close(); // closes connection
		}
		catch (Exception e)
		{
			System.err.println("Output error: " + e);
		}
		System.err.println("Done handling connection.");
		return;
	}

	/**
	 * Read the HTTP request header.
	 **/
	private String readHTTPRequest(InputStream is)
	{
		String line;
		String result = "/default"; // /default is based on browser, Chrome is /favicon.ico=
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		while (true)
		{
			// this is the spot where we can find what to return back to the browser
			// look at request lines
			try
			{
				while (!r.ready())
					Thread.sleep(1);
				line = r.readLine();
				
				// check if line request is default path and store the path
				if ( line.contains("GET") && !line.contains("/favicon.ico") ) {
						System.out.println("Request Path: " + line.substring(5,line.length() - 9));
						result = line.substring(5, line.length() - 9);
						File fileExist = new File(result);
						
						// if result is default, write default content
						if ( result.length() == 0 ) {
							System.out.println("File is default");
							exist = true;
						} // end if
						
						// if result is a path, check if file exists in path
						// if file exists in path, change exist to true and show file
						if ( fileExist.isFile() ) {
							System.out.println("File exists, directing user to file and 200 OK");
							exist = true;
						} // end else if
						
						// if file does not exist in path, keep exist at false and produce 404 NotFound
						else
							System.out.println("File does not exists, directing user to 404 NotFound");
				}
				
				
				// this will print the error in red!
				// .out. will print this in black or white
				System.err.println("Request line: (" + line + ")");
				if ( line.length() == 0 ) 
					break;
			}
			catch (Exception e)
			{
				System.err.println("Request error: " + e);
				break;
			}
		}
		return result;
	}

	/**
	 * Write the HTTP header lines to the client network connection.
	 * 
	 * @param os
	 *          is the OutputStream object to write to
	 * @param contentType
	 *          is the string MIME content type (e.g. "text/html")
	 **/
	private void writeHTTPHeader(OutputStream os, String contentType) throws Exception
	{
		Date d = new Date();
		DateFormat df = DateFormat.getDateTimeInstance();
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		if ( exist )
			os.write("HTTP/1.1 200 OK\n".getBytes()); // 200 if right, 404 if wrong
		else
			os.write("HTTP/1.1 404 Not Found\n".getBytes());
		os.write("Date: ".getBytes());
		os.write((df.format(d)).getBytes());
		os.write("\n".getBytes());
		os.write("Server: Mike's very own server\n".getBytes());
		// os.write("Last-Modified: Wed, 08 Jan 2003 23:11:55 GMT\n".getBytes());
		//os.write("Content-Length: 438\n".getBytes());
		os.write("Connection: close\n".getBytes());
		os.write("Content-Type: ".getBytes()); // for program 2
		os.write(contentType.getBytes());
		os.write("\n\n".getBytes()); // HTTP header ends with 2 newlines
		return;
	}
	
	/**
	 * Write the 404 HTTP message
	 * @param os is the OutputStream
	 * @param locationString is the string MIME content type
	 */
	private void write404(OutputStream os, String locationString ) throws Exception {
		
		os.write(("HTTP/1.0 404 Not Found/r/n"+
				  "Content-type: text/html/r/n/r/n"+
				  "<!DOCTYPE html PUBLIC \"-//IETF//"+
				  "DTD HTML 2.0//EN\"><html><head><meta"+
				  " http-equiv=\"content-type\" content=\"text/html;"+
				  " charset=windows-1252\"><title>Slack: 404 Not Found</title></head><body>" +
				  "<h1>Not Found <h1><p>The requested URL " + locationString +
				  " was not found on this server.</p></body></html>").getBytes());
		return;
	}
	
	/**
	 * Write custom HTTP message based on provided path
	 * @param os is the OutputStream
	 */
	private void writeCustom(OutputStream os) throws Exception {
		
		os.write("<html><head></head><body>\n".getBytes());
		os.write("<p>Hello world, the current date is: February 10, 2022. The server is: ml1238's Server.</p>".getBytes());
		os.write("</body></html>\n".getBytes());
		
	}

	/**
	 * Write the data content to the client network connection. This MUST be done after the HTTP
	 * header has been written out.
	 * +
	 * @param os
	 *          is the OutputStream object to write to
	 **/
	private void writeContent(OutputStream os) throws Exception
	{
		// loop here until file is completely read
		os.write("<html><head></head><body>\n".getBytes());
		os.write("<h3>My web server works!</h3>\n".getBytes());
		os.write("</body></html>\n".getBytes());
	}

} // end class
