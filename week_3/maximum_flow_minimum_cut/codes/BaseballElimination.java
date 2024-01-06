import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BaseballElimination {
    private final int numberOfTeams;
    private final Map<String, Integer> teamIndices;
    private final String[] teams;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] against;

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
                against[i][j] = in.readInt();
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

        // Trivial elimination
        for (int i = 0; i < numberOfTeams; i++) {
            if (wins[x] + remaining[x] < wins[i]) {
                return true;
            }
        }

        // Non-trivial elimination
        List<String> subset = buildFlowNetwork(x);

        // FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, 0,
        // flowNetwork.V() - 1);
        if (subset == null)
            return false;

        return true;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validateTeam(team);
        int x = teamIndices.get(team);

        // Trivial elimination
        List<String> subset = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            if (wins[x] + remaining[x] < wins[i]) {
                subset.add(teams[i]);
            }
        }
        if (!subset.isEmpty())
            return subset;
        // Non-trivial elimination
        subset = buildFlowNetwork(x);

        return subset;
    }

    private List<String> buildFlowNetwork(int x) {
        int gameVertices = numberOfTeams * (numberOfTeams - 1) / 2;
        int teamVertices = numberOfTeams - 1;
        int vertices = 2 + gameVertices + teamVertices;
        FlowNetwork flowNetwork = new FlowNetwork(vertices);
        int sum = 0;
        for (int i = 0; i < numberOfTeams; i++) {
            if (i == x)
                continue;
            for (int j = i + 1; j < numberOfTeams; j++) {
                if (j == x)
                    continue;
                flowNetwork.addEdge(new FlowEdge(vertices - 2, i, against[i][j]));
                sum += against[i][j];
                flowNetwork.addEdge(new FlowEdge(i, teamVertices + i, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(i, teamVertices + j, Double.POSITIVE_INFINITY));
            }
            flowNetwork.addEdge(new FlowEdge(teamVertices + i, vertices - 1, wins[x] + remaining[x] - wins[i]));
        }
        FordFulkerson ff = new FordFulkerson(flowNetwork, vertices - 2, vertices - 1);
        List<String> subset = new ArrayList<>();
        if (sum == ff.value())
            return null;
        else {
            for (int v = gameVertices; v < vertices - 2; v++) {
                if (ff.inCut(v)) {
                    subset.add(teams[v - gameVertices]);
                }
            }
            return subset.isEmpty() ? null : subset;
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