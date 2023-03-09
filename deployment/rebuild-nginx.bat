@ECHO OFF

SET "SERVER_IP=207.148.71.252"
SET "SERVER_DIR=/app/nginx"

ECHO rebuild nginx on server %SERVER_IP%

ECHO uploading configuration...
scp ../docker/docker-compose.nginx.yml root@%SERVER_IP%:%SERVER_DIR%/docker-compose.yml
scp -r ../docker/nginx root@%SERVER_IP%:%SERVER_DIR%

ECHO restarting...
ssh root@%SERVER_IP% "cd %SERVER_DIR% && docker compose up -d --build"

ECHO completed!