package com.smartcity.connectivity;
import com.smartcity.graph.*;import java.util.*;
public final class TopologicalSort {
 private TopologicalSort(){}
 public record Result(List<Integer> order, boolean hasCycle, long operations){}
 public static Result run(Graph graph){
  if(!graph.directed())throw new IllegalArgumentException("Topological order requires directed graph");
  int n=graph.vertices();int[] indegree=new int[n];long operations=0;
  for(Edge e:graph.edges()){indegree[e.to()]++;operations++;}
  Queue<Integer> q=new ArrayDeque<>();for(int i=0;i<n;i++)if(indegree[i]==0)q.add(i);
  List<Integer> result=new ArrayList<>();while(!q.isEmpty()){
   int u=q.remove();result.add(u);operations++;
   for(Edge e:graph.neighbors(u)){operations++;if(--indegree[e.to()]==0)q.add(e.to());}
  }
  boolean cycle=result.size()!=n;return new Result(cycle?List.of():result,cycle,operations);
 }
}
