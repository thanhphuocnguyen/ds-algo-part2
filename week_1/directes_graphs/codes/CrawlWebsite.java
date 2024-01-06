
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class CrawlWebsite {
  // Use BFS in Digraph to crawl the website;
  public CrawlWebsite() {
    Queue<String> queue = new Queue<>();
    SET<String> discovered = new SET<>();

    String root = "https://algs4.cs.princeton.edu/";
    queue.enqueue(root);
    discovered.add(root);
    while (!queue.isEmpty()) {
      String v = queue.dequeue();
      StdOut.print(v);
      In in = new In(v);
      String input = in.readAll();

      String regExp = "http://(\\w+\\.)*(\\w+)";
      Pattern pattern = Pattern.compile(regExp);
      Matcher matcher = pattern.matcher(input);

      while (matcher.find()) {
        String url = matcher.group();
        if (!discovered.contains(url)) {
          queue.enqueue(url);
          discovered.add(url);
        }
      }
    }
  }
}
