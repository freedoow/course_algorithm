package com.course.graph.direct;

import com.course.graph.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.TreeSet;

/**
 * @author freed
 * @Description: 无向无权图 有向无权图
 * @Date 2022-08-14
 */
public class AdjacentHash implements Graph {
    private int V;
    private int E;
    private TreeSet<Integer>[] data;

    private boolean isDirected;

    private int[] inDegress;
    private int[] outDegress;

    public AdjacentHash(String name, boolean isDirected) {
        this.isDirected = isDirected;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(name));
            String line = reader.readLine();
            String[] arr = line.split(" ");

            this.V = Integer.valueOf(arr[0]);
            this.E = Integer.valueOf(arr[1]);
            this.data = new TreeSet[V];
            for (int i = 0; i < V; i++) {
                data[i] = new TreeSet<>();
            }

            this.inDegress =new int[V];
            this.outDegress =new int[V];
            while ((line = reader.readLine()) != null) {
                arr = line.split(" ");
                int a = Integer.valueOf(arr[0]);
                validateVertex(a);
                int b = Integer.valueOf(arr[1]);
                validateVertex(b);

                if (a == b) throw new RuntimeException("出现了自环边，错误");
                if (data[a].contains(b)) throw new RuntimeException("出现了平行边边，错误");

                data[a].add(b);
                if (!isDirected) {
                    data[b].add(a);
                }else { //有向图
                    outDegress[a]++;
                    outDegress[b]++;
                }
            }


        } catch (Exception e) {

        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("顶点数 = %d，边数 = %d，isDirected = %b \n", V, E, isDirected));
        for (int i = 0; i < V; i++) {
            sb.append(i + ": ");

            for (Integer j : data[i]) {
                sb.append(j + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException(String.format("顶点 %d 不合格", v));
    }

    @Override
    public int getV() {
        return V;
    }

    @Override
    public int getE() {
        return E;
    }

    // 判断两个指定的顶点之间是否有边
    @Override
    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return data[v].contains(w);
    }

    // 获取指定顶点所有相邻的顶点
    @Override
    public Collection<Integer> getTargetVAllV(int v) {
        validateVertex(v);
        return data[v];
    }

    // 获取指定顶点的度数
    @Override
    public int degree(int v) {
        if (isDirected) throw new RuntimeException("无向图才可以计算");

        return getTargetVAllV(v).size();
    }

    public boolean isDirected() {
        return isDirected;
    }

    public int[] getInDegress() {
        if (!isDirected)  throw new RuntimeException("有向图才可以计算");
        return inDegress;
    }

    public int[] getOutDegress() {
        if (!isDirected)  throw new RuntimeException("有向图才可以计算");
        return outDegress;
    }

    public static void main(String[] args) {

        AdjacentHash adjacentMatrix = new AdjacentHash("/Users/whb/code/algo/algorithm/data/graph.txt", true);
        System.out.println(adjacentMatrix);
    }
}
