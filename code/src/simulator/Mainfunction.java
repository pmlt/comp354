package simulator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.ParseException;

public class Mainfunction {
	
	private static class ParsedArguments {
		boolean Version = false;
		boolean Help = false;
		InetSocketAddress Address = null;
		File InputFile = null;
		String Host = null;
		int Port = 0;
		String Error = null;
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// 1) Parse command-line arguments to obtain host, port, and vsf filename
		ParsedArguments parsedArgs = parseArguments(args);
		if (parsedArgs.Error != null) {
			System.out.println(parsedArgs.Error);
			printHelp();
			System.exit(1);
		}
		if (parsedArgs.Help){
			printHelp();
			System.exit(0);
		}
		if (parsedArgs.Version){
			System.out.println("Vessel Simulator: v. 0.1");
			System.exit(0);
		}
		try {
			// Opening VSF file fore reading 
			BufferedReader br = new BufferedReader(new FileReader(parsedArgs.InputFile));
			
			// Call SimulatorConfiguration.parseVSF(), which returns config instance
			SimulatorConfiguration config = SimulatorConfiguration.parseVSF(br);
			
			// Close file
			br.close();
			
			// Create ConnectionClient instance
			ConnectionClient cc = new ConnectionClient();
			
			// Connect to the VMS
			cc.connect(parsedArgs.Host, parsedArgs.Port);
			
			// Create Simulator instance, passing the config
			Simulator sim = new Simulator(config);
			if (cc.isReady()){
				// Call simulator.start(), passing the connection client instance.
				sim.start(cc);	
			}
			// When start() returns, we are done; close ConnectionClient and exit
			cc.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public static void printHelp() {
		System.out.println("");
		System.out.println("Vessel Simulator: v. 0.1");
		System.out.println("");
		System.out.println("Usage:");
		System.out.println("[-h|--host HOST] [-p|--port PORT] [-i|--input VSFFILEPATH]");
	}
	
	public static ParsedArguments parseArguments(String[] args) {
		ParsedArguments ret = new ParsedArguments();
		if (args.length == 0) {
			ret.Help = true;
			return ret;
		}
		for (int i=0; i < args.length; i++) {
			switch (args[i]) {
			case "--host":
			case "-h":
				//Require next argument to be present
				if ((i+1) >= args.length) {
					ret.Help = true;
					return ret;
				}
				ret.Host = args[i+1];
				break;
			case "--port":
			case "-p":
				//Require next argument to be present
				if ((i+1) >= args.length) {
					ret.Help = true;
					return ret;
				}
				try {
					ret.Port = Integer.parseInt(args[i+1]);
				}
				catch (NumberFormatException e) {
					ret.Error = "You must supply a number for the port.";
				}
				if ((ret.Port < 0)||(ret.Port > 99999)){
					ret.Error = "You must provide a positive integer for the port.";
				}
				break;
			case "--input":
			case "-i":
				//Require next argument to be present
				if ((i+1) >= args.length) {
					ret.Help = true;
					return ret;
				}
				ret.InputFile = new File(args[i+1]);
				if (!ret.InputFile.exists()) {
					ret.Error = "File " + args[i+1] + " does not exist.";
					return ret;
				}
				if (!ret.InputFile.canRead()) {
					ret.Error = "File " + args[i+1] + " is not readable.";
					return ret;
				}
				break;
			case "--help":
			case "-?":
				ret.Help = true;
				break;
			case "--version":
			case "-v":
				ret.Version = true;
				break;
			}
		}
		if (ret.Host != null && ret.Port != 0) {
			ret.Address = new InetSocketAddress(ret.Host, ret.Port);
			if (ret.Address.isUnresolved()) {
				ret.Error = "Cannot resolve address " + ret.Host + ":" + ret.Port;
				return ret;
			}
		}
		
		return ret;
	}
}
