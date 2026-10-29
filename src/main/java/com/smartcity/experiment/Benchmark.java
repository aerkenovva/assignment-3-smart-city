package com.smartcity.experiment;
import com.smartcity.graph.*;import com.smartcity.mst.*;import com.smartcity.connectivity.*;import com.smartcity.shortestpath.*;
import java.nio.file.*;import java.io.*;import java.util.*;import java.util.function.LongSupplier;
public final class Benchmark {
 private Benchmark(){}
 private static volatile long blackhole;
 private record Spec(int vertices,int edges){}
 private static final Spec[] SIZES={new Spec(10,30),new Spec(100,500),new Spec(1000,5000)};
 private static void measure(PrintWriter out,String name,Spec spec,LongSupplier workload,LongSupplier operationCounter){
  for(int warmup=0;warmup<3;warmup++)blackhole=workload.getAsLong();
  long sumTime=0,sumOps=0;
  for(int i=0;i<5;i++){
   long start=System.nanoTime();blackhole=workload.getAsLong();long time=System.nanoTime()-start;
   sumTime+=time;sumOps+=operationCounter.getAsLong();
  }
  out.printf(Locale.US,"%s,%d,%d,5,%.2f,%.2f%n",name,spec.vertices(),spec.edges(),sumTime/5.0,sumOps/5.0);
 }
 public static void main(String[] args) throws IOException {
  Path path=Path.of("results/tables/benchmarks.csv");Files.createDirectories(path.getParent());
  try(PrintWriter out=new PrintWriter(Files.newBufferedWriter(path))){
   out.println("algorithm,vertices,edges,repetitions,average_time_ns,average_operations");
   for(Spec spec:SIZES){
    Graph undirected=GraphGenerator.make(spec.vertices,spec.edges,false,false,12345L+spec.vertices);
    Graph dag=GraphGenerator.make(spec.vertices,spec.edges,true,true,23456L+spec.vertices);
    Graph cyclic=GraphGenerator.make(spec.vertices,spec.edges,true,false,34567L+spec.vertices);
    MSTResult[] kr={null},pr={null};Kosaraju.Result[] sc={null};TopologicalSort.Result[] ta={null},tc={null};Dijkstra.Result[] di={null};
    measure(out,"Kruskal",spec,()->{kr[0]=Kruskal.run(undirected);return kr[0].totalWeight();},()->kr[0].operations());
    measure(out,"Prim",spec,()->{pr[0]=Prim.run(undirected);return pr[0].totalWeight();},()->pr[0].operations());
    if(kr[0].totalWeight()!=pr[0].totalWeight())throw new AssertionError("MST weight mismatch");
    measure(out,"Kosaraju",spec,()->{sc[0]=Kosaraju.run(cyclic);return sc[0].components().size();},()->sc[0].operations());
    measure(out,"Topological_DAG",spec,()->{ta[0]=TopologicalSort.run(dag);return ta[0].order().size();},()->ta[0].operations());
    measure(out,"Topological_Directed",spec,()->{tc[0]=TopologicalSort.run(cyclic);return tc[0].hasCycle()?1:0;},()->tc[0].operations());
    measure(out,"Dijkstra",spec,()->{di[0]=Dijkstra.run(undirected,0);return di[0].distances()[spec.vertices()-1];},()->di[0].operations());
   }
  }
  System.out.println("Saved "+path);
 }
}
