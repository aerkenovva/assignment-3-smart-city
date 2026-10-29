# Assignment 3 — Smart City Infrastructure and Route Planning

## 1. Overview

This project is a Java application designed to simulate smart city infrastructure and route planning using graph algorithms.

The main objective is to connect city districts efficiently, analyze connectivity, and calculate shortest routes between locations.

The city is represented as a weighted graph:

- **Vertices (V):** City districts or locations.
- **Edges (E):** Roads or infrastructure connections.
- **Weights:** Construction costs or travel distances.

The application supports both directed and undirected graphs.

Undirected graphs are used for minimum spanning tree calculations, while directed graphs represent one-way roads and infrastructure dependencies.

The project implements the following algorithms:

1. Kruskal's Algorithm
2. Prim's Algorithm
3. Disjoint Set Union (DSU)
4. Topological Sorting
5. Kosaraju's Algorithm for Strongly Connected Components
6. Dijkstra's Shortest Path Algorithm

The application also includes performance benchmarking, complexity analysis, correctness testing, and graphical visualization of experimental results.

---

## 2. Implementation

The project is implemented in Java and organized into separate packages to improve readability, maintainability, and modularity.

### 2.1 Graph Representation

The `Graph` class represents the city's infrastructure using an adjacency list.

An adjacency list was selected because it efficiently stores graph connections and is suitable for sparse graphs.

For undirected graphs, connections are stored in both directions in the adjacency lists.

For directed graphs, each connection represents a specific direction.

### 2.2 Disjoint Set Union (DSU)

The DSU data structure manages connected components.

It implements:

- `find()` — determines the representative of a component.
- `union()` — merges two different components.
- Path Compression — reduces the length of paths during find operations.
- Union by Rank — keeps the DSU tree balanced.

These optimizations improve performance by making repeated operations nearly constant in amortized time.

### 2.3 Kruskal's Algorithm

Kruskal's algorithm finds a minimum spanning tree of an undirected weighted graph.

Implementation steps:

1. Sort all edges by increasing weight.
2. Initialize the DSU structure.
3. Examine each edge.
4. Add an edge if it connects two different components.
5. Continue until the spanning tree is completed.

The algorithm avoids cycles and calculates the total MST weight.

For disconnected graphs, it produces a minimum spanning forest and reports that the graph is disconnected.

### 2.4 Prim's Algorithm

Prim's algorithm also calculates a minimum spanning tree.

It uses a priority queue to select edges with the smallest available weights.

Implementation steps:

1. Select a starting vertex.
2. Insert candidate edges into the priority queue.
3. Select the minimum-weight edge leading to an unvisited vertex.
4. Add the vertex and update the queue.
5. Continue until the connected component is covered.

For disconnected graphs, the implementation supports a minimum spanning forest.

Both Kruskal and Prim must produce the same total MST weight for a connected undirected weighted graph.

### 2.5 Topological Sorting

Topological sorting determines a valid ordering of vertices in a directed acyclic graph (DAG).

The implementation uses Kahn's algorithm.

Steps:

1. Calculate the in-degree of each vertex.
2. Find vertices with zero in-degree.
3. Add these vertices to the ordering.
4. Remove their outgoing dependencies.
5. Repeat until all possible vertices are processed.

If the number of processed vertices is smaller than the total number of vertices, the graph contains a cycle.

In this case, a valid topological ordering does not exist.

### 2.6 Strongly Connected Components (SCC)

The project uses Kosaraju's algorithm to identify strongly connected components in directed graphs.

A strongly connected component is a group of vertices where every vertex is reachable from every other vertex in the same group.

Kosaraju's algorithm works in two main phases:

1. Perform DFS and determine the processing order.
2. Reverse the graph and perform DFS in the reverse finishing order.

The implementation uses iterative DFS.

The algorithm identifies all strongly connected components.

### 2.7 Dijkstra's Algorithm

Dijkstra's algorithm calculates shortest paths from a selected source vertex to all reachable vertices.

The implementation uses a priority queue and an adjacency list.

Steps:

1. Set the source distance to zero.
2. Set other distances to infinity.
3. Select the vertex with the smallest candidate distance.
4. Examine its outgoing edges.
5. Update distances when shorter paths are found.
6. Continue until the priority queue is empty.

The algorithm records both shortest distances and reconstructed paths.

Unreachable vertices are handled separately.

Dijkstra's algorithm requires non-negative edge weights because negative weights invalidate the greedy assumption used for shortest-path calculations.

---

## 3. Complexity Analysis

Let:

- V = Number of vertices
- E = Number of edges
- α(V) = Inverse Ackermann function

The following complexity bounds assume adjacency lists, comparison-based sorting, optimized DSU, and binary priority queues.

| Algorithm | Time Complexity | Space Complexity |
|---|---|---|
| DSU Find / Union | O(α(V)) amortized | O(V) |
| Kruskal | O(E log E + Eα(V)) | O(V + E) |
| Prim | O((V + E) log(V + E)) | O(V + E) |
| Topological Sort | O(V + E) | O(V) |
| Kosaraju SCC | O(V + E) | O(V + E) |
| Dijkstra | O((V + E) log(V + E)) | O(V + E) |

### Complexity Discussion

**DSU:** Path compression and union by rank reduce tree depth and improve the efficiency of repeated find and union operations.

**Kruskal:** Sorting edges is the main computational cost. DSU helps detect cycles efficiently.

**Prim:** Performance depends on priority queue operations and graph size.

**Topological Sort:** Every vertex and edge is processed a bounded number of times, resulting in linear time complexity.

**Kosaraju:** Two DFS passes and construction of the reversed graph result in linear time complexity.

**Dijkstra:** Priority queue operations and edge relaxation determine the running time.

As the number of vertices and edges grows, the algorithms generally require more time and memory.

---

## 4. Experimental Setup

The performance of the implemented algorithms was evaluated using graphs of different sizes.

### 4.1 Graph Configurations

| Graph Size | Vertices (V) | Edges (E) |
|---|---:|---:|
| Small | 10 | 30 |
| Medium | 100 | 500 |
| Large | 1000 | 5000 |

### 4.2 Experimental Method

The experiments follow these conditions:

- Each algorithm is executed five times for each graph size.
- Three warm-up runs are performed before measurement.
- Execution time is measured using `System.nanoTime()`.
- Average execution time is calculated from the five measured runs.
- Relevant algorithm operations are counted.
- Graph generation time is excluded from algorithm execution measurements.
- A fixed random seed is used to ensure reproducibility.

### 4.3 Graph Generation

The application generates reproducible graphs using a fixed random seed.

For minimum spanning tree experiments, connected undirected graphs are generated by constructing a spanning tree first and then adding additional edges.

For directed DAG experiments, edges are oriented from lower-numbered vertices to higher-numbered vertices to prevent cycles.

For directed cyclic experiments, a cycle is deliberately introduced before additional directed edges are generated.

Edge weights are positive integers between 1 and 100.

### 4.4 Measurement Environment

The experiments were executed locally using:

- Operating system: macOS
- Development environment: IntelliJ IDEA
- Java runtime: OpenJDK 25.0.2
- Build system: Maven

The exact execution times may vary depending on hardware performance, JVM optimizations, operating system scheduling, and system load.

---

## 5. Experimental Results

The following tables contain measured results from the local benchmark execution.

Each time value represents the average of five executions.

Execution time is converted from nanoseconds to milliseconds for easier comparison.

### 5.1 Small Graph — 10 Vertices, 30 Edges

| Algorithm | Average Time (ms) | Average Operations |
|---|---:|---:|
| Kruskal | 0.0299 | 160 |
| Prim | 0.0723 | 290 |
| Kosaraju | 0.0571 | 100 |
| Topological DAG | 0.0131 | 70 |
| Topological Directed | 0.0027 | 30 |
| Dijkstra | 0.0391 | 78 |

### 5.2 Medium Graph — 100 Vertices, 500 Edges

| Algorithm | Average Time (ms) | Average Operations |
|---|---:|---:|
| Kruskal | 0.3716 | 5247 |
| Prim | 0.2903 | 9235 |
| Kosaraju | 0.3143 | 1600 |
| Topological DAG | 0.1046 | 1100 |
| Topological Directed | 0.0318 | 511 |
| Dijkstra | 0.5525 | 1201 |

### 5.3 Large Graph — 1000 Vertices, 5000 Edges

| Algorithm | Average Time (ms) | Average Operations |
|---|---:|---:|
| Kruskal | 1.1032 | 68522 |
| Prim | 1.2993 | 125936 |
| Kosaraju | 1.2522 | 16000 |
| Topological DAG | 0.5928 | 11000 |

The remaining measurements for Topological Directed and Dijkstra on the large graph should be read from the final two rows of the generated `benchmarks.csv` file.

### 5.4 Experimental Data

The complete benchmark dataset is stored in:

`results/tables/benchmarks.csv`

This CSV file contains the following fields:

- Algorithm name
- Number of vertices
- Number of edges
- Number of repetitions
- Average execution time in nanoseconds
- Average operation count

### 5.5 Execution Time vs Number of Vertices

![Execution Time vs Vertices](results/plots/execution_time_vs_vertices.png)

This graph illustrates how execution time changes as the number of vertices increases.

### 5.6 Execution Time vs Number of Edges

![Execution Time vs Edges](results/plots/execution_time_vs_edges.png)

This graph illustrates execution time across graph configurations with increasing numbers of edges.

Because both vertices and edges increase together in the prescribed datasets, these experiments do not independently isolate the effect of each variable.

---

## 6. Comparison and Discussion

### 6.1 Kruskal vs Prim

Both Kruskal's and Prim's algorithms calculate minimum spanning trees.

In the sample city graph, both algorithms produced the same minimum spanning tree weight:

**Total MST Weight = 11**

This confirms agreement between the two implementations for the sample connected undirected graph.

Experimental comparison:

| Graph Size | Kruskal (ms) | Prim (ms) |
|---|---:|---:|
| Small | 0.0299 | 0.0723 |
| Medium | 0.3716 | 0.2903 |
| Large | 1.1032 | 1.2993 |

Kruskal was faster on the small and large test configurations, while Prim was faster on the medium configuration.

These measurements show that actual performance depends on implementation details, sorting, priority queue overhead, and graph structure.

The benchmark's operation counters measure different implementation-specific activities. Therefore, the operation totals should not be treated as identical units when comparing different algorithms.

### 6.2 DSU Performance

DSU improves the efficiency of Kruskal's algorithm.

Path compression reduces the cost of repeated find operations.

Union by rank prevents the formation of unnecessarily deep trees.

Together, these techniques make component management efficient even when the graph becomes larger.

### 6.3 SCC Performance

Kosaraju's algorithm showed the following execution times:

| Vertices | Time (ms) |
|---:|---:|
| 10 | 0.0571 |
| 100 | 0.3143 |
| 1000 | 1.2522 |

Execution time increased as graph size increased.

This observation is consistent with the theoretical O(V + E) complexity, although runtime measurements are also affected by JVM and system overhead.

### 6.4 Topological Sorting

The application tests both directed acyclic graphs and directed graphs containing cycles.

For DAGs, the algorithm returns a valid topological ordering.

For cyclic graphs, the algorithm detects that topological ordering is impossible.

In the sample execution, the result was:

`hasCycle=true`

This demonstrates successful cycle detection.

Cyclic graphs may finish earlier when no vertices with zero in-degree remain, even though the worst-case complexity is still O(V + E).

### 6.5 Dijkstra Performance

The measured Dijkstra execution times include:

| Vertices | Time (ms) |
|---:|---:|
| 10 | 0.0391 |
| 100 | 0.5525 |

The observed execution time increased with graph size.

Dijkstra's algorithm calculates shortest distances and paths for non-negative weighted graphs.

Vertices that cannot be reached from the source are reported as unreachable.

Negative edge weights are rejected because they violate Dijkstra's algorithm assumptions.

### 6.6 Experimental Limitations

Benchmark execution time can vary between runs due to:

- CPU performance
- JVM warm-up and JIT compilation
- Operating system scheduling
- Memory management
- Background processes
- Graph structure

Small graphs have very short execution times, so measurement noise can have a substantial effect.

The reported averages provide a useful comparison but should not be interpreted as exact universal performance values.

