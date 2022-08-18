package listToMap;
 
import java.io.IOException;
import java.util.List;
import static listToMap.Utils.loadLines;

public class Players {

    static Player playerFromLine(String l) {
        String[] vals = l.split("\t");
        return new Player(vals[0], vals[1], Integer.valueOf(vals[2]), vals[3]);
    }

    static List<Player> loadPlayers(String file) throws IOException {
        return loadLines(file).stream().skip(1).map(Players::playerFromLine).limit(15).toList();
    }

  
}
