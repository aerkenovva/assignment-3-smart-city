# Assignment 3 — Smart City Infrastructure and Route Planning

## 1. Overview

This project is a Java application that simulates smart city infrastructure and route planning using graph algorithms.

The main objectives are to:

- Connect city districts with minimum infrastructure cost.
- Analyze connectivity between districts.
- Identify strongly connected components.
- Determine valid dependency ordering.
- Calculate shortest routes between locations.
- Evaluate algorithm performance on different graph sizes.

The city is represented as a weighted graph:

- **Vertices (V):** City districts or locations.
- **Edges (E):** Roads or infrastructure connections.
- **Weights:** Road construction costs or travel distances.

The application supports directed and undirected graphs.

Undirected graphs are used for minimum spanning tree calculations. Directed graphs represent one-way roads and infrastructure dependencies.

### Implemented Algorithms

1. Disjoint Set Union (DSU)
2. Kruskal's Minimum Spanning Tree
3. Prim's Minimum Spanning Tree
4. Topological Sorting using Kahn's Algorithm
5. Strongly Connected Components using Kosaraju's Algorithm
6. Dijkstra's Shortest Path Algorithm

The project also includes performance benchmarking, complexity analysis, automated correctness checks, experimental tables, and graphical visualizations.

---

## 2. Implementation

The application is implemented in Java using a modular structure.

Each algorithm is organized into a separate package to improve readability, maintainability, and testing.

### 2.1 Graph Representation

The `Graph` class represents the city's infrastructure using an adjacency list.

An adjacency list was selected because it stores graph connections efficiently and is suitable for sparse graphs.

For undirected graphs, connections are stored in both directions in the adjacency lists.

For directed graphs, each connection represents a specific direction.

### 2.2 Disjoint Set Union (DSU)

DSU is a data structure used to manage connected components.

It supports two main operations:

- `find()` — determines the representative of a component.
- `union()` — merges two different components.

The implementation includes two optimizations:

**Path Compression:** Reduces the depth of DSU trees during find operations.

**Union by Rank:** Keeps the DSU trees balanced when components are merged.

These techniques make repeated operations efficient, with nearly constant amortized time complexity.

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

The implementation uses a priority queue to select minimum-weight edges.

Implementation steps:

1. Select a starting vertex.
2. Insert candidate edges into the priority queue.
3. Select the minimum-weight edge leading to an unvisited vertex.
4. Add the vertex and update the queue.
5. Continue until the connected component is covered.

For disconnected graphs, the implementation supports a minimum spanning forest.

Both Kruskal and Prim must produce the same total MST weight for connected undirected weighted graphs.

### 2.5 Topological Sorting

Topological sorting determines a valid ordering of vertices in a directed acyclic graph (DAG).

The implementation uses Kahn's algorithm.

Steps:

1. Calculate the in-degree of every vertex.
2. Find vertices with zero in-degree.
3. Add these vertices to the ordering.
4. Remove their outgoing dependencies.
5. Repeat until all possible vertices are processed.

If the number of processed vertices is smaller than the total number of vertices, the graph contains a cycle.

In this case, a valid topological ordering does not exist.

### 2.6 Strongly Connected Components (SCC)

The project uses Kosaraju's algorithm to identify strongly connected components in directed graphs.

A strongly connected component is a group of vertices where every vertex is reachable from every other vertex in the same component.

Kosaraju's algorithm consists of two main phases:

1. Perform DFS and determine the processing order.
2. Reverse the graph and perform DFS in reverse finishing order.

The implementation uses iterative DFS.

The algorithm identifies all strongly connected components.

### 2.7 Dijkstra's Algorithm

Dijkstra's algorithm calculates the shortest paths from a selected source vertex to all reachable vertices.

The implementation uses a priority queue and adjacency list.

Steps:

1. Set the source distance to zero.
2. Set other distances to infinity.
3. Select the vertex with the smallest candidate distance.
4. Examine its outgoing edges.
5. Update distances when shorter paths are found.
6. Continue until the priority queue is empty.

