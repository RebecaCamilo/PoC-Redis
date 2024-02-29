# PoC Redis

This is a simple project aimed at providing a practical demonstration of using Redis as a caching system.
The main goal is to offer a foundation for experimentation and learning about caching using Redis.

## Environment Setup
Before getting started, make sure you have Docker installed on your machine. If you don't have it yet, download and install it from the <a href="https://www.docker.com/get-started/">official Docker website</a>.

### Starting Redis with Docker
Run the following command to start a Redis container using Docker:
```
docker run -d --name my-redis -p 6379:6379 -p 8801:8801 redis:latest
```
This command will create a container named "my-redis" and map it to ports 6379 (default Redis port) and 8801 (web Redis interface). Make sure that no other application is using these ports on your system.

### Running the PoC Redis Application
Clone this repository to your local machine:
```
git clone https://github.com/your-username/poc-redis.git
```
Navigate to the project directory:
```
cd poc-redis
```
Install the project dependencies:
```
mvn clean install
```
Start the application:
```
mvn spring-boot:run
```
Now, the Swagger of application will be available for access at http://localhost:8080/swagger-ui/index.html. You can start experimenting and exploring the use of Redis as a caching system in the application.
To access the Redis web interface, you can visit http://localhost:8001/.

https://github.com/RebecaCamilo/PoC-Redis/assets/71824475/ed603aad-a8c3-4ec0-8895-db366c3cb4fd

