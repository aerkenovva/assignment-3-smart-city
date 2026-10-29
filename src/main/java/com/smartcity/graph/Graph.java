package com.smartcity.graph;
import java.util.*;
public class Graph {
    private final int n; private final boolean directed;
    private final List<List<Edge>> adj; private final List<Edge> edges=new ArrayList<>();
    public Graph(int vertices, boolean directed) {
        if(vertices < 1) throw new IllegalArgumentException("At least one vertex is required");
        this.n=vertices; this.directed=directed; adj=new ArrayList<>();
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
    }
    public int vertices(){return n;} public boolean directed(){return directed;}
    public List<Edge> edges(){return Collections.unmodifiableList(edges);}
    public List<Edge> neighbors(int v){check(v); return Collections.unmodifiableList(adj.get(v));}
    private void check(int v){if(v<0||v>=n)throw new IllegalArgumentException("Invalid vertex "+v);}
    public void addEdge(int u,int v,int w){
        check(u);check(v); Edge e=new Edge(u,v,w);edges.add(e);adj.get(u).add(e);
        if(!directed) adj.get(v).add(new Edge(v,u,w));
    }
}
