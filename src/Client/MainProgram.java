package Client;

import java.util.ArrayList;


public class MainProgram {

    public static void main(String[] args) {




        String serverAddress = "localhost";
        int serverPort = 54321;

        NPMainPage nmp = new NPMainPage(serverAddress, serverPort);

        String title = "임시 대기방";
        ArrayList<User> users = new ArrayList<>();

        NPWaitPage nwp = new NPWaitPage(title,users);
    }
}
