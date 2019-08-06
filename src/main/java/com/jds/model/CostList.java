package com.jds.model;

import java.util.ArrayList;
import java.util.List;

public class CostList {

    int totalCost;
    List<LineCostList> list;

    public void addLine(String name,int group,boolean headline,int cost){
       list.add(new LineCostList(name,group,headline,cost));
       totalCost +=cost;
   }

    public CostList() {
        totalCost = 0;
        list = new ArrayList<>();
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public List<LineCostList> getCostList() {
        return list;
    }

    public void setCostList(List<LineCostList> list) {
        this.list = list;
    }

    public List<LineCostList> getList() {
        return list;
    }

    public void setList(List<LineCostList> list) {
        this.list = list;
    }
}
