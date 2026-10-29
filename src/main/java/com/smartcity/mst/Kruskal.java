package com.smartcity.mst;
import com.smartcity.graph.*;import com.smartcity.dsu.DSU;import java.util.*;
public final class Kruskal {
 private Kruskal(){}
 public static MSTResult run(Graph graph){
  if(graph.directed())throw new IllegalArgumentException("MST requires undirected graph");
  List<Edge> sorted=new ArrayList<>(graph.edges());long[] comparisons={0};
  sorted.sort((a,b)->{comparisons[0]++;return Integer.compare(a.weight(),b.weight());});
  DSU dsu=new DSU(graph.vertices());List<Edge> chosen=new ArrayList<>();long weight=0,examined=0;
  for(Edge edge:sorted){examined++;if(dsu.union(edge.from(),edge.to())){chosen.add(edge);weight+=edge.weight();
   if(chosen.size()==graph.vertices()-1)break;}}
  return new MSTResult(weight,chosen,dsu.components()==1,comparisons[0]+examined+dsu.operations());
 }
}
