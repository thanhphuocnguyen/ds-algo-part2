// LRS longest repeated substring
public class LRS {
    public static String lrs(String s) {
        int N = s.length();
        String[] suffixes = new String[N];
        for (int i = 0; i < N; i++) {
            suffixes[i] = s.substring(i, N);
        }

        String lrs = "";
        for (int i = 0; i < N - 1; i++) {
            int len = lcp(suffixes[i], suffixes[i + 1]);
            if (len > lrs.length()) {
                lrs = suffixes[i].substring(0, len);
            }
        }
        return lrs;
    }

    private static int lcp(String s1, String s2) {
        int N = Math.min(s1.length(), s2.length());
        for (int i = 0; i < N; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return i;
            }
        }
        return N;
    }
}
