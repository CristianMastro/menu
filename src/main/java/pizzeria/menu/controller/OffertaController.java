package pizzeria.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import pizzeria.menu.model.Offerta;
import pizzeria.menu.repository.OffertaRepository;

@Controller
@RequestMapping("/offerte")
public class OffertaController {
    
    @Autowired
    private OffertaRepository offertaRepository;

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("promo") Offerta formOfferta, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "offerte/create";
        }

        offertaRepository.save(formOfferta);
        return "redirect:/pizze/show/" + formOfferta.getPizza().getId();
    }





}
