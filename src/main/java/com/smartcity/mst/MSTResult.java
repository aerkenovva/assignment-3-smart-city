package com.smartcity.mst;
import com.smartcity.graph.Edge;
import java.util.List;
public record MSTResult(long totalWeight, List<Edge> edges, boolean connected, long operations) { }
