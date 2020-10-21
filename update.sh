#!/bin/bash
# Let's Encryptを更新
# サービスを再起動
ssh sukeroku@itetenosuke.com "cd itetenosuke/; docker-compose down; sudo certbot renew;docker-compose -f docker-compose.yml -f docker-compose-prod.yml up -d"
