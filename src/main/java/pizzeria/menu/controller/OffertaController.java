package pizzeria.menu.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import pizzeria.menu.model.Offerta;
import pizzeria.menu.model.Pizza;
import pizzeria.menu.repository.OffertaRepository;
import org.springframework.web.bind.annotation.GetMapping;



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
        }
        if (formOfferta.getFineOfferta() != null && formOfferta.getFineOfferta().isBefore(formOfferta.getInizioOfferta())) {
            bindingResult.rejectValue("fineOfferta", "invalidData", "La data di fine non può essere precedente a quella di inizio");
            return "offerte/create";
        }
        offertaRepository.save(formOfferta);
        redirectAttributes.addFlashAttribute("successMessage", "Offerta creata con successo!");

        return "redirect:/pizze/show/" + formOfferta.getPizza().getId();
    }

    // Metodo per eliminare un'offerta
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
    // Trova l'offerta nel database
    Optional<Offerta> offertaOptional = offertaRepository.findById(id);

    if (offertaOptional.isPresent()) {
        // Ottieni l'ID della pizza associata all'offerta
        Pizza pizza = offertaOptional.get().getPizza();
        
        // Elimina l'offerta
        offertaRepository.deleteById(id);
        
        // Aggiungi un messaggio di successo
        redirectAttributes.addFlashAttribute("successMessage", "Offerta eliminata con successo!");
        return "redirect:/pizze/show/" + pizza.getId();
    }

    // Se l'offerta non esiste, reindirizza comunque alla pagina delle pizze
    return "redirect:/pizze";
    }

       
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Model model) {

        Offerta offerta = offertaRepository.findById(id).get();
        model.addAttribute("promo", offerta);

        return "offerte/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid @ModelAttribute("promo") Offerta formOfferta, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if(bindingResult.hasErrors()){
            return "offerte/edit";
        }

        if(formOfferta.getInizioOfferta().isBefore(LocalDate.now())){
            bindingResult.rejectValue("inizioOfferta", "invalidData", "La data di inizio non può essere nel passato.");
            return "offerte/edit";
        }
        if (formOfferta.getFineOfferta() != null && formOfferta.getFineOfferta().isBefore(formOfferta.getInizioOfferta())) {
            bindingResult.rejectValue("fineOfferta", "invalidData", "La data di fine non può essere precedente a quella di inizio");
            return "offerte/edit";
        }
        offertaRepository.save(formOfferta);
        redirectAttributes.addFlashAttribute("successMessage", "Offerta modificata con successo!");

        return "redirect:/pizze/show/" + formOfferta.getPizza().getId();
    }
    
    
}

