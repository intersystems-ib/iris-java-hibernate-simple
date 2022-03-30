Using hibernate to connect to InterSystems IRIS. Simple example.

This example uses a container running InterSystems IRIS as backend and a local java application.

# Setup
Build IRIS image
```
docker-compose build
```

Compile java application
VS Code > Maven > Lifecycle > compile

Instead, you can run:
```
mvn compile
```

# Run
Run IRIS container
```
docker-compose up -d
```

Run java application
VS Code > Open hibernateSimple.java > Click "Run" on top of main 

