package com.smartcity.connectivity;
import com.smartcity.graph.*;import java.util.*;
public final class Kosaraju {
 private Kosaraju(){}
 public record Result(List<List<Integer>> components,long operations){}
 private static class Counter{long value;}
 // Iterative DFS avoids recursion-stack overflow on large graphs.
 public static Result run(Graph graph){
  if(!graph.directed())throw new IllegalArgumentException("SCC requires directed graph");
  int n=graph.vertices();boolean[] seen=new boolean[n];List<Integer> order=new ArrayList<>();Counter c=new Counter();
  for(int s=0;s<n;s++)if(!seen[s]){
   Deque<int[]> stack=new ArrayDeque<>();seen[s]=true;stack.push(new int[]{s,0});
   while(!stack.isEmpty()){
    int[] top=stack.peek();List<Edge> adj=graph.neighbors(top[0]);
    if(top[1]<adj.size()){int v=adj.get(top[1]++).to();c.value++;if(!seen[v]){seen[v]=true;stack.push(new int[]{v,0});}}
    else{order.add(top[0]);stack.pop();}
   }
  }
  List<List<Integer>> reverse=new ArrayList<>();for(int i=0;i<n;i++)reverse.add(new ArrayList<>());
  for(Edge e:graph.edges()){reverse.get(e.to()).add(e.from());c.value++;}
  Arrays.fill(seen,false);List<List<Integer>> groups=new ArrayList<>();
  for(int i=order.size()-1;i>=0;i--){int s=order.get(i);if(seen[s])continue;
   List<Integer> group=new ArrayList<>();Deque<Integer> stack=new ArrayDeque<>();stack.push(s);seen[s]=true;
   while(!stack.isEmpty()){int u=stack.pop();group.add(u);c.value++;
    for(int v:reverse.get(u)){c.value++;if(!seen[v]){seen[v]=true;stack.push(v);}}
   }
   groups.add(group);
  }
  return new Result(groups,c.value);
 }
}
