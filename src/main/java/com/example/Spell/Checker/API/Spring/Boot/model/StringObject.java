package com.example.Spell.Checker.API.Spring.Boot.model;

import com.example.Spell.Checker.API.Spring.Boot.service.SpellCorrectService;

public class StringObject {    private String name;
    public StringObject(String name, long hashValue) {
        this.name = name;
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }

        String objName = ((StringObject) obj).getName();
        return this.name.equals(objName);
    }

    @Override
    public int hashCode() {
//        System.out.println("hashcode called");
        return (int) SpellCorrectService.computeWordHashValue(this.name);
    }

    public String getName() {
        return name;
    }

}
