package com.sntaks.tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.sntaks.tacos.TacoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import com.sntaks.tacos.Ingredient;
import com.sntaks.tacos.Ingredient.Type;
import com.sntaks.tacos.Taco;


@Slf4j                              // Lombok-provided annotation that at compilation time, will automatically generate a Simple Logging Face for Java (SlF4j)
@Controller                         // Identifies the class as s Controller
@RequestMapping("/design")        // When applied at class level, it specifies the requests that this Controller handles
@SessionAttributes("tacoOrder")     // The 'TacoOrder' object that is put into the model in the class should/is maintained in session
public class DesignTacoController {
    /**
     * Invoked when a request is handled and constructs a list of
     * Ingredient objects to be put in a model.
     * Model is an object ferrying data betwwen a Controller and whatever view
     * is charged with rendering data
     */
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for(Type type: types){
            /**
             * Filters the list vy ingredient type suing filterByType().
             * A list if ingredient types is then added to the Model object that is passed to showDesignForm()
             */
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    /**
     * @GetMapping paired with the class-level @RequestMapping,
     * specifies that when a HTTP GET request is received for '/design',
     * Spring MVC will call showDesignForm() to handle request.
     */
    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
