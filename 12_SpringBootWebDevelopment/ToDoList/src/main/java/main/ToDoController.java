package main;


import main.model.ToDo;
import main.model.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ToDoController
{
    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping("/list/")
    public List<ToDo> list()
    {
        Iterable<ToDo> toDoIterable = toDoRepository.findAll();
        ArrayList<ToDo> toDos = new ArrayList<>();
        for (ToDo toDo : toDoIterable) {
            toDos.add(toDo);
        }
        return toDos;
    }

    @PostMapping("/list/")
    public int add(ToDo toDo)
    {
        ToDo newToDo = toDoRepository.save(toDo);
        return newToDo.getId();
    }

    @PutMapping("/list/")
    public ToDo edit(ToDo toDo)
    {
        toDoRepository.save(toDo);
        return toDo;
    }

    @DeleteMapping("/list/{id}")
    public void delete(@PathVariable int id)
    {
        toDoRepository.deleteById(id);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity get(@PathVariable int id)
    {
        Optional<ToDo> optionalToDo = toDoRepository.findById(id);
        if(!optionalToDo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalToDo.get(), HttpStatus.OK);
    }
}