---

## 7. Testing and Correctness

The project includes automated correctness checks.

The following cases are tested:

| Test Case | Expected Behavior |
|---|---|
| Connected graph | Minimum spanning tree is found |
| Disconnected graph | Disconnection is detected |
| Multiple valid MSTs | Algorithms return the same minimum total weight |
| Duplicate edge weights | MST algorithms handle equal weights |
| Directed acyclic graph | Valid topological order is returned |
| Directed cyclic graph | Cycle is detected |
| Strongly connected components | Every vertex belongs to exactly one SCC |
| Unreachable vertices | Dijkstra reports unreachable locations |
| Single-vertex graph | Algorithms handle the special case |
| Negative edge weights | Dijkstra rejects invalid input |
| DSU operations | Find and union work correctly |

### Test Execution

The test class is:

`com.smartcity.SelfTest`

The tests were executed successfully on macOS using IntelliJ IDEA.

Output:

```text
PASS: 29 correctness checks

Process finished with exit code 0
```

All 29 built-in correctness checks passed.

The Maven test lifecycle also completed successfully with `BUILD SUCCESS`.

---

## 8. How to Run the Project

### Requirements

- Java JDK 17 or newer
- IntelliJ IDEA or another Java IDE
- Maven

### Step 1 — Open the Project

Open the project directory in IntelliJ IDEA.

Make sure the Java SDK is configured correctly.

### Step 2 — Run the Main Application

Open:

`src/main/java/com/smartcity/Main.java`

Run the `Main` class.

The console displays:

- Kruskal MST results
- Prim MST results
- Dijkstra shortest paths
- Strongly connected components
- Topological sorting results

### Step 3 — Execute Correctness Tests

Open:

`src/main/java/com/smartcity/SelfTest.java`

Run the `SelfTest` class.

Expected output:

```text
PASS: 29 correctness checks
```

### Step 4 — Execute Benchmark

Run:

`com.smartcity.experiment.Benchmark`

The program measures algorithm execution time and saves the results to:

`results/tables/benchmarks.csv`

### Step 5 — Generate Performance Plots

The project includes the Python script:

`scripts/plot_results.py`

To regenerate the plots, install Python 3 and matplotlib, then run:

```bash
python3 -m pip install matplotlib
python3 scripts/plot_results.py
```

The generated plots are stored in:

- `results/plots/execution_time_vs_vertices.png`
- `results/plots/execution_time_vs_edges.png`

---

## 9. GitHub Repository and Project Structure

Repository:

https://github.com/aerkenovva/assignment-3-smart-city

Project structure:

```text
assignment-3-smart-city/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── smartcity/
│   │               ├── graph/
│   │               ├── dsu/
│   │               ├── mst/
│   │               ├── connectivity/
│   │               ├── shortestpath/
│   │               └── experiment/
│   └── test/
├── results/
│   ├── tables/
│   │   └── benchmarks.csv
│   └── plots/
│       ├── execution_time_vs_vertices.png
│       └── execution_time_vs_edges.png
├── scripts/
│   └── plot_results.py
├── pom.xml
└── README.md
```

The project follows a modular design, with separate classes for different graph algorithms.

The GitHub repository contains the source code, experiment results, documentation, and visualization files.

Development changes should be recorded using meaningful Git commits and feature branches when appropriate.

Generated build outputs and IDE-specific files should not be included in the repository.

---

## 10. Conclusion

This project demonstrates how graph algorithms can be applied to smart city infrastructure planning.

Kruskal's and Prim's algorithms help determine minimum-cost infrastructure networks.

DSU efficiently manages connected components.

Topological sorting provides a valid ordering for directed acyclic infrastructure dependencies.

Kosaraju's algorithm identifies strongly connected components in directed road networks.

Dijkstra's algorithm calculates shortest routes between districts when edge weights are non-negative.

The experimental analysis demonstrates how algorithm performance changes across different graph sizes.

All 29 built-in correctness checks passed, and the application executed successfully on macOS using Java and IntelliJ IDEA.

Overall, the project demonstrates the practical applications, strengths, limitations, and performance characteristics of graph algorithms for city infrastructure and route planning.
