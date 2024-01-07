import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
    private final int numberOfTeams;
    private final Map<String, Integer> teamIndices;
    private final String[] teams;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] against;
    private List<String> certificate;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        numberOfTeams = in.readInt();
        teamIndices = new HashMap<>();
        teams = new String[numberOfTeams];
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        against = new int[numberOfTeams][numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            String team = in.readString();
            teamIndices.put(team, i);
            teams[i] = team;
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < numberOfTeams; j++) {
                int againstValue = in.readInt();
                against[i][j] = againstValue;
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return numberOfTeams;
    }

    // all teams
    public Iterable<String> teams() {
        return Arrays.asList(teams);
    }

    private void validateTeam(String team) {
        if (!teamIndices.containsKey(team)) {
            throw new IllegalArgumentException();
        }
    }

    // number of wins for given team
    public int wins(String team) {
        validateTeam(team);
        return wins[teamIndices.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        validateTeam(team);
        return losses[teamIndices.get(team)];

    }

    // number of remaining games for given team
    public int remaining(String team) {
        validateTeam(team);
        return remaining[teamIndices.get(team)];

    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validateTeam(team2);
        validateTeam(team1);
        return against[teamIndices.get(team1)][teamIndices.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validateTeam(team);
        int x = teamIndices.get(team);
        certificate = new ArrayList<>();
        boolean flag = false;
        // Trivial elimination
        for (int i = 0; i < numberOfTeams; i++) {
            if (wins[x] + remaining[x] < wins[i]) {
                certificate.add(teams[i]);
                return true;
            }
        }
        // Non-trivial elimination
        flag = checkFlow(x);
        return flag;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validateTeam(team);
        // Non-trivial elimination
        boolean flag = isEliminated(team);
        if (flag) {
            Collections.sort(certificate);
            return certificate;
        } else
            return null;
    }

    private boolean checkFlow(int x) {
        int teamInFlow = numberOfTeams - 1;
        int gameVertices = teamInFlow * (teamInFlow - 1) / 2;
        int vertices = 2 + gameVertices + teamInFlow;
        FlowNetwork flowNetwork = new FlowNetwork(vertices);
        int gameVertex = 1;
        int teamVertex = 1 + gameVertices;
        int count = 0, countJ = 1;
        int sum = 0;
        for (int i = 0; i < teamInFlow; i++) {
            if (i == x)
                continue;
            for (int j = 0; j < teamInFlow; j++) {
                if (j == x || j <= i)
                    continue;

                flowNetwork.addEdge(new FlowEdge(0, gameVertex, against[i][j]));
                flowNetwork.addEdge(new FlowEdge(gameVertex, teamVertex + count, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(gameVertex, teamVertex + countJ, Double.POSITIVE_INFINITY));
                sum += against[i][j];
                gameVertex++;
                countJ++;
            }

            flowNetwork.addEdge(new FlowEdge(teamVertex + count, vertices - 1, wins[x] + remaining[x] - wins[i]));
            count++;
            countJ = count + 1;
        }

        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, 0, flowNetwork.V() - 1);
        if (sum == fordFulkerson.value())
            return false;
        else {
            int checkIdx = gameVertices + 1;
            for (int v = checkIdx; v < vertices - 1; v++) {
                if (fordFulkerson.inCut(v)) {
                    certificate.add(teams[v - checkIdx]);
                }
            }
            for (FlowEdge edge : flowNetwork.adj(0)) {
                if (edge.capacity() > edge.flow()) {
                    return true;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}