public class UnionFind {
    private int[] id;
    private int[] size;
    private int count;
    private int totalConnections;
    public UnionFind(int count) {
        this.count = count;
        id = new int[count];
        size = new int[count];
        totalConnections = 0;
        for(int i=0;i<count;i++) {
            id[i] = i;
            size[i] = 1; 
        }
    }

    public int getCount() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        while(id[p] != p) {
            p = id[p];
        }
        return p;
    }

    public void union(int p, int q) {
        p = find(p);
        q = find(q);

        if(p == q) {
            return;
        }
        else if (size[p] < size[q]) {
            id[p] = id[q];
            size[q] += size[p];
        }
        else {
            id[q] = id[p];
            size[p] += size[q];
        }
        count--;
        totalConnections++;

    }


}
