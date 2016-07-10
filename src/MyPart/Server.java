import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by GrandTemplar on 7/8/2016.
 */
public final class Server {

    private Map<Integer, Socket> player_id = new HashMap<>();
    private Random generator = new Random();

    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(3146);
        } catch (IOException e) {
            System.out.println("Failed to bind port.");
            System.exit(-1);
            return;
        }

        while (true) {
            Socket player1;
            try {
                player1 = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Failed to accept");
                continue;
            }
            BufferedReader inP1;
            try {
                inP1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            } catch (IOException e) {
                System.out.println("Failed to create input stream");
                continue;
            }

            String message;
            try {
                message = inP1.readLine();
            } catch (IOException exception) {
                System.out.println("Failed to read message from P1");
                continue;
            }

            String[] tokens = message.split(" ");
            if (tokens.length < 2) {
                System.out.println("Not enough tokens");
                continue;
            }

            if (!tokens[0].equals("SHIT")) {
                System.out.println("Try again with SHIT");
                continue;
            }

            if (tokens[1].equals("JOIN")) {
                if (tokens.length < 3) {
                    System.out.println("Not enough tokens to join");
                    continue;
                }
                int id;
                try {
                    id = Integer.parseInt(tokens[2]);
                } catch (NumberFormatException exception) {
                    System.out.println("ID is bad");
                    continue;
                }
                if ((id != - 1) && (!player_id.containsKey(id))) {
                    System.out.println("Game with this ID doesn't exist");
                    continue;
                }
                joinPlayer(id, player1);
                continue;
            }

            if (tokens[1].equals("NEW")) {
                int id = generator.nextInt();
                while (player_id.containsKey(id)) {
                    id = generator.nextInt();
                }
                player_id.put(id, player1);
                continue;
            }

            System.out.println("Wrong command. Please use JOIN or NEW.");
            continue;

        }
    }

    private void joinPlayer(int id, Socket p2) {
        if (id == -1) {
            int randId = generator.nextInt(player_id.size());
            new Thread(
                    new Game(player_id.get(
                            (int) player_id.keySet().toArray()[randId]
                    ), p2)
            );
            return;
        }
        new Thread((new Game(player_id.get(id), p2))).run();
    }

}
