#!/usr/bin/env bash

filePath="$(pwd)/app/src/main/res/values/strings.xml"

curl \
  -F "files[strings.xml]=@$filePath" \
  https://api.crowdin.com/api/project/hadescoincom/update-file?key=$CROWDIN_PROJECT_KEY