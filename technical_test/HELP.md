# Notes API (Spring Boot + MongoDB)

A simple RESTful API for managing personal notes.  
This project was created as a **test assignment** to demonstrate understanding of Spring Boot fundamentals, MongoDB integration, REST architecture, and clean code practices.

## Implemented Features

- Create notes with `title`, `createdDate`, `text`, and optional `tags` (`BUSINESS`, `PERSONAL`, `IMPORTANT`)
- Update and delete notes
- Validation: notes cannot be created without a title or text
- Retrieve a list of notes showing only `title` and `createdDate`, with:
    - filtering by tag
    - pagination
    - sorting (newest first)
- Retrieve a single note with full content
- Get word frequency statistics for a note’s text, sorted by descending count

## Technologies Used

- **Java 21**
- **Spring Boot 3.5.7**
- **MongoDB**
- **Spring Data MongoDB**
- **Spring Validation**
- **Lombok**
- **Docker / Docker Compose**
- **JUnit + MockMvc** for service and controller testing

## Run Instructions

### Run Locally

1. Make sure MongoDB is running locally (`mongodb://localhost:27017/notes`)
2. Build the project:
   ```bash
   ./mvnw -DskipTests package
   ```
3. Run the app:
   ```bash
   java -jar target/technical_test-0.0.1-SNAPSHOT.jar
   ```
4. API will be available at:  
   `http://localhost:8080`

### Run with Docker Compose

1. From the project root, run:
   ```bash
   docker compose up --build
   ```
2. Application runs at `http://localhost:8080`  
   MongoDB runs at `localhost:27017`

## Configuration

Environment variable:

```bash
MONGODB_URI=mongodb://mongo:27017/notes
```

(default: `mongodb://localhost:27017/notes`)

## API Endpoints

**Base path:** `/api/notes`

| Method   | Path                    | Description                                     |
| -------- | ----------------------- | ----------------------------------------------- |
| `POST`   | `/api/notes`            | Create a new note                               |
| `GET`    | `/api/notes`            | Get paginated list of notes (filterable by tag) |
| `GET`    | `/api/notes/{id}`       | Get a note by ID                                |
| `PUT`    | `/api/notes/{id}`       | Update an existing note                         |
| `DELETE` | `/api/notes/{id}`       | Delete a note                                   |
| `GET`    | `/api/notes/{id}/stats` | Get word statistics for note text               |

### Example — Create Note

```http
POST /api/notes
Content-Type: application/json

{
  "title": "My note",
  "text": "note is just a note",
  "tags": ["BUSINESS", "IMPORTANT"]
}
```

**Response:**

```json
{
  "id": "1",
  "title": "My note",
  "createdDate": "2025-11-10T13:45:00Z",
  "text": "note is just a note",
  "tags": ["BUSINESS", "IMPORTANT"]
}
```

## Example — Word Statistics

`GET /api/notes/{id}/stats`

**Text:** `"note is just a note"`

**Response:**

```json
{ "note": 2, "a": 1, "is": 1, "just": 1 }
```

## Testing

Basic tests are included to verify:

- word statistics computation (`NoteServiceTest`)
- main REST API endpoints (`NoteControllerTest`)

Run all tests:

```bash
./mvnw test
```

## Notes

This project was built for learning purposes as part of a technical test assignment.  
Focus areas include:

- clean architecture (Controller → Service → Repository)
- REST design principles
- input validation and exception handling
- DTO usage and separation of concerns
- containerization and testing

  **Summary:**  
  The project demonstrates practical Spring Boot skills, clean structure, and a strong understanding of backend fundamentals — written clearly and simply, as expected from a **strong junior developer**.

