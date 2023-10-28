current problem:

login merge->iau token-u, il pun (in postman la auth unde trebuie)->incerc /TerraTechInc/managers/addUser si primesc 403 forbidden. in terminal in IDE primesc doar asta inapoi '2023-10-28T13:04:47.757+03:00 DEBUG 6408 --- [nio-8080-exec-3] c.e.s.filters.JwtAuthenticationFilter    : User - User(id=1, firstName=Manager Alex, lastName=Vad, password=$2a$10$4IO9nG3w2qK6FYCSC1C0Qusy/4vkaRW6NQxHpl.hdT/2.3tICzGUC, createdAt=2023-10-28, email=a@a.com, phone=1234567890, role=MANAGER, manager=null, activities=[])
'
la fel si pentru /TerraTechInc/managers/user/1 . Chestia ii ca inainte mergeau, dar sunt foarte sigur ca la .manager(manager) ii problema acuma. Nu-s foarte sigur cum sa returnez manager-u din token..
