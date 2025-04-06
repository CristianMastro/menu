package pizzeria.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pizzeria.menu.model.Pizza;
import pizzeria.menu.repository.PizzaRepository;



@Controller
@RequestMapping("/pizze")
public class PizzeriaController {

    @Autowired
    private PizzaRepository pizzaRepository;
    
    @GetMapping
    public String index(Model model, @RequestParam(name="keyword", required =false) String nome) {
        List<Pizza> menu;
        if(nome !=null && !nome.isBlank()){
            menu= pizzaRepository.findByNomeContainingIgnoreCase(nome);
        } else {
            menu = pizzaRepository.findAll();
        }
        model.addAttribute("lista", menu);
        return "pizze/index";
    }
    

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).orElse(null));
        return "pizze/show";
    }
    



}