The algorithm records shortest distances and reconstructed paths.

Unreachable vertices are handled separately.

Dijkstra's algorithm requires non-negative edge weights because negative weights invalidate the greedy assumption used in shortest-path calculations.

---

## 3. Complexity Analysis

Let:

- V = Number of vertices
- E = Number of edges
- α(V) = Inverse Ackermann function

The complexity analysis assumes adjacency lists, comparison-based sorting, optimized DSU, and binary priority queues.

| Algorithm | Time Complexity | Space Complexity |
|---|---|---|
| DSU Find / Union | O(α(V)) amortized | O(V) |
| Kruskal | O(E log E + Eα(V)) | O(V + E) |
| Prim | O((V + E) log(V + E)) | O(V + E) |
| Topological Sort | O(V + E) | O(V) |
| Kosaraju SCC | O(V + E) | O(V + E) |
| Dijkstra | O((V + E) log(V + E)) | O(V + E) |

### 3.1 DSU Complexity

Path compression and union by rank reduce tree depth and improve the efficiency of repeated operations.

The amortized time complexity of each operation is O(α(V)), where α is the inverse Ackermann function, which grows extremely slowly.

The space complexity is O(V).

### 3.2 Kruskal Complexity

Kruskal sorts edges by weight, requiring O(E log E).

DSU operations add O(Eα(V)) amortized time.

The algorithm uses O(V + E) additional space, including the data structures required to store graph edges and components.

### 3.3 Prim Complexity

Prim uses a binary priority queue.

In this implementation, candidate edges may be inserted into the priority queue, giving O((V + E) log(V + E)) time.

Space complexity is O(V + E).

### 3.4 Topological Sorting Complexity

Kahn's algorithm processes each vertex and edge a bounded number of times.

Time complexity: O(V + E).

Auxiliary space complexity: O(V).

### 3.5 Kosaraju Complexity

Kosaraju performs two DFS passes and constructs the reversed graph.

Time complexity: O(V + E).

Space complexity: O(V + E).

### 3.6 Dijkstra Complexity

Dijkstra uses a binary priority queue to process candidate shortest distances.

Time complexity: O((V + E) log(V + E)).

Space complexity: O(V + E).

---

## 4. Experimental Setup

The performance of the algorithms was evaluated using three graph configurations.

### 4.1 Graph Configurations

| Graph Size | Vertices (V) | Edges (E) |
|---|---:|---:|
| Small | 10 | 30 |
| Medium | 100 | 500 |
| Large | 1000 | 5000 |

### 4.2 Experimental Method

The experiments follow these conditions:

- Every algorithm is executed five times for each graph size.
- Three warm-up runs are performed before measurements.
- Execution time is measured using `System.nanoTime()`.
- Average execution time is calculated from five measured runs.
- Relevant operations are counted.
- Graph generation time is excluded from algorithm execution measurements.
- Fixed random seeds ensure reproducibility.

### 4.3 Graph Generation

The application generates reproducible graphs using fixed random seeds.

For minimum spanning tree experiments, connected undirected graphs are generated by constructing a spanning tree first and adding additional edges.

For directed DAG experiments, edges are oriented from lower-numbered vertices to higher-numbered vertices to prevent cycles.

For directed cyclic experiments, a cycle is deliberately introduced before additional directed edges are generated.

Edge weights are positive integers between 1 and 100.

### 4.4 Measurement Environment

The experiments were executed locally using:

- **Operating System:** macOS
- **Development Environment:** IntelliJ IDEA
- **Java Runtime:** OpenJDK 25.0.2
- **Build System:** Maven
- **Programming Language:** Java

Execution times may vary depending on hardware performance, JVM optimizations, background processes, and operating system scheduling.

---

## 5. Experimental Results

Each algorithm was executed five times for each graph size.

The following tables present the measured average execution times and average operation counts.

Execution times were converted from nanoseconds to milliseconds for easier interpretation.

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
| Topological Directed | 0.0708 | 5037 |
| Dijkstra | 1.1046 | 11921 |

