# ManagementSystem
Ez egy Springboot project maven projektkezelővel

Buildelés:

    mvn clean install
Futtatás:

    java -jar ./target/ManagementSystem-0.0.1-SNAPSHOT.jar
  
A szerver REST api-n keresztül komunikál a kliensekkel

Alapértelmezetten a 8080 as porton komunikál a szerver (localhost:8080)

táblák:

    Education
    RelEducationUser
    RelEducationToolCtegory
    Tool
    ToolCategory
    User
  
Végpontokhoz példa:
    
    ManagementSystem.postman_collection.json

Ebből a fájlbl lehet importálni postmanbe
A fájlban a user táblához és a tool táblához vannak példa requestek.
A többi táblához is hasonló végpontok vannak mint a toolhoz.
