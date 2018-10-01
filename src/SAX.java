import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*; 

import java.util.*;
import java.io.*;


public class SAX {

		static public void main(String[] args) {
			
			String filename = null;

		    for (int i = 0; i < args.length; i++) {
		        filename = args[i];
		        if (i != args.length - 1) {
		            usage();
		        }
		    }

		    if (filename == null) {
		        usage();
		    } 
		}
		
		private static void usage() {
		    System.err.println("Usage: SAXLocalNameCount <file.xml>");
		    System.err.println("       -usage or -help = this message");
		    System.exit(1);
		}
		
		private static String convertToFileURL(String filename) {
	        String path = new File(filename).getAbsolutePath();
	        if (File.separatorChar != '/') {
	            path = path.replace(File.separatorChar, '/');
	        }

	        if (!path.startsWith("/")) {
	            path = "/" + path;
	        }
	        return "file:" + path;
	    }
}
