package com.smartcity;
import com.smartcity.graph.*;import com.smartcity.mst.*;import com.smartcity.connectivity.*;import com.smartcity.shortestpath.*;
import com.smartcity.experiment.Benchmark;
import java.util.*;
public class Main {
 public static void main(String[] args) throws Exception {
  if(args.length>0&&args[0].equalsIgnoreCase("benchmark")){Benchmark.main(new String[0]);return;}
  if(args.length>0&&args[0].equalsIgnoreCase("test")){com.smartcity.SelfTest.run();return;}
  Graph roads=new Graph(5,false);roads.addEdge(0,1,4);roads.addEdge(0,2,2);roads.addEdge(1,2,1);
  roads.addEdge(1,3,5);roads.addEdge(2,3,8);roads.addEdge(3,4,3);
  System.out.println("Smart City — undirected roads:");
  System.out.println("Kruskal MST: "+Kruskal.run(roads));
  System.out.println("Prim MST: "+Prim.run(roads));
  Dijkstra.Result result=Dijkstra.run(roads,0);
  for(int target=0;target<roads.vertices();target++)System.out.println("0 -> "+target+": distance="+result.distances()[target]+", path="+result.pathTo(target));
  Graph directed=new Graph(5,true);directed.addEdge(0,1,1);directed.addEdge(1,2,1);directed.addEdge(2,0,1);
  directed.addEdge(2,3,1);directed.addEdge(3,4,1);
  System.out.println("SCC: "+Kosaraju.run(directed).components());
  System.out.println("Topological sort: "+TopologicalSort.run(directed));
  System.out.println("Run with 'test' or 'benchmark' argument for additional checks.");
 }
}
