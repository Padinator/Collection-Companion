# Collection-Companion
Movs Project

# Build & Start Production Environment
docker-compose -f prod-env_docker-compose.yml up -d --build --force-recreate

# (Re)build images and (re)create containers from images in detached mode:
docker-compose -f dev-env_docker-compose.yml up -d --build --force-recreate

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
