# Collection-Companion
Movs Project

# Build & Start Production Environment
docker build -t prodenv -f Dockerfile_ProductionEnvironment .
docker run -v /var/run/docker.sock:/var/run/docker.sock --name prodenv prodenv

# (Re)build images and (re)create containers from images in detached mode:
docker-compose up -d --build --force-recreate

# Stop and remove all containers and images:
docker-compose down --rmi all

# Test command for building an image with docker (with output, what will be done):
docker build -t test123 --progress=plain --no-cache .


# Build and Test jar-archive "Data-Files":
# Build jar-archive
jar -cf collections.jar ports data_files

# Compile Main-file/-class:
javac -cp "gson-2.10.1.jar;lombok.jar;collections.jar;" Main.java

# Call/Test Main class/file:
java Main
