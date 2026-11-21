package model;

import java.util.ArrayList;

public class TreeNode {

    private String data;
    private ArrayList<TreeNode> children;

    public TreeNode(String data) {

        this.data = data;
        this.children = new ArrayList<>();

    }

    public String getData() {

        return data;

    }

    public ArrayList<TreeNode> getChildren() {

        return children;

    }

    public void addChild(TreeNode child) {

        children.add(child);

    }

}