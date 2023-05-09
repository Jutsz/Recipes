# Recipes Application

The project is focused on managing recipes, 
allowing users to add, update, delete, and search for recipes in a cookbook. 
A recipe can be located using its ingredients and recipe type.
An ingredient can be located using its name.

The data in the application is currently test data. The next step in the project will involve implementing real data.

## Entities
The application includes two entities: `Recipe` and `Ingredient`.

### Recipe

The `Recipe` entity consists of the following fields:

Id,
Name,
Recipe type (an enumeration),
Text (the full text of the recipe),
Ingredients

### Ingredient

The `Ingredient` entity consists of the following fields:

Id,
Name,
Recipes (the set of recipes in which the ingredient is used)

## Functionality

### Starting the application
To start the application, you can run `RecipesApplication` (`src/main/java/com/example/recipes/RecipesApplication.java`) in your IDE.
To start the application with docker, use the docker_build.sh and docker_run.sh scripts. 

### Testing the application
To test the application, use the Recipes_requests.http file to send HTTP requests.
You can also test the application with running `src/test/java/com/example/recipes` folder.

### Documentation
Additionally, you can use Swagger at http://localhost:8080/swagger-ui/index.html after starting the application 
to interact with the endpoints. (If you are running with docker: http://localhost/swagger-ui/index.html).

### Recipe Management

`GET /recipes`: Get all recipes of the cookbook. The response should include an array of `RecipeDTO` objects.
`GET /recipes/{id}`: Get a recipe by id. The response should include a single `RecipeDTO` object.
`GET /recipes/ingredient`: Get a list of recipes that include the specified ingredient. The response should include an array of `RecipeDTO` objects.
`GET /recipes/recipetype/ingredient`: Get a list of recipes by an ingredient, in a given recipe type. The response should include an array of `RecipeDTO` objects.
`POST /recipes`: Add a new recipe to the cookbook.
`PUT /recipes/update/{id}`: Update a recipe in the cookbook with a given id.
`DELETE /recipes/delete/{id}`: Delete a recipe from the cookbook with a given id.

### Ingredient Management

`GET /ingredients`: Get all ingredients of the cookbook. The response should include an array of `IngredientDTO` objects.
`GET /ingredients/{id}`: Get an ingredient by id. The response should include a single `IngredientDTO` object.
`GET /ingredients/name`: Get a list of ingredients matching the provided name. The response should include an array of `IngredientDTO` objects.
`POST /ingredients`: Add a new ingredient to the cookbook.
`PUT /ingredients/update/{id}`: Update an ingredient in the cookbook with a given id.
`DELETE /ingredients/delete/{id}`: Delete an ingredient from the cookbook with a given id.



