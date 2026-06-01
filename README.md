# Research Paper Platform

A full-stack application for publishing, discovering, and collaborating on research papers with AI-powered features.

## 🚀 Overview

This platform enables researchers to:
- **Publish** research papers with admin validation workflow
- **Organize** papers into collections with AI capabilities
- **Interact** through comments, likes, and feedback
- **Chat** with AI about papers and collections
- **Manage** research events and announcements

## 🏗️ Architecture

### Backend (Spring Boot)
- **Framework**: Spring Boot 4.0.3
- **Language**: Java 25
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT (JJWT 0.12.6)
- **API Documentation**: OpenAPI/Swagger 3.0.2
- **Port**: 9090

### Frontend (Next.js)
- **Framework**: Next.js 16.1.6
- **Language**: TypeScript
- **UI**: React 19.2.3, Tailwind CSS
- **HTTP Client**: Axios
- **Notifications**: Sonner
- **Icons**: FontAwesome
- **Port**: 3000

### AI Services
- **Comment Moderation**: FastAPI with profanity detection (port 8000)
- **Recommendation System**: Machine learning-based paper recommendations

## 📁 Project Structure

```
TpWeb-main/
├── back/                    # Spring Boot backend
│   ├── src/main/java/com/research/paper/
│   │   ├── controller/      # REST controllers
│   │   ├── service/         # Business logic
│   │   ├── repository/      # Database access
│   │   ├── entity/          # JPA entities
│   │   ├── dto/             # Request/Response DTOs
│   │   └── security/        # JWT configuration
│   └── pom.xml              # Maven dependencies
├── angular/                 # Angular frontend (scholarIA)
│   └── scholarIA/
├── AI/                      # AI services
│   └── swear-checker.py     # Comment moderation API
├── recommendation/          # ML recommendation system
│   └── ml-recommendation-system-main/
└── INTEGRATION_GUIDE.md     # Detailed setup instructions
```

## ✨ Key Features

### 1. User Authentication
- Multi-step signup (Account Info → Photo → Interests)
- JWT-based authentication with refresh tokens
- Secure password requirements (8+ chars, uppercase, lowercase, number, special char)

### 2. Paper Publishing
- Draft creation with document upload (PDF, DOCX, DOC - max 50MB)
- Admin validation workflow (DRAFT → VALIDATED/REJECTED)
- Rich metadata (title, abstract, keywords, authors, citations)

### 3. Collections Management
- Organize papers into thematic collections
- Enable AI features for enhanced analysis
- Multi-paper support for research grouping

### 4. LLM Chat Interface
- Two context modes: General and Collection-specific
- Session-based conversations with history
- Real-time AI responses

### 5. Admin Dashboard
- Platform statistics (users, papers, downloads, active users)
- Paper validation interface
- Activity monitoring

### 6. Engagement Features
- Comments with threading and replies
- Like/dislike system
- Flag inappropriate content
- Download tracking

## 🛠️ Tech Stack

### Backend Dependencies
- Spring Boot Starter Web & Security
- Spring Data JPA
- PostgreSQL Driver
- Lombok
- JWT (JJWT)
- OpenAPI/Swagger

### Frontend Dependencies
- Next.js 16.1.6
- React 19.2.3
- TypeScript
- Tailwind CSS
- Axios
- Sonner
- FontAwesome

### AI Services
- FastAPI
- profanity-check (English SVM model)
- Machine Learning libraries (scikit-learn, pandas, numpy)

## 🚀 Quick Start

### Prerequisites
- Java 25+
- Node.js 18+
- PostgreSQL 12+
- Maven 3.8+
- Python 3.8+ (for AI services)

### Installation

1. **Clone the repository**
```bash
cd TpWeb-main
```

2. **Backend Setup**
```bash
cd back
# Configure database in src/main/resources/application.yml
mvn clean install
mvn spring-boot:run
```
Backend runs on `http://localhost:9090`

3. **Frontend Setup**
```bash
cd angular/scholarIA
npm install
npm run dev
```
Frontend runs on `http://localhost:4200`

4. **AI Services Setup**
```bash
cd AI
pip install fastapi uvicorn profanity-check
python swear-checker.py
```
AI service runs on `http://localhost:8000`

## 📖 Documentation

For detailed setup instructions, API endpoints, and configuration guide, see:
- **[INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md)** - Complete setup and integration guide

## 🔌 API Endpoints

### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/refresh` - Refresh token

### Papers
- `POST /api/v1/papers/add` - Create paper
- `GET /api/v1/papers` - List all papers
- `PATCH /api/v1/papers/validate/{paperId}` - Admin: validate paper
- `PATCH /api/v1/papers/reject/{paperId}` - Admin: reject paper

### Collections
- `POST /api/v1/collection/add` - Create collection
- `GET /api/v1/collection/user` - User's collections
- `PATCH /api/v1/collection/{collectionId}` - Update collection

### Chat
- `POST /api/v1/chat_session/add` - Create chat session
- `POST /api/v1/chat_message/add` - Send message
- `GET /api/v1/chat_message/session/{sessionId}` - Get messages

### Comments
- `POST /api/v1/comment/add` - Add comment
- `POST /api/v1/comment/reply` - Reply to comment
- `PATCH /api/v1/comment/like/{commentId}` - Like comment

### AI Services
- `POST /v1/filter-comment` - Moderate comments (AI service)

## 🔐 Security

- JWT-based authentication
- CORS configuration
- Password encryption
- Token refresh mechanism
- Role-based access control (User/Admin)

## 🗄️ Database Schema

The application uses PostgreSQL with the following main entities:
- Users
- Research Papers
- Collections
- Chat Sessions & Messages
- Comments & Feedback
- Events
- Likes & Saved Papers

## 🧪 Testing

### Test User Flow
1. Signup at `/signup`
2. Login at `/signin`
3. Publish paper at `/user/papers/publish`
4. Create collections at `/user/collections`
5. Chat with AI at `/user/llm`

### Test Admin Flow
1. Login as admin
2. View dashboard at `/admin/dashboard`
3. Validate papers at `/admin/papers/validation`

## 🐳 Docker Support

Backend includes Docker Compose configuration:
```bash
cd back
docker-compose up
```

## 📝 Environment Variables

### Backend (application.yml)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/research_papers
    username: postgres
    password: ${DB_PASSWORD}
jwt:
  secret: ${JWT_SECRET}
```

### Frontend (.env.local)
```
NEXT_PUBLIC_API_URL=http://localhost:9090/api
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📄 License

MIT License - See LICENSE file for details

## 🔮 Future Enhancements

- [ ] Dark mode support
- [ ] Social sharing (Twitter, LinkedIn)
- [ ] Advanced search with filters
- [ ] Paper recommendation engine (in progress)
- [ ] Notification system
- [ ] Mobile app (React Native)
- [ ] Paper versioning
- [ ] Collaboration features
- [ ] Email notifications

## 📞 Support

- Backend API Docs: `http://localhost:9090/swagger-ui.html`
- Detailed Guide: [INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md)

---

**Built with ❤️ for the research community**
