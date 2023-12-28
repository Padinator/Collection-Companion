# Collection-Companion
Movs Project

# (Re)build images and (re)create containers from images in detached mode:
docker-compose up -d --build --force-recreate

# Stop and remove all containers and images:
docker-compose down --rmi all

# Test command for building an image with docker (with output, what will be done)
docker build -t test123 --progress=plain --no-cache .
