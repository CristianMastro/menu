package pizzeria.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pizzeria.menu.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

}
