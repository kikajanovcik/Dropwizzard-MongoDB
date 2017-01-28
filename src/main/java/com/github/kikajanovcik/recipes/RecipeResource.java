package com.github.kikajanovcik.recipes;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/recipes")
@Produces(MediaType.APPLICATION_JSON)
public class RecipeResource {

    private List<Recipe> recipes = new ArrayList<>();

    public RecipeResource() {
        Recipe cake = new Recipe("Chocolate Cake");
        cake.setIngredients(Arrays.asList("chocolate", "flour", "eggs"));
        cake.setMinutes(120);
        cake.setId(1L);

        Recipe omelette = new Recipe("Spanish Omelette");
        omelette.setIngredients(Arrays.asList("eggs", "potatoes", "onion"));
        omelette.setMinutes(30);
        omelette.setId(2L);

        Recipe roast = new Recipe("Sunday Roast");
        roast.setIngredients(Arrays.asList("turkey", "gravy", "potatoes", "brussel sprouts"));
        roast.setMinutes(240);
        roast.setId(3L);

        Recipe pancakes = new Recipe("Pancakes");
        pancakes.setIngredients(Arrays.asList("eggs", "flour", "sugar", "milk"));
        pancakes.setMinutes(30);
        pancakes.setId(4L);

        recipes.add(cake);
        recipes.add(omelette);
        recipes.add(roast);
        recipes.add(pancakes);
    }

    @GET
    @Path("{id}")
    public List<Recipe> getRecipeByID(@PathParam("id") long id) {
        List<Recipe> selectedRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (recipe.getId() == id) {
                selectedRecipes.add(recipe);
                return selectedRecipes;
            }
        }
        return null;
    }

    @GET
    @Path("time")
    public List<Recipe> getRecipeByTime(@QueryParam("time") int minutes) {
        List<Recipe> selectedRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (recipe.getMinutes() <= minutes) {
                selectedRecipes.add(recipe);
            }
        }
        return selectedRecipes;
    }

    @POST
    public void postRecipe(Recipe recipe) {
        recipes.add(recipe);
        recipe.setId(recipes.size());
    }

    @GET
    public List<Recipe> getAllRecipes() {
        return recipes;
    }
}