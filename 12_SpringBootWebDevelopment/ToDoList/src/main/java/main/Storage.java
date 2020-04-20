package main;

import main.model.ToDo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage
{
    private static int currentId = 1;
    private static HashMap<Integer, ToDo> toDos = new HashMap<Integer, ToDo>();

    public static List<ToDo> getAllToDos()
    {
        ArrayList<ToDo> toDoArrayList = new ArrayList<ToDo>();
        toDoArrayList.addAll(toDos.values());
        return toDoArrayList;
    }

    public static int addToDo(ToDo toDo)
    {
        int id = currentId++;
        toDo.setId(id);
        toDo.setStatus();
        toDos.put(id, toDo);

        return id;
    }

    public static ToDo editToDo(ToDo toDo)
    {
        int id = toDo.getId();
//        toDo.setStatus();
        toDos.replace(id, toDo);

        return toDo;
    }

    public static ToDo getToDo(int todoId){
        if(toDos.containsKey(todoId)){
            return toDos.get(todoId);
        }
        return null;
    }

    public static void deleteToDo(int todoId){
        if(toDos.containsKey(todoId)){
            toDos.remove(todoId);
        }
    }
}
