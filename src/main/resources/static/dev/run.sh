docker stop opkp-app 
docker rm opkp-app  
docker run --name opkp-app -v "$PWD":/usr/share/nginx/html -v "$PWD"/dev/nginx.conf:/etc/nginx/nginx.conf:ro -p 80:80 -d nginx
