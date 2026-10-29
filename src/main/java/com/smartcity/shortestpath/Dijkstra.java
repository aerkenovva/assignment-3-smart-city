package com.smartcity.shortestpath;
import com.smartcity.graph.*;import java.util.*;
public final class Dijkstra {
 private Dijkstra(){}
 public record Result(long[] distances,int[] previous,long operations){
  public List<Integer> pathTo(int target){if(distances[target]==Long.MAX_VALUE)return List.of();
   LinkedList<Integer> path=new LinkedList<>();for(int v=target;v!=-1;v=previous[v])path.addFirst(v);return path;}
 }
 private record Entry(int vertex,long distance){}
 public static Result run(Graph graph,int source){
  if(source<0||source>=graph.vertices())throw new IllegalArgumentException("Invalid source");
  for(Edge edge:graph.edges())if(edge.weight()<0)throw new IllegalArgumentException("Dijkstra requires non-negative weights");
  long[] dist=new long[graph.vertices()];int[] prev=new int[graph.vertices()];
  Arrays.fill(dist,Long.MAX_VALUE);Arrays.fill(prev,-1);dist[source]=0;
  PriorityQueue<Entry> pq=new PriorityQueue<>(Comparator.comparingLong(Entry::distance));pq.add(new Entry(source,0));long operations=0;
  while(!pq.isEmpty()){Entry current=pq.poll();operations++;if(current.distance()!=dist[current.vertex()])continue;
   for(Edge e:graph.neighbors(current.vertex())){operations++;long candidate=current.distance()+e.weight();
    if(candidate<dist[e.to()]){dist[e.to()]=candidate;prev[e.to()]=current.vertex();pq.add(new Entry(e.to(),candidate));}}
  }
  return new Result(dist,prev,operations);
 }
}
