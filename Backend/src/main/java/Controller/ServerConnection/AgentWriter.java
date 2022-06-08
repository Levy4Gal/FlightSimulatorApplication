package Controller.ServerConnection;

import java.io.*;
import java.net.Socket;

public class AgentWriter{
    private Socket client;
    private PrintWriter out;
    private String outToAgent;

    public AgentWriter(Socket client) {
        this.client = client;
        try {
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outToAgent(String outToAgent) {
        this.outToAgent = outToAgent;
        out.println(outToAgent);
    }
}