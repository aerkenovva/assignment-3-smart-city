package com.smartcity;
import com.smartcity.graph.*;import com.smartcity.dsu.*;import com.smartcity.mst.*;import com.smartcity.connectivity.*;import com.smartcity.shortestpath.*;
import java.util.*;
public final class SelfTest {
 private SelfTest(){}
 private static int checks;
 private static void check(boolean condition,String message){checks++;if(!condition)throw new AssertionError(message);}
 public static void run(){checks=0;
  DSU d=new DSU(4);check(d.components()==4,"DSU initial");check(d.union(0,1),"DSU join");check(!d.union(0,1),"DSU redundant");check(d.find(0)==d.find(1),"DSU find");
  Graph mst=new Graph(4,false);mst.addEdge(0,1,1);mst.addEdge(1,2,2);mst.addEdge(2,3,3);mst.addEdge(0,3,10);mst.addEdge(0,2,2);
  check(Kruskal.run(mst).connected(),"Kruskal connected");check(Prim.run(mst).connected(),"Prim connected");
  check(Kruskal.run(mst).totalWeight()==6,"Kruskal weight");check(Prim.run(mst).totalWeight()==6,"Prim weight");
  Graph disconnected=new Graph(4,false);disconnected.addEdge(0,1,1);disconnected.addEdge(2,3,1);
  check(!Kruskal.run(disconnected).connected(),"Kruskal disconnected");check(!Prim.run(disconnected).connected(),"Prim disconnected");
  Graph single=new Graph(1,false);check(Kruskal.run(single).connected(),"Single Kruskal");check(Prim.run(single).connected(),"Single Prim");
  Graph dag=new Graph(4,true);dag.addEdge(0,1,1);dag.addEdge(0,2,1);dag.addEdge(1,3,1);dag.addEdge(2,3,1);
  TopologicalSort.Result order=TopologicalSort.run(dag);check(!order.hasCycle(),"DAG incorrectly cyclic");check(order.order().size()==4,"Order size");
  Map<Integer,Integer> positions=new HashMap<>();for(int i=0;i<order.order().size();i++)positions.put(order.order().get(i),i);
  for(Edge e:dag.edges())check(positions.get(e.from())<positions.get(e.to()),"Order edge violation");
  Graph cycle=new Graph(4,true);cycle.addEdge(0,1,1);cycle.addEdge(1,0,1);cycle.addEdge(1,2,1);cycle.addEdge(2,3,1);cycle.addEdge(3,2,1);
  check(TopologicalSort.run(cycle).hasCycle(),"Cycle not detected");
  List<List<Integer>> groups=Kosaraju.run(cycle).components();Set<Integer> all=new HashSet<>();for(List<Integer> group:groups)for(int v:group)check(all.add(v),"Vertex repeated in SCC");
  check(all.size()==4&&groups.size()==2,"SCC partition");
  Graph paths=new Graph(5,true);paths.addEdge(0,1,2);paths.addEdge(0,2,10);paths.addEdge(1,2,3);paths.addEdge(2,3,1);
  Dijkstra.Result result=Dijkstra.run(paths,0);check(result.distances()[3]==6,"Dijkstra distance");check(result.pathTo(3).equals(List.of(0,1,2,3)),"Dijkstra path");
  check(result.distances()[4]==Long.MAX_VALUE&&result.pathTo(4).isEmpty(),"Unreachable vertex");
  Graph negative=new Graph(2,true);negative.addEdge(0,1,-1);boolean rejected=false;
  try{Dijkstra.run(negative,0);}catch(IllegalArgumentException ex){rejected=true;}check(rejected,"Negative weight rejection");
  Graph duplicated=new Graph(3,false);duplicated.addEdge(0,1,5);duplicated.addEdge(1,2,5);duplicated.addEdge(0,2,5);
  check(Kruskal.run(duplicated).totalWeight()==Prim.run(duplicated).totalWeight(),"Multiple valid MSTs");
  System.out.println("PASS: "+checks+" correctness checks");
 }
 public static void main(String[] args){run();}
}
