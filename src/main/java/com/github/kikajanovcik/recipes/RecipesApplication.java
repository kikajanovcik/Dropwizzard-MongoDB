package com.github.kikajanovcik.recipes;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

public class RecipesApplication extends Application<RecipesConfiguration> {

    public RecipesApplication() {}

    public static void main(String[] args) throws Exception {
        new RecipesApplication().run(args);
    }

    @Override
    public String getName() {
        return "recipes";
    }

    @Override
    public void initialize(Bootstrap<RecipesConfiguration> bootstrap) {

        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    }

    @Override
    public void run(RecipesConfiguration configuration, Environment environment) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("RecipesDatabase");

        environment.healthChecks().register("mongo", new MongoHealthCheck(mongoClient));
        final RecipeResource resource = new RecipeResource(database);
        environment.jersey().register(resource);
    }
}