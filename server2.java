import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
//Toan Ngo
//server
class Server2 {

	public static void main(String args[]) {
        int term_track=1;
        String return_val="instruction ";
        ArrayList<Integer> inputValues=new ArrayList<>();
        //List<String> action_command=Arrays.asList("Add","Sort_A","Remove","Get_Summation");
        LinkedHashMap<String,String> out_packet=new LinkedHashMap<>();
        //server_int.add(10);
        //int count=1;
		try {
			ServerSocket mySocket = new ServerSocket(6666);

			
			System.out.println("Startup the server side over port 6666 ....");

			Socket connectedClient = mySocket.accept();
			

			System.out.println("Connection established");

			
			// to interact (read incoming data / send data) with the connected client, we need to create the following:

			// BufferReader object to read data coming from the client
			BufferedReader br = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));

			// PrintStream object to send data to the connected client
			PrintStream ps = new PrintStream(connectedClient.getOutputStream());

			
			// Let's keep reading data from the client, as long as the client does't send "exit".
			String inputData;
			while (!(inputData = br.readLine()).equals("exit")) {    
                //System.out.println(inputData);
                if(!(inputData.equals("exit")))
                {
                    System.out.println("received a message from client: " + inputData);   //print the incoming data from the client
                    String packet=inputData.replace("{","").replace("}","").replace(",", " ").replace("Instruction", "").replace("=", " ");
                    packet=packet.strip();
                    //System.out.println(inputData.split(" "));
                    //System.out.println(packet);
                    String[]sample=packet.split(" ");
                    List<String> track=Arrays.asList(sample);
                    for(int i=0;i<(sample.length);i++)                  //runtime
                    {
                        String term_track_str=String.valueOf(term_track);
                        if((track.indexOf(term_track_str))!=-1)
                        {
                            int get_index=track.indexOf(term_track_str);
                            //extraction
                            //System.out.println(sample[get_index+2]);
                            String extract=sample[get_index+1];             //Add:sdaiaosjdoisjd
                            System.out.println(extract);
                            System.out.println("inputvalues"+inputValues);
                            //System.out.println(extract +"at this term:"+term_track_str);
                            String out=work(extract, inputValues);
                            //System.out.println("check m");
                            //System.out.println(out);
                            out_packet.put(return_val+term_track_str,out);
                            term_track+=1;
                            
                        }
                        else
                        {
                           // System.out.println("this to execape the loop if the number reach the end");
                            break;
                        }
                    }
                    //correction
                    //term_track-=1;
                    //System.out.println("current num:"+term_track);
                    //System.out.println(track.size());
                    //System.out.println(track);

                    
                    ps.println(out_packet);              //respond back to the client
                    out_packet.clear();
                }
                   
            }
                System.out.println("Closing the connection and the sockets");

                // close the input/output streams and the created client/server sockets
                ps.close();
                br.close();
                mySocket.close();
                connectedClient.close();

                 
            }
            catch (Exception exc) 
            {
                System.out.println("Error :" + exc.toString());
            }
        

	}

    public static String remove_val(String value, ArrayList <Integer> array)
    {
        //System.out.println("remove some"+ value);
        Integer val=Integer.parseInt(value);
        while(true)
        {
            if(array.contains(val))
            {
                array.remove(val);
            }
            else
            {
                return "this number doesn't exits in this list";
            }
        }
    }

    public static String sort_list(ArrayList <Integer> array )
    {  
        if(array.size()==0)
        {
            return "{null}";
        }
        Collections.sort(array);
        String value="";
        for (Integer i : array)
        {
            String conver=String.valueOf(i);
            value+=conver+" ";
        }
        value=value.strip().replace(" ",",");
        return "{"+value+"}";
    }

    public static void add(String value,ArrayList <Integer> array)
    {
        int value_int=Integer.parseInt(value);
        array.add(value_int);
        //System.out.println("this is the add array"+array);
    }

    public static String summation(ArrayList <Integer> array)
    { 
        if(array.size()==0)
        {
            return "{null}";
        }
        Integer value=0;
        for(Integer i : array)
        {
            value+=i;
        }
        String output=String.valueOf(value);
        return output;
    }

    public static String work(String input,ArrayList <Integer> array)   //>add:456454564-6454
    {   //System.out.println("this");
        String method_work="";
        if(input.contains(":"))
        {
            //if(input.)
            //System.out.println("check"+input);
            String[] slice=input.split(":");
            String method=slice[0];
            String value=slice[1];
            value=value.strip();
            //System.out.println("sech");
            if(method.toUpperCase().equals("ADD"))
            {
                if(value.contains("-"))
                {  
                    value=value.replace("-", " ");
                    //add(method);
                    String[] run=value.split(" ");
                    method_work="these number has been added: ";
                    for(String element: run)
                    {
                        method_work+=element+",";
                        add(element,array);
                    }
                    //System.out.println("sample"+array);
                    //return method_work;
                    return "Added Successfull";

                }
                else
                {
                    //add(method);
                    //System.out.println("seeL "+array);
                    add(value,array);
                    //return "this number "+value+" has been added.";
                    return "Added succesfull";
                }

            }
            else if(method.toUpperCase().equals("REMOVE"))
            {
                if(value.contains("-"))
                {
                    //System.out.println("int");
                    value=value.replace("-", " ");
                    String[] run=value.split(" ");
                    method_work+="these number has being remove: ";
                    for(String element: run)
                    {
                        method_work+=remove_val(element, array);
                    }
                    //return method_work;
                    return "remove successfull";
                }
                else
                {
                    method_work=remove_val(value, array);
                    //return method_work;
                    return"remove successfull";
                }
            }
            else
            {
                return "this comand doesn't exist";
            }
        }
        else
        {
            if(input.toUpperCase().equals("SORT_A"))
            {
                String sorted=sort_list(array);
                return "sorted list: "+sorted;
            }
            else if(input.toUpperCase().equals("GET_SUMMATION"))
            {
                String sum=summation(array);
                return "this is the sum of the list: "+sum;
            }
            else
            {
                return "this comand doesnt exist";
            }
        }

    }
    /*public static void packer(String instuction,String output,LinkedHashMap sotage)
    {
        sortage.put(instuction,output);
    }*/
}

