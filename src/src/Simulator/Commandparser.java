package Simulator;

public class Commandparser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String word[]= args;
		String host,port;
		// if the command is in the form 
		if (word.length==6){
			// System.out.print("check length pass");
			if ((word[0].compareTo("--host")==0)||(word[0].compareTo("-h")==0)){
				// System.out.print("check host pass");
				if(Validip(args[1])){
					host=args[1];
					// System.out.print(host);
					if((word[2].compareTo("-p")==0)||(word[2].compareTo("--port")==0)){
						if(validport(args[3])){
							port=args[3];
							if((word[4].compareTo("-i")==0)||(word[4].compareTo("--input")==0)){
								if(filname(word[5]))	
								System.out.println("file:" + word[5]+" host:"+ host +" port:"+port);
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
				}
			}
			quit();// if wrong command 

				
	}

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

	public static boolean filname(String file){
		String fname[]=new String [2];
		fname=file.split("\\.");
		if (fname[1].compareTo("vsf")==0)
			return true;
		else return false;

	}

	public static boolean validport(String port){
		int i=Integer.parseInt(port);
		if ((i>0)||(i<99999)){
			return true;
		}
		return false ;
	}


	public static void quit(){
		System.exit(0);
	}
}
