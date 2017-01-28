package com.github.kikajanovcik.recipes;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

public class RecipesApplication extends Application<RecipesConfiguration> {

    public RecipesApplication() throws UnknownHostException {
    }

    public static void main(String[] args) throws Exception {
        new RecipesApplication().run(args);
    }

    MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    DB database = mongoClient.getDB("RecipesDatabase");


    @Override
    public String getName() {
        return "recipes";
    }

    @Override
    public void initialize(Bootstrap<RecipesConfiguration> bootstrap) {

        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    }

    @Override
    public void run(RecipesConfiguration configuration, Environment environment) {
        final RecipeResource resource = new RecipeResource();
        environment.jersey().register(resource);
    }
}