# City Suggestions API

A Spring Boot application that provides city name suggestions based on user input. The suggestions are generated using a Trie data structure and are enriched with geographic information such as latitude and longitude.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Features

- Auto-complete functionality for city names based on user input.
- Geographic details like latitude and longitude.
- No database is required; city data is loaded from a TSV file.
- Swagger UI for easy API exploration.

## Technologies

- **Java**: Programming language used for development.
- **Spring Boot**: Framework used to create the RESTful API.
- **Thymeleaf**: Templating engine for rendering web pages.
- **JUnit & Mockito**: Libraries for testing.
- **OpenAPI (Swagger)**: Documentation for the REST API.

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/bimasenaputra/geosuggest.git
   ```

2. **Navigate to the project directory:**
   ```bash
   cd geosuggest
   ```

3. **Clean the project:**
   Make sure you have Gradle installed. Run the following command:
   ```bash
   ./gradlew clean
   ```
   
5. **Build the application:**
   ```bash
   ./gradlew build
   ```

6. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```
   
7. **Access the application:**
   Open your browser and go to `http://localhost:8080`.

## Usage

- Navigate to the home page at `/` to see the demo input for city suggestions.
- Type in the name of a city in the input box, and the application will provide suggestions based on your input.
- You can also pass latitude and longitude to refine the suggestions.

## API Endpoints

### Get City Suggestions

- **Endpoint**: `/suggestions`
- **Method**: `GET`
- **Query Parameters**:
  - `q` (String): The query string to filter city names.
  - `latitude` (Double, optional): Latitude for distance-based filtering.
  - `longitude` (Double, optional): Longitude for distance-based filtering.

- **Responses**:
  - `200 OK`: Returns a list of suggestions matching the query.
  - `500 Internal server error`: Internal server error.

### Swagger UI

To explore the API, you can access Swagger UI at:
```
http://localhost:8080/swagger-ui/index.html
```

## Testing

To run the unit tests, use the following command:
```bash
./gradlew test
```

Make sure to check the test coverage and verify that all tests pass successfully.

## Contributing

Contributions are welcome! If you have suggestions or improvements, please create a pull request.

1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/new-feature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/new-feature`).
5. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
```

### Instructions for Use
- Replace `your-username` in the clone URL with your actual GitHub username.
- Feel free to modify sections such as "Features," "Usage," and "API Endpoints" to reflect the specifics of your application.
- Add additional sections if necessary, depending on the functionality of your project.
