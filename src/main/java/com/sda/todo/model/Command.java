package com.sda.todo.model;

import java.util.HashMap;
import java.util.Map;

public class Command {

    private int option;
    private Map<String, Object> arguments;

    public Command(int option) {
        this.option = option;
        this.arguments = new HashMap();
    }
    public void addArgument(String key, Object value){
        arguments.put(key,value);
    }

    public Object getArgument(String key) {
        return arguments.get(key);
    }

//    public int getOption() {
//    }
}
