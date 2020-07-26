#!/bin/bash
# outputディレクトリ内のファイルをrsync over SSHで転送
rsync -acvz --delete ./ ukeroku@itetenosuke.com:itetenosuke/
# サービスを再起動
ssh sukeroku@itetenosuke.com "cd itetenosuke/; docker-compose build; docker-compose -f docker-compose.yml -f docker-compose-prod.yml up -d"
