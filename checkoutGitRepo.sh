#!/bin/bash

# Install Docker-Compose-Plugin
DOCKER_CONFIG=${DOCKER_CONFIG:-$HOME/.docker}
mkdir -p $DOCKER_CONFIG/cli-plugins
curl -SL https://github.com/docker/compose/releases/download/v2.23.3/docker-compose-linux-x86_64 -o $DOCKER_CONFIG/cli-plugins/docker-compose
chmod +x $DOCKER_CONFIG/cli-plugins/docker-compose

# Do first checkout from main
git clone https://github.com/Padinator/Collection-Companion.git
cd Collection-Companion
newest_tag=$(git describe --tags `git rev-list --tags --max-count=1`)
git checkout $tag -b latest

# Do first docker compose build
docker compose -p collection-companion -f dev-env_docker-compose.yml up -d --build --force-recreate

# Check git repository
while true
do
  # Checkout???
  git fetch --tags
	latest_tag=$(git describe --tags `git rev-list --tags --max-count=1`)
  echo "Latest Tag is: $latest_tag"

  if [ "$newest_tag" != "$latest_tag" ]
	then
	    newest_tag=$(git describe --tags `git rev-list --tags --max-count=1`)
		  git checkout $tag
	    docker compose -p collection-companion -f dev-env_docker-compose.yml up -d --build --force-recreate
	else
	    echo "Nothing to checkout, wait 1 minute"
	    sleep 1m
	    echo "Waited 1 minute"
	fi
done
