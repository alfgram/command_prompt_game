package StagGame;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.*;
import java.util.*;

class StagServer
{
    StagModel model;
    StagController controller;

    public static void main(String args[]) throws IOException, ParseException {
        if(args.length != 2) System.out.println("Usage: java StagGame.StagServer <entity-file> <action-file>");
        else new StagServer(args[0], args[1], 8888);
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber) throws IOException, ParseException {
        model = new StagModel(entityFilename, actionFilename);
        controller = new StagController(model);
        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while(true) acceptNextConnection(ss);
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void acceptNextConnection(ServerSocket ss)
    {
        try {
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in, out);
            out.close();
            in.close();
            socket.close();
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out) throws IOException
    {
        String line = in.readLine();
        ArrayList<String> commandArrayList = new ArrayList<String>();
        for (String word : line.split(" |:")) {
            commandArrayList.add(word);
        }
        out.write(controller.processCommand(commandArrayList));
    }
}
