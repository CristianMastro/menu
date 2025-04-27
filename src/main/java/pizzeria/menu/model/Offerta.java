package pizzeria.menu.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
public class Offerta {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message="Inserisci una data da oggi in poi")
    private LocalDate inizioOfferta;

    private LocalDate fineOfferta;

    @NotNull(message="Inserisci un valore da 1 a 90%")
    @DecimalMin(value = "1.0", message = "La percentuale di sconto deve essere almeno di 1%")
    @DecimalMax(value = "90.0", message = "La percentuale di sconto deve essere massimo 90%")
    private Integer percentualeSconto;

    @ManyToOne
    @JoinColumn(name="pizza_id", nullable=false)
    private Pizza pizza;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getInizioOfferta() {
        return inizioOfferta;
    }

    public void setInizioOfferta(LocalDate inizioOfferta) {
        this.inizioOfferta = inizioOfferta;
    }

    public LocalDate getFineOfferta() {
        return fineOfferta;
    }

    public void setFineOfferta(LocalDate fineOfferta) {
        this.fineOfferta = fineOfferta;
    }

    public Integer getPercentualeSconto() {
        return percentualeSconto;
    }

    public void setPercentualeSconto(Integer percentualeSconto) {
        this.percentualeSconto = percentualeSconto;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
