package pizzeria.menu.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import pizzeria.menu.model.Offerta;
import pizzeria.menu.repository.OffertaRepository;

@Controller
@RequestMapping("/offerte")
public class OffertaController {
    
    @Autowired
    private OffertaRepository offertaRepository;

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("promo") Offerta formOfferta, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            return "offerte/create";
        }

        if(formOfferta.getInizioOfferta().isBefore(LocalDate.now())){
            bindingResult.rejectValue("inizioOfferta", "invalidData", "La data di inizio non può essere nel passato.");
            return "offerte/create";
        } else if(formOfferta.getFineOfferta().isBefore(formOfferta.getInizioOfferta())) {
            bindingResult.rejectValue("fineOfferta", "invalidData", "La data di fine non può essere precedente a quella di inizio");
            return "offerte/create";
        }
        offertaRepository.save(formOfferta);
        redirectAttributes.addFlashAttribute("successMessage", "Offerta creata con successo!");
        
        return "redirect:/pizze/show/" + formOfferta.getPizza().getId();
    }





}
