package simulator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Scanner;

public class Mainfunction {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub

		String str;
		String word[]= args;
		StringBuffer wrd = new StringBuffer();
		wrd = Checkcommand(args);
		str = wrd.substring(0, wrd.length());
		//System.out.println(str);
		String fname[]=new String [3];
		fname=str.split("\\ ");
		String host=fname[0];
		int port= Integer.parseInt(fname[1]);

		// Opening VSf file fore reading 
			try {
				/*
				 * open the vsf file 
				 * read the configuration file 
				 * create the configuration 
				 */
				BufferedReader br = new BufferedReader(new FileReader("src/simulator/"+fname[2]));
				//InputStream F_ip = new FileInputStream("src/simulator/"+fname[2]);
				SimulatorConfiguration.parseVSF(br);
				br.close();//	
				// create connection 
				ConnectionClient sc=new ConnectionClient();
				sc.connect(host, port);
					
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File not find");
			}
			
				
	}
	// validation of command arguments
	public static StringBuffer Checkcommand(String[] word){
		String host,port,filename;
		 StringBuffer sb = new StringBuffer();
		if (word.length==6){
			// System.out.print("check length pass");
			if ((word[0].compareTo("--host")==0)||(word[0].compareTo("-h")==0)){
				// System.out.print("check host pass");
				if(Validip(word[1])){
					host = word[1];
					// System.out.print(host);
					if((word[2].compareTo("-p")==0)||(word[2].compareTo("--port")==0)){
						if(validport(word[3])){
							port=word[3];
							if((word[4].compareTo("-i")==0)||(word[4].compareTo("--input")==0)){
								if(filname(word[5])){	
								System.out.println("file:" + word[5]+" host:"+ host +" port:"+port);
								filename=word[5];
								sb.append(host);
								sb.append(" ");
								sb.append(port);
								sb.append(" ");
								sb.append(filename);
								return sb;}
								else quit();
							}
							quit();
						}
						quit();
					}
					else quit();
				}
				quit();
			}
		}
			else if (word.length==1) {
				if((word[0].compareTo("-v")==0)||(word[0].compareTo("--version")==0)){
					System.out.println("version");
					return null;
				}
			}
			else quit();// if wrong command 
		return null;
	}
	//validation of ip adress
	public static boolean Validip(String ipadress)
	{
		String[] numbers = ipadress.split("\\.");
		if(numbers.length!=4)
		{
			return false;
		}
		for(String st:numbers)
		{
			int i=Integer.parseInt(st);
			if((i<0)||(i>255)){return false;}
		}
		return true;

	}
	//validation of file name 
	public static boolean filname(String file){
		String fname[]=new String [2];
		fname=file.split("\\.");
		if (fname[1].compareTo("vsf")==0)
			return true;
		else return false;

	}
	//Validation of port number
	public static boolean validport(String port){
		int i=Integer.parseInt(port);
		if ((i>0)||(i<99999)){
			return true;
		}
		return false ;
	}
	// method the handles error
	public static void quit(){
		System.exit(0);
	}
}
