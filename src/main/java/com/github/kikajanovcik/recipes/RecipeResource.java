package com.github.kikajanovcik.recipes;

import com.mongodb.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/recipes")
@Produces(MediaType.APPLICATION_JSON)
public class RecipeResource {

    private DB database;
    private List<Recipe> recipes = new ArrayList<>();

    private final DBObject toDBObject(Recipe recipe) {
        return new BasicDBObject("_id", recipe.getId())
                .append("name", recipe.getName())
                .append("ingredients", recipe.getIngredients())
                .append("minutes", recipe.getMinutes());
    }

    public RecipeResource(DB database) {
        this.database = database;
    }

    @GET
    public List<Recipe> getAllRecipes() {
        //this.recipes = collection.find().into(new ArrayList<>());
        return recipes;
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
        recipes.add(recipe); //to remove
        recipe.setId(recipes.size()); //to remove

        saveToDB(recipe);
    }

    private void saveToDB(Recipe recipe) throws NullPointerException {
        DBCollection collection = database.getCollection("recipesCollection");
        collection.insert(toDBObject(recipe));
    }
}