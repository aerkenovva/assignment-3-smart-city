"""Generate required plots from actual measured benchmark CSV. Requires matplotlib."""
import csv
from pathlib import Path
import matplotlib.pyplot as plt
ROOT = Path(__file__).resolve().parents[1]
with (ROOT / "results/tables/benchmarks.csv").open(newline="") as f:
    rows = list(csv.DictReader(f))
algorithms = sorted({r['algorithm'] for r in rows})
for x, filename, xlabel in [('vertices','execution_time_vs_vertices.png','Vertices (V)'),('edges','execution_time_vs_edges.png','Edges (E)')]:
    plt.figure(figsize=(9,5.5))
    for name in algorithms:
        series = sorted((r for r in rows if r['algorithm']==name), key=lambda r:int(r[x]))
        plt.plot([int(r[x]) for r in series], [float(r['average_time_ns'])/1e6 for r in series], marker='o',label=name)
    plt.xlabel(xlabel);plt.ylabel('Average time (ms)');plt.title('Algorithm runtime vs '+xlabel)
    plt.legend();plt.grid(alpha=.25);plt.tight_layout()
    out=ROOT/'results/plots'/filename;plt.savefig(out,dpi=160);plt.close();print('Saved',out)