### 5.4 Complete Experimental Data

The complete experimental results are stored in:

`results/tables/benchmarks.csv`

The CSV file includes:

- Algorithm name
- Number of vertices
- Number of edges
- Number of repetitions
- Average execution time in nanoseconds
- Average operation count

A total of 18 benchmark configurations were evaluated.

### 5.5 Execution Time vs Number of Vertices

![Execution Time vs Vertices](results/plots/execution_time_vs_vertices.png)

This plot illustrates how execution time changes when graph size increases from 10 to 1000 vertices.

### 5.6 Execution Time vs Number of Edges

![Execution Time vs Edges](results/plots/execution_time_vs_edges.png)

This plot illustrates execution time for graph configurations containing 30, 500, and 5000 edges.

Because both vertices and edges increase together in these configurations, these experiments do not independently isolate the effect of each variable.

---

## 6. Comparison and Discussion

### 6.1 Kruskal vs Prim

Both algorithms calculate minimum spanning trees.

For the sample connected undirected graph, both produced the same result:

**Total MST Weight = 11**

This confirms agreement between the two implementations for the sample graph.

Performance comparison:

| Graph Size | Kruskal (ms) | Prim (ms) | Faster Algorithm |
|---|---:|---:|---|
| Small | 0.0299 | 0.0723 | Kruskal |
| Medium | 0.3716 | 0.2903 | Prim |
| Large | 1.1032 | 1.2993 | Kruskal |

Kruskal was faster on the small and large graph configurations.

Prim performed better on the medium graph configuration.

The results demonstrate that actual performance depends on graph size, graph structure, sorting, priority queue operations, and implementation overhead.

### 6.2 DSU Optimization

DSU improves Kruskal's efficiency by quickly determining whether two vertices belong to different components.

Path compression reduces the length of repeated search paths.

Union by rank prevents DSU trees from becoming unnecessarily deep.

Together, these optimizations improve the performance of connectivity checks.

### 6.3 Strongly Connected Components

Kosaraju's algorithm produced the following results:

| Vertices | Time (ms) | Operations |
|---|---:|---:|
| 10 | 0.0571 | 100 |
| 100 | 0.3143 | 1600 |
| 1000 | 1.2522 | 16000 |

The operation count increases with the number of vertices and edges.

The observed trend is consistent with the theoretical O(V + E) complexity.

### 6.4 Topological Sorting

The application evaluates two types of directed graphs:

- Directed acyclic graphs (DAGs)
- Directed graphs containing cycles

For DAGs, the algorithm returns a valid topological ordering.

For cyclic graphs, it detects that a valid topological ordering is impossible.

Sample output:

```text
Topological sort: Result[order=[], hasCycle=true, operations=5]
```

The cyclic benchmark may finish earlier because Kahn's algorithm cannot continue once no vertices with zero in-degree remain.

The worst-case complexity remains O(V + E).

### 6.5 Dijkstra Performance

| Vertices | Average Time (ms) | Operations |
|---|---:|---:|
| 10 | 0.0391 | 78 |
| 100 | 0.5525 | 1201 |
| 1000 | 1.1046 | 11921 |

The execution time and operation count generally increase with graph size.

The algorithm calculates shortest paths using non-negative edge weights and a priority queue.

For the sample graph, the shortest path from vertex 0 to vertex 4 was:

`0 → 2 → 1 → 3 → 4`

**Total Distance = 11**

The algorithm also handles unreachable vertices by reporting them separately.

### 6.6 Operation Counts

The benchmark counts relevant operations performed by each algorithm.

However, these counters represent different algorithm-specific activities.

Therefore, operation totals provide useful information about work performed but should not be interpreted as identical units across all algorithms.

### 6.7 Experimental Limitations

Execution time may vary between benchmark runs due to:

- CPU performance
- JVM warm-up and JIT compilation
- Background applications
- Memory management
- Operating system scheduling
- Graph density and structure

Small graphs execute quickly, so measurement noise can influence results substantially.

The experimental averages provide useful comparisons but do not establish universal performance rankings.

