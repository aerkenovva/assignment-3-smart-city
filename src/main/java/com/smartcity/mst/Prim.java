package com.smartcity.mst;
import com.smartcity.graph.*;import java.util.*;
public final class Prim {
 private Prim(){}
 public static MSTResult run(Graph graph){
  if(graph.directed())throw new IllegalArgumentException("MST requires undirected graph");
  boolean[] visited=new boolean[graph.vertices()];List<Edge> chosen=new ArrayList<>();
  long[] comparisons={0};PriorityQueue<Edge> pq=new PriorityQueue<>((a,b)->{comparisons[0]++;return Integer.compare(a.weight(),b.weight());});
  long weight=0, examined=0;int reached=0;
  // A spanning forest is produced for disconnected graphs.
  for(int start=0;start<graph.vertices();start++){
   if(visited[start])continue;visited[start]=true;reached++;
   pq.addAll(graph.neighbors(start));
   while(!pq.isEmpty()){Edge e=pq.poll();examined++;if(visited[e.to()])continue;
    visited[e.to()]=true;reached++;chosen.add(e);weight+=e.weight();
    for(Edge next:graph.neighbors(e.to())){examined++;if(!visited[next.to()])pq.add(next);}
   }
  }
  return new MSTResult(weight,chosen,chosen.size()==graph.vertices()-1,comparisons[0]+examined);
 }
}
