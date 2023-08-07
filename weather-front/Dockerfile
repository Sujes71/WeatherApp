FROM node:20-alpine3.17 as builder

LABEL stage=builder

WORKDIR /app

COPY package.json package-lock.json /app/

RUN npm install

COPY . /app

RUN npm run build --prod

FROM nginx:1.25.1

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=builder ./app/dist/weather-app /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]