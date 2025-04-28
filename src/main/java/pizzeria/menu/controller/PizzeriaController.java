package pizzeria.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import pizzeria.menu.model.Offerta;
import pizzeria.menu.model.Pizza;
import pizzeria.menu.repository.PizzaRepository;
import pizzeria.menu.repository.OffertaRepository;


@Controller
@RequestMapping("/pizze")
public class PizzeriaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired 
    private OffertaRepository offertaRepository;
    
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
    

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "/pizze/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult ,Model model) {

        if (bindingResult.hasErrors()) {
            return "/pizze/create";
        }

        pizzaRepository.save(formPizza);
        return "redirect:/pizze";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        return "/pizze/edit";
    }
    
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute ("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            return "pizze/edit";
        }

        pizzaRepository.save(formPizza);
        return "redirect:/pizze";
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {

        Pizza pizza = pizzaRepository.findById(id).get();
        for (Offerta offerte : pizza.getOfferte()){
            offertaRepository.deleteById(offerte.getId());
        }
        pizzaRepository.deleteById(id);
        return "redirect:/pizze";
    }
    
    @GetMapping("/{id}/offerte")
    public String create(@PathVariable Integer id, Model model) {
        Offerta promo = new Offerta();
        promo.setPizza(pizzaRepository.findById(id).get());

        model.addAttribute("promo", promo);
        return "offerte/create";
    }

}
