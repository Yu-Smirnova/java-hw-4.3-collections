package ru.netology.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issue implements Comparable<Issue>{
    private int id;
    private String name;
    private boolean open;
    private String author;
    private Set<String> label;
    private String project;
    private Set<String> milestone;
    private String assignee;
    private int createDaysAgo;

    @Override
    public int compareTo(Issue o){
        return this.createDaysAgo - o.getCreateDaysAgo();
    }

}
