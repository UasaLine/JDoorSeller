package com.jds.model;

import java.util.ArrayList;
import java.util.List;

public class CostList {

    int totalCost;
    List<LineCostList> list;



    public class LineCostList {

        String name;
        int group;
        Boolean headline;
        int cost;

        public LineCostList() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        public Boolean getHeadline() {
            return headline;
        }

        public void setHeadline(Boolean headline) {
            this.headline = headline;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }
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
}
