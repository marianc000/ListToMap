package listToMap;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import static java.util.stream.Collectors.*;
import static listToMap.Players.loadPlayers;
import static listToMap.Utils.equal;
import static listToMap.Utils.notEequal;
import static listToMap.Utils.print;

public class ListToMap {

    public static void main(String[] args) throws IOException {
        new ListToMap().run();
    }



    void run() throws IOException {
        List<Player> players = loadPlayers("players.txt");
  
        Map<String, Player> playersByName = players.stream()
                .collect(toMap(Player::name, p -> p));
        print(playersByName);

        Map<String, Integer> playerScores = players.stream()
                .collect(toMap(Player::name, Player::score));
        print(playerScores);

        Map<String, Player> playersByName2 = players.stream()
                .collect(HashMap::new, (m, p) -> m.put(p.team(), p), (m1, m2) -> m1.putAll(m2));
        print("playersByName2", playersByName2);
 
        Map<String, List<Player>> playersByTeam = players.stream().collect(groupingBy(Player::team));
        print("playersByTeam", playersByTeam);
 
        Map<Boolean, List<Player>> bestPlayers = players.stream().collect(partitioningBy(p -> p.score() > 1000));
        print("bestPlayers", bestPlayers);

        Map<Boolean, List<Player>> bestPlayers2 = players.stream().collect(groupingBy(p -> p.score() > 1000));
        print(bestPlayers2);
        notEequal(bestPlayers, bestPlayers2);
  
        Map<String, Integer> totalScoreByTeam = players.stream()
                .collect(groupingBy(Player::team, summingInt(Player::score)));
        print("totalScoreByTeam", totalScoreByTeam);

        Map<String, Double> avgScoreByTeam = players.stream()
                .collect(groupingBy(Player::team, averagingInt(Player::score)));
        print(avgScoreByTeam);

        Map<String, Long> teamMemberCount = players.stream()
                .collect(groupingBy(Player::team, counting()));
        print(teamMemberCount);

        /* values groupped by groupingBy can be further grouped by grouppingBy or partitioningBy 
        
         */
        Map<String, Map<String, Long>> sexCountInTeams = players.stream().collect(
                groupingBy(Player::team, groupingBy(Player::sex, counting())));

        print(sexCountInTeams);

        Map<String, List<String>> namesBySex = players.stream().collect(
                groupingBy(Player::sex, mapping(Player::name, toList())));
        print("namesBySex", namesBySex);

        Map<String, Set<Integer>> teamUniqueScores = players.stream().collect(
                groupingBy(Player::team, mapping(Player::score, toSet())));
        print(teamUniqueScores);

        Map<String, Set<Integer>> sortedUniqueScoresBySex = players.stream().collect(
                groupingBy(Player::sex, mapping(Player::score, toCollection(TreeSet::new))));

        print(sortedUniqueScoresBySex);

        Map<String, String> teamMemberNames = players.stream().collect(groupingBy(Player::team,
                mapping(Player::name, joining(", ")))); // how to make sorted??
        print("teamMemberNames", teamMemberNames);

        Map<String, String> teamMemberNames2 = players.stream().collect(
                toMap(Player::team, Player::name, (t, v) -> t + ", " + v));
        print("teamMemberNames2", teamMemberNames2);

        Map<String, String> teamMemberNames3 = players.stream()
                .collect(groupingBy(Player::team, reducing("", Player::name, (a, b) -> a + ", " + b)));
        print("teamMemberNames3", teamMemberNames3);

        Map<String, String> teamMemberNames4 = players.stream().collect(groupingBy(Player::team,
                mapping(Player::name, collectingAndThen(toList(), l -> l.stream().sorted().collect(joining(", ")))))); // how to make sorted??

        print("teamMemberNames4", teamMemberNames4);

        Map<String, String> teamMemberNames5 = players.stream()
                .sorted(Comparator.comparing(Player::name))
                .collect(groupingBy(Player::team,
                        mapping(Player::name, joining(", ")))); // how to make sorted??

        print("teamMemberNames5", teamMemberNames5);
 
        Map<String, Map<String, List<String>>> namesByTeamAndSex = players.stream()
                .collect(groupingBy(Player::team,
                        groupingBy(Player::sex, mapping(Player::name, toList()))));
        print(namesByTeamAndSex);
 
        Map<String, Optional<Player>> highestScoreByTeam = players.stream()
                .collect(groupingBy(Player::team,
                        maxBy(Comparator.comparing(Player::score))));

        Map<String, Player> highestScoreByTeam2 = players.stream()
                .collect(groupingBy(Player::team,
                        maxBy(Comparator.comparing(Player::score))))
                .entrySet().stream().collect(toMap(e -> e.getKey(), e -> e.getValue().get()));
        print("highestScoreByTeam2", highestScoreByTeam2);

        Map<String, Player> highestScoreByTeam3 = players.stream()
                .collect(toMap(Player::team, p -> p,
                        (p1, p2) -> p1.score() > p2.score() ? p1 : p2));
        print(highestScoreByTeam3);

        equal(highestScoreByTeam2, highestScoreByTeam3);

        record MinMax(Player min, Player max) {

        };

        Map<String, MinMax> highestScoreInTeam4 = players.stream()
                .collect(groupingBy(Player::team, teeing(
                        minBy(Comparator.comparing(Player::score)),
                        maxBy(Comparator.comparing(Player::score)),
                        (a, b) -> new MinMax(a.get(), b.get())
                )));
        print(highestScoreInTeam4);
 
    }
}