---

## 7. Testing and Correctness

The project includes automated correctness checks for the graph algorithms.

### 7.1 Test Cases

| Test Case | Expected Behavior |
|---|---|
| Connected undirected graph | MST is calculated |
| Disconnected graph | Disconnection is detected |
| Multiple valid MSTs | Same minimum total weight |
| Duplicate edge weights | Algorithms handle equal weights |
| Directed acyclic graph | Valid topological ordering |
| Directed cyclic graph | Cycle is detected |
| Strongly connected components | Every vertex belongs to one SCC |
| Unreachable vertices | Dijkstra reports unreachable locations |
| Single-vertex graph | Algorithms handle the special case |
| Negative edge weights | Dijkstra rejects invalid input |
| DSU operations | Find and union work correctly |

### 7.2 Correctness Verification

The following conditions are checked:

1. Kruskal and Prim return the same MST weight on connected undirected weighted graphs.
2. Topological ordering is valid for DAGs.
3. Directed cycles are detected.
4. Each vertex belongs to exactly one strongly connected component.
5. Dijkstra calculates correct shortest distances for non-negative edge weights.

### 7.3 Test Execution

The test class is:

`com.smartcity.SelfTest`

The tests were executed in IntelliJ IDEA on macOS.

Output:

```text
PASS: 29 correctness checks

Process finished with exit code 0
```

**All 29 built-in correctness checks passed successfully.**

The Maven test lifecycle also completed with:

```text
BUILD SUCCESS
```

---

## 8. How to Run the Project

### Requirements

- Java JDK 17 or newer
- IntelliJ IDEA or another Java IDE
- Maven

### Step 1 — Open the Project

Open the project directory in IntelliJ IDEA.

Make sure the project SDK is configured correctly.

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

### Step 3 — Run Correctness Tests

Open:

`src/main/java/com/smartcity/SelfTest.java`

Run `SelfTest.main()`.

Expected output:

```text
PASS: 29 correctness checks
```

### Step 4 — Run Benchmark

Run the class:

`com.smartcity.experiment.Benchmark`

The program measures execution time and saves results to:

`results/tables/benchmarks.csv`

### Step 5 — Generate Plots

The project includes the Python script:

`scripts/plot_results.py`

To regenerate performance plots, install Python 3 and matplotlib.

Run:

```bash
python3 -m pip install matplotlib
python3 scripts/plot_results.py
```

The resulting plots are stored in:

- `results/plots/execution_time_vs_vertices.png`
- `results/plots/execution_time_vs_edges.png`

---

## 9. Project Structure and GitHub

### Repository

https://github.com/aerkenovva/assignment-3-smart-city

### Project Structure

```text
assignment-3-smart-city/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── smartcity/
│   │               ├── Main.java
│   │               ├── SelfTest.java
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

The project follows a modular structure with separate packages for graph algorithms.

The GitHub repository is used to store Java source code, documentation, experiment results, and visualizations.

Changes can be organized using meaningful Git commits and feature branches.

Generated build outputs and IDE-specific files should be excluded from version control.

---

## 10. Conclusion

This project demonstrates the application of graph algorithms to smart city infrastructure planning.

**Kruskal's and Prim's algorithms** help determine minimum-cost infrastructure networks.

**Disjoint Set Union** efficiently manages connected components and supports Kruskal's algorithm.

**Topological Sorting** determines a valid ordering of infrastructure dependencies when the directed graph contains no cycles.

**Kosaraju's Algorithm** identifies strongly connected components in directed networks.

**Dijkstra's Algorithm** calculates shortest routes between city districts with non-negative edge weights.

The experimental evaluation demonstrates how execution time and operation counts change as graph sizes increase.

Results show that Kruskal and Prim can have different execution times while producing the same minimum spanning tree weight.

All 29 built-in correctness checks passed, and the application executed successfully on macOS using Java and IntelliJ IDEA.

Overall, the project demonstrates the strengths, limitations, and practical applications of graph algorithms for efficient smart city infrastructure and route planning.
