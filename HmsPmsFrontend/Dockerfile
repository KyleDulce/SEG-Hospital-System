# Create project
FROM node:20.9.0-alpine as buildNode

WORKDIR /project-build

# Copy code into image
COPY ./ /project-build

RUN npm i

RUN npm run build

# Setup app serve
FROM nginx:1.24.0-alpine3.17

COPY --from=buildNode /project-build/dist/hms-pms-frontend /etc/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80