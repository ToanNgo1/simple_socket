import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;
//Toan Ngo  
//Client
class Client2 {
    
    
	public static void main(String args[]) {
        HashMap<String,String> server_memory= new HashMap<String,String>();
        String start="Instruction ";
        Integer counter=0;
        String merge;
		try {
			
			Socket mySocket = new Socket("127.0.0.1", 6666);


			
			//DataOutputStream object to send data through the socket
			DataOutputStream outStream = new DataOutputStream(mySocket.getOutputStream()); //send data

			// BufferReader object to read data coming from the server through the socket
			BufferedReader inStream = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));		//get data

			
			String statement = "";
			Scanner in = new Scanner(System.in);
			
			while(!statement.equals("exit")) {
				statement = in.nextLine();  			// read user input from the terminal data to the server
                if(statement.equals("exit"))
                    {
                        outStream.writeBytes(statement+"\n");
                        break;
                    }
                
                counter+=1;
                merge=generate_hash(start,counter);            //generate a string for instruction
                if(statement.contains(";"))     //meaning there are two command 
                {
                    statement=statement.replace(",", "-");
                    String [] spliter=statement.split(";");
                    for(int i=0;i<spliter.length;i++)        //packet all instuction and pass it on
                    {
                        server_memory.put(merge,spliter[i]);
                        counter+=1;
                        merge=generate_hash(start, counter);
                        

                    }
                    //done writing packet, sending time
                    outStream.writeBytes(server_memory+"\n");
                    counter-=1;
                    //server_memory.clear();
                }
                else
                {
                    statement=statement.replace(",", "-");
                    //System.out.println("this run");             //this only run one
                    server_memory.put(merge, statement);            //generate a hash 
                    //server_memory.put("merge", "statement");
                    System.out.println(server_memory);
                    outStream.writeBytes(server_memory+"\n");		// send such input data to the server
                    //server_memory.clear();                      //clean the hash after sending information
                }                    
                server_memory.clear();
				String str = inStream.readLine();     	// receive response from server as a hash values type string
                //samprecive_output();
                //HashMap <String,String> str_hash=inStream.readLine(); 
                //recieve_hash(str);

				System.out.println(str);                // print this response
				
			}

			System.out.println("Closing the connection and the sockets");
			
			// close connection.
			outStream.close();
			inStream.close();
			mySocket.close();
		
		} catch (Exception exc) {
			System.out.println("Error is : " + exc.toString());

		}
	}
    public static String generate_hash(String start,Integer counter)
    {//generate a hash map
        //System.out.println("in the hash prep");
        String tranfer=String.valueOf(counter);
        start+=tranfer;
        return start;
    }
    /*public void recieve_hash(HashMap input)
    {
        for(String values: input.values())
        {
            System.out.println("this is the respond: "+key);
        }
    }*/
    /*public void recive_output(String ouput)
    {   //unpack and print
        String output_prt=output.replace("{","").replace("}","").replace("=",":");           //{instuction 1=done, instuction 2=done}-> instuction 1:done(there a space here)instuction 2:done
        String [] out_list=output.split(",");
        /*for(int i=out_list.length-1;i>=0;i--)
        {
            
        }*/
    //}
}