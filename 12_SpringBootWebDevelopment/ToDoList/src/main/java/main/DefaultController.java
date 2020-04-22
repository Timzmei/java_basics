/**
 *     URL             Method              Description
 *
 *     /list/          GET                 Список всех задач
 *
 *     [
 *          {
 *              "id": 23,
 *              "name": "Заполнить БД",
 *              "date": 01.01.2001,
 *              "description": "БД должна содержать поля..... ",
 *          },
 *          {
 *              "id": 24,
 *              "name": "Сходить в магазин",
 *              "date": 01.04.2020,
 *              "description": "Купить: гречку, туалетную бумагу, дробовик, патроны",
 *          },
 *      ]
 *
 *     /list/ID        GET                 Конкретная задача
 *
 *     {
 *         "id": 23,
 *         "name": "Заполнить БД",
 *         "date": 01.01.2001,
 *         "description": "БД должна содержать поля..... ",
 *     }
 *
 *     /list/          POST                Добавление новой задачи
 *
 *     {
 *         "name": "Заполнить БД",
 *         "date": 01.01.2001,
 *         "description": "БД должна содержать поля..... ",
 *     }
 *
 *
 *
 *     /list/ID        PUT                 Сохранение изменений задачи целиком
 *
 *     {
 *         "name": "Заполнить БД",
 *         "date": 01.01.2001,
 *         "description": "БД должна содержать поля..... ",
 *     }
 *
 *     /list/ID        PATCH               Сохранение изменений отдельных свойств
 *
 *     {
 *         "description": "БД должна содержать поля..... ",
 *     }
 *
 *     /list/ID        DELETE              Удаление задачи
 *
 *
 *
 *
 *
 */

package main;

import main.model.ToDo;
import main.model.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;


@Controller
public class DefaultController
{
    @Autowired
    ToDoRepository toDoRepository;

    @RequestMapping("/")
    public String index(Model model){
        Iterable<ToDo> toDoIterable = toDoRepository.findAll();
        ArrayList<ToDo> toDos = new ArrayList<>();
        for (ToDo toDo : toDoIterable){
            toDos.add(toDo);

        }
        model.addAttribute("list", toDos);


        return "index";
    }

}
