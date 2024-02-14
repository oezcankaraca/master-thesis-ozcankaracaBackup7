import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;


public class GeneratorOfNetworkTopologyRandom {
    private static int numberOfPeers = 75; // Default-Anzahl der Peers inklusive lectureStudioServer
    static JsonArray peersArray = new JsonArray(); // JSON-Array für Peer-Informationen
    static JsonArray connectionsArray = new JsonArray(); // JSON-Array für Verbindungen zwischen Peers
    private static Random random = new Random(); // Instanz von Random für die Generierung von Zufallswerten

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                numberOfPeers = Integer.parseInt(args[0]) + 1; // Anpassung für zusätzlichen Peer lectureStudioServer
            } catch (NumberFormatException e) {
                System.err.println("Error: Argument must be an integer. Using default value of 11.");
            }
        }

        String homeDirectory = System.getProperty("user.home");
        String basePath = homeDirectory + "/Desktop/master-thesis-ozcankaraca";
        String pathToInputData = basePath + "/data-for-testbed/inputs-new/input-data-" + numberOfPeers + ".json";

        addLectureStudioServerPeer();
        for (int i = 1; i <= numberOfPeers; i++) { // Start bei 1, da lectureStudioServer schon hinzugefügt wurde
            generatePeerData(i);
        }
        generateConnections();

        JsonObject networkTopology = new JsonObject();
        networkTopology.addProperty("filename", "test.pdf");
        networkTopology.addProperty("filesize", 5000);
        networkTopology.add("peers", peersArray);
        networkTopology.add("connections", connectionsArray);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToInputData))) {
            new GsonBuilder().setPrettyPrinting().create().toJson(networkTopology, writer);
            System.out.println("Network topology JSON has been saved to: " + pathToInputData);
        } catch (IOException e) {
            System.err.println("Error while writing the JSON file: " + e.getMessage());
        }
    }

    private static void addLectureStudioServerPeer() {
        JsonObject lectureStudioServer = new JsonObject();
        lectureStudioServer.addProperty("name", "lectureStudioServer");
        lectureStudioServer.addProperty("maxUpload", 28664);
        lectureStudioServer.addProperty("maxDownload", 79823);
        peersArray.add(lectureStudioServer);
    }

    private static void generatePeerData(int peerId) {
        JsonObject peerObject = new JsonObject();
        int downloadSpeed = 80000 + random.nextInt(10000); // 80-90 Mbit/s
        int uploadSpeed = 25000 + random.nextInt(5000); // 25-30 Mbit/s

        peerObject.addProperty("name", String.valueOf(peerId));
        peerObject.addProperty("maxUpload", uploadSpeed);
        peerObject.addProperty("maxDownload", downloadSpeed);

        peersArray.add(peerObject);
    }

    private static void generateConnections() {
        for (int i = 0; i < peersArray.size(); i++) {
            for (int j = 0; j < peersArray.size(); j++) {
                if (i != j) {
                    JsonObject connection = new JsonObject();

                    JsonObject sourcePeer = peersArray.get(i).getAsJsonObject();
                    JsonObject targetPeer = peersArray.get(j).getAsJsonObject();

                    int sourceUpload = sourcePeer.get("maxUpload").getAsInt();
                    int targetDownload = targetPeer.get("maxDownload").getAsInt();
                    double latency = 40 + random.nextDouble() * 40; // 40-80 ms
                    double packetLoss = 0.001 + random.nextDouble() * 0.001; // 0.001-0.002
                    int bandwidth = Math.min(sourceUpload, targetDownload); // Bandbreite als Minimum von Upload und Download

                    connection.addProperty("sourceName", sourcePeer.get("name").getAsString());
                    connection.addProperty("targetName", targetPeer.get("name").getAsString());
                    connection.addProperty("bandwidth", bandwidth);
                    connection.addProperty("latency", String.format(Locale.US, "%.2f", latency));
                    connection.addProperty("loss", String.format(Locale.US, "%.4f", packetLoss));

                    connectionsArray.add(connection);
                }
            }
        }
    }
}
