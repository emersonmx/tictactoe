#!/bin/bash

ASSETS_DIR=../android/assets
IMAGE_DIR=../images

cp -v $IMAGE_DIR/*.png $ASSETS_DIR

./texture_packer.sh $IMAGE_DIR/game $ASSETS_DIR game

