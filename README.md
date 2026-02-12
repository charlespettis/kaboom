# StudyBunches

> A classroom tool that turns prompts into educational video games for students to play in a web browser.

Teachers create a room, enter a topic, and KaBoom generates a set of multiple-choice questions using OpenAI. Students join with a room code and play through the questions in an interactive 3D environment. Scores are tracked in real time.

## Tech Stack

| Layer    | Technology                     |
|----------|--------------------------------|
| Frontend | React (Vite)                   |
| Backend  | Spring Boot 4 / Java 17       |
| AI       | OpenAI GPT-4o-mini             |
| Build    | Gradle 9                       |
| Storage  | In-memory (no database needed) |

## Prerequisites

- Java 17+
- An [OpenAI API key](https://platform.openai.com/api-keys)

## Getting Started

```bash
git clone https://github.com/charlespettis/kaboom.git
cd kaboom
export OPENAI_API_KEY=your-api-key
./gradlew bootRun
```

The app will be available at **http://localhost:8080**.

## API Endpoints

| Method | Endpoint                  | Description                    |
|--------|---------------------------|--------------------------------|
| POST   | `/room`                   | Create a new game room         |
| GET    | `/room?code={code}`       | Get room details and questions |
| POST   | `/room/generateQuestions` | Generate questions from a prompt |
| POST   | `/room/incrementScore`    | Increment a player's score     |
| POST   | `/room/resetScore`        | Reset a player's score         |

## Project Structure

```
src/main/java/com/baboom/web/
├── controller/     # REST endpoints
├── service/        # Business logic
├── model/          # Room, Question, QuestionList
├── dto/            # Request/response records
├── store/          # In-memory data store
└── integration/    # OpenAI client and prompt building
```

## How It Works

1. A teacher creates a room and receives a 6-digit join code.
2. The teacher enters a topic or prompt to generate 20 multiple-choice questions via OpenAI.
3. Students join the room using the code and answer questions in a 3D game environment.
4. Scores update in real time and are stored for the duration of the session.

> Data is stored in memory only and is lost when the server restarts.
