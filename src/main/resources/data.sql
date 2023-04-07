insert into recipe(name, recipe_type, text)
values ('mozzarellás szendvics', 'REGGELI', 'készítsünk mozzarellás szendvicset'),
       ('sonkás szendvics', 'REGGELI', 'készítsünk sonkás szendvicset'),
       ('camambertes szendvics', 'REGGELI', 'készítsünk camambertes szendvicset'),
       ('rizseshús', 'EBÉD', 'készítsünk rizseshúst'),
       ('korpovit + alma', 'UZSONNA', 'együk meg'),
       ('bulgursaláta', 'VACSORA', 'készítsünk bulguros salátát');

insert into ingredient(name)
values ('mozzarella'),
       ('sonka'),
       ('teljes kiőrlésű kenyér'),
       ('camambert'),
       ('margarin'),
       ('rizs'),
       ('csirkemell'),
       ('hagyma'),
       ('olaj'),
       ('korpovit'),
       ('alma'),
       ('bulgur'),
       ('salátakeverék');

insert into recipes_ingredients(recipe_id, ingredient_id)
values (1,1),
       (1,3),
       (1,5),
       (2,1),
       (2,3),
       (2,5),
       (3,4),
       (3,3),
       (3,5),
       (4,6),
       (4,7),
       (4,8),
       (4,9),
       (5,10),
       (5,11),
       (6,12),
       (6,13);
