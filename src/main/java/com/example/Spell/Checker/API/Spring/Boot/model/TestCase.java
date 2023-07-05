package com.example.Spell.Checker.API.Spring.Boot.model;

public class TestCase {
    private String right;
    private String wrong;
    public TestCase(String right, String wrong) {
        this.right = right;
        this.wrong = wrong;
    }

    public String getRight() {
        return right;
    }

    public String getWrong() {
        return wrong;
    }
}
