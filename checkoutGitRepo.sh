#!/bin/bash

# Do first checkout from main
git clone https://github.com/Padinator/Collection-Companion.git
cd Collection-Companion
newest_tag=$(git describe --tags `git rev-list --tags --max-count=1`)
git checkout $tag -b $tag

# Do first docker compose build
docker-compose -p collection-companion up -d --build --force-recreate

# Check git repository
while [ 1 -eq 1 ]
do
    # Checkout???
	latest_tag=$(git describe --tags `git rev-list --tags --max-count=1`)

    if [ $newest_tag = $latest_tag ]
	then
	    newest_tag=$(git describe --tags `git rev-list --tags --max-count=1`)
		git checkout $tag -b $tag
	    docker-compose -p collection-companion up -d --build --force-recreate
	else
	    sleep 1m
	fi
done
