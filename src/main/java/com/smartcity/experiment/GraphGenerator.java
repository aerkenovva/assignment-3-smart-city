package com.smartcity.experiment;
import com.smartcity.graph.*;import java.util.*;
public final class GraphGenerator {
 private GraphGenerator(){}
 public static Graph make(int n,int m,boolean directed,boolean dag,long seed){
  if(dag&&!directed)throw new IllegalArgumentException("DAG must be directed");
  long max=dag?(long)n*(n-1)/2:directed?(long)n*(n-1):(long)n*(n-1)/2;
  if(m<0||m>max)throw new IllegalArgumentException("Invalid edge count");
  if(!directed&&m<n-1)throw new IllegalArgumentException("Connected graph needs n-1 edges");
  Graph g=new Graph(n,directed);Random random=new Random(seed);Set<Long> seen=new HashSet<>();
  if(!directed)for(int i=1;i<n;i++)add(g,seen,i,random.nextInt(i),1+random.nextInt(100),false);
  if(directed&&!dag&&n>=2&&m>=2){add(g,seen,0,1,1+random.nextInt(100),true);add(g,seen,1,0,1+random.nextInt(100),true);}
  while(g.edges().size()<m){int u=random.nextInt(n),v=random.nextInt(n);if(u==v)continue;
   if(dag&&u>v){int t=u;u=v;v=t;}
   add(g,seen,u,v,1+random.nextInt(100),directed);
  }
  return g;
 }
 private static void add(Graph g,Set<Long> seen,int u,int v,int w,boolean directed){
  int a=directed?u:Math.min(u,v),b=directed?v:Math.max(u,v);long key=((long)a<<32)|(b&0xffffffffL);
  if(seen.add(key))g.addEdge(u,v,w);
 }
}
