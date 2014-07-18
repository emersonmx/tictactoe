#!/bin/bash

if [[ $# != 3 ]]; then
    echo "Usage $0 <input_dir> <output_dir> <pack_file_name>"
    exit 0
fi

LIBGDX=~/.m2/repository/com/badlogicgames/gdx/gdx/1.2.0/gdx-1.2.0.jar
LIBGDX_TOOLS=~/.gradle/caches/modules-2/files-2.1/com.badlogicgames.gdx/gdx-tools/1.2.0/f5903c6c9bccda9616752703459a5783bc0e73b/gdx-tools-1.2.0.jar
TEXTURE_PACKER=com.badlogic.gdx.tools.texturepacker.TexturePacker

java -cp $LIBGDX:$LIBGDX_TOOLS $TEXTURE_PACKER $1 $2 $3
