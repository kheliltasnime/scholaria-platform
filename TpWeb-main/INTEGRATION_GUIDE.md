# Research Paper Platform - Setup & Integration Guide

## Overview

This is a full-stack application for publishing, discovering, and collaborating on research papers. It features:

- **User Authentication** (Signup/Login)
- **Paper Publishing** (Draft creation, validation by admins)
- **Paper Collections** (Organize papers, enable AI features)
- **LLM Chat Interface** (Ask AI about papers/collections)
- **Admin Dashboard** (Validate/reject papers)
- **Comments & Feedback** (Like, comment, download papers)
- **Events Management** (Publish research events)

---

## Tech Stack

### Backend
- **Framework**: Spring Boot 4.0.3
- **Language**: Java 25
- **Database**: PostgreSQL
- **Security**: Spring Security, JWT (JJWT 0.12.6)
- **API**: REST with OpenAPI/Swagger 3.0.2

### Frontend
- **Framework**: Next.js 16.1.6
- **Language**: TypeScript
- **UI**: React 19.2.3, Tailwind CSS
- **HTTP Client**: Axios
- **Notifications**: Sonner
- **Icons**: FontAwesome

---

## Setup Instructions

### Backend Setup

1. **Requirements**
   - Java 25+
   - PostgreSQL 12+
   - Maven 3.8+

2. **Configure Database**
   Edit `back/src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/research_papers
       username: postgres
       password: your_password
     jpa:
       hibernate:
         ddl-auto: update
   ```

3. **Run Backend**
   ```bash
   cd back
   mvn clean install
   mvn spring-boot:run
   ```
   Backend runs on `http://localhost:9090`

### Frontend Setup

1. **Requirements**
   - Node.js 18+
   - npm or yarn

2. **Install Dependencies**
   ```bash
   cd front
   npm install
   ```

3. **Configure API Base URL**
   File: `front/src/services/api.ts`
   ```typescript
   const api = axios.create({
     baseURL: 'http://localhost:9090/api',
     headers: {
       'Content-Type': 'application/json'
     }
   });
   ```

4. **Run Frontend**
   ```bash
   npm run dev
   ```
   Frontend runs on `http://localhost:3000`

---

## Project Structure

### Frontend
```
front/src/
├── app/                          # Next.js pages and layouts
│   ├── signin/                   # Login page
│   ├── signup/                   # Registration page
│   ├── user/
│   │   ├── page.tsx             # User dashboard
│   │   ├── papers/
│   │   │   └── publish/         # Paper publishing form
│   │   ├── collections/         # Collections management
│   │   └── llm/                 # LLM chat interface
│   └── admin/
│       ├── dashboard/           # Admin stats dashboard
│       └── papers/
│           └── validation/      # Paper validation interface
├── interfaces/                   # TypeScript interfaces
│   ├── AuthRequest.ts           # Auth DTOs
│   ├── User.ts                  # User models
│   ├── Paper.ts                 # Paper models
│   ├── Collection.ts            # Collection models
│   ├── Event.ts                 # Event models
│   ├── ChatSession.ts           # Chat models
│   ├── Comment.ts               # Comment models
│   └── Feedback.ts              # Feedback models
└── services/
    ├── api.ts                   # Axios config
    └── apiService.ts            # All API endpoints
```

### Backend
```
back/src/main/java/com/research/paper/
├── controller/                      # REST controllers
│   ├── user/
│   │   ├── AuthenticationController
│   │   ├── UserController
│   │   └── DomainController
│   ├── paper/
│   │   ├── ResearchPaperController
│   │   ├── CollectionController
│   │   └── SavedPaperController
│   ├── feedback/
│   │   ├── CommentController
│   │   └── LikeController
│   ├── llm/
│   │   ├── ChatSessionController
│   │   └── ChatMessageController
│   └── EventController
├── service/                         # Business logic
├── repository/                      # Database access
├── entity/                          # JPA entities
├── dto/
│   ├── request/                     # Request DTOs
│   └── response/                    # Response DTOs
└── security/                        # JWT configuration
```

---

## API Endpoints

### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/refresh` - Refresh token

### Papers
- `POST /api/v1/papers/add` - Create paper
- `GET /api/v1/papers` - List all papers
- `GET /api/v1/papers/{paperId}` - Get paper details
- `PATCH /api/v1/papers/{paperId}` - Update paper
- `PATCH /api/v1/papers/validate/{paperId}` - Admin: validate paper
- `PATCH /api/v1/papers/reject/{paperId}` - Admin: reject paper

### Collections
- `POST /api/v1/collection/add` - Create collection
- `GET /api/v1/collection/user` - User's collections
- `GET /api/v1/collection/{collectionId}` - Get collection
- `PATCH /api/v1/collection/{collectionId}` - Update collection

### Chat
- `POST /api/v1/chat_session/add` - Create chat session
- `GET /api/v1/chat_session/user` - User's chat sessions
- `GET /api/v1/chat_session/{sessionId}` - Get session
- `POST /api/v1/chat_message/add` - Send message
- `GET /api/v1/chat_message/session/{sessionId}` - Get messages

### Comments
- `POST /api/v1/comment/add` - Add comment
- `POST /api/v1/comment/reply` - Reply to comment
- `PATCH /api/v1/comment/{commentId}` - Update comment
- `PATCH /api/v1/comment/like/{commentId}` - Like comment
- `PATCH /api/v1/comment/signal/{commentId}` - Signal comment

### Likes
- `POST /api/v1/likes/add` - Like paper
- `DELETE /api/v1/likes/{paperId}` - Unlike paper

### Saved Papers
- `POST /api/v1/saved_paper/add` - Save paper
- `DELETE /api/v1/saved_paper/{paperId}` - Unsave paper
- `GET /api/v1/saved_paper/user` - User's saved papers

---

## Key Features & Usage

### 1. User Registration & Login
- Users complete 3-step signup: Account Info → Photo → Interests
- Password requirements: 8+ chars, uppercase, lowercase, number, special char
- JWT tokens stored in localStorage for authenticated requests

### 2. Publishing Research Papers
- Users create papers as drafts (status: DRAFT)
- Upload document (PDF, DOCX, DOC - max 50MB)
- Add title, abstract, keywords, authors, cited papers
- Admin validates papers (status: VALIDATED) or rejects them

### 3. Collections Management
- Users organize papers into collections
- Collections can have AI features enabled
- Support for adding multiple papers to a collection
- Perfect for grouping related research

### 4. LLM Chat Interface
- Two context types:
  - **General**: Ask questions about any topic
  - **Collection**: LLM focuses on papers in the collection
- Sessions are saved for later reference
- Messages stored with timestamps
- Real-time chat responses from AI

### 5. Admin Dashboard
- View statistics (users, papers, downloads, active users)
- Validate or reject submitted papers
- Filter by paper status
- Monitor platform activity

### 6. Comments & Feedback
- Users can comment on papers
- Thread comments with replies
- Like/dislike comments
- Flag inappropriate comments
- Track downloads

---

## Authentication Flow

1. **Signup**
   ```
   User enters credentials → POST /auth/register → User created
   ```

2. **Login**
   ```
   User enters email/password → POST /auth/login
   ↓
   Server returns access_token + refresh_token
   ↓
   Frontend stores tokens in localStorage
   ↓
   Axios interceptor adds Authorization header to all requests
   ```

3. **Request Authentication**
   ```
   Every request includes: Authorization: Bearer {access_token}
   Server validates JWT → Request processed
   ```

4. **Token Refresh**
   ```
   If access_token expires → POST /auth/refresh with refresh_token
   ↓
   New access_token issued
   ```

---

## Data Flow Examples

### Publishing a Paper
```
User fills form → Validates data → POST /papers/add
↓
Backend validates all fields → Saves to database (status: DRAFT)
↓
Returns success message → Redirects to dashboard
```

### Validating Papers (Admin)
```
GET /papers → Filters DRAFT status → Displays in validation page
↓
Admin clicks "Validate" → PATCH /papers/validate/{paperId}
↓
Backend updates status to VALIDATED → Removes from draft list
```

### LLM Chat
```
User creates session → POST /chat_session/add
↓
User sends message → POST /chat_message/add
↓
Backend processes with LLM → Generates response
↓
GET /chat_message/session/{sessionId} → Returns all messages
↓
User sees conversation in real-time
```

---

## Environment Variables

### Backend (application.yml)
```yaml
server:
  port: 9090
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/research_papers
    username: postgres
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update

jwt:
  secret: ${JWT_SECRET}
  expiration: 3600000
  refresh-expiration: 604800000
```

### Frontend (.env.local)
```
NEXT_PUBLIC_API_URL=http://localhost:9090/api
```

---

## Common Issues & Solutions

### Issue: CORS Errors
**Solution**: Backend needs CORS configuration:
```java
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
```

### Issue: 401 Unauthorized
**Solution**: Ensure token is in localStorage and included in headers:
```typescript
const token = localStorage.getItem('access_token');
if (token) {
    config.headers.Authorization = `Bearer ${token}`;
}
```

### Issue: File Upload Fails
**Solution**: Ensure file size < 50MB and format is PDF/DOCX/DOC

---

## Testing the Application

### Test User Flow
1. **Signup**: Go to /signup → Fill form → Submit
2. **Login**: Go to /signin → Enter credentials → Access dashboard
3. **Publish Paper**: /user/papers/publish → Fill form → Submit
4. **Collections**: /user/collections → Create → Add papers
5. **Chat**: /user/llm → Create session → Ask questions

### Test Admin Flow
1. **Login as admin user**
2. Navigate to /admin/dashboard
3. Go to /admin/papers/validation
4. Review and validate/reject papers

---

## Future Enhancements

- [ ] Dark mode support
- [ ] Social sharing (Twitter, LinkedIn)
- [ ] Advanced search with filters
- [ ] Paper recommendation engine
- [ ] Notification system
- [ ] Mobile app (React Native)
- [ ] Paper versioning
- [ ] Collaboration features
- [ ] Publication workflow
- [ ] Email notifications

---

## Support & Documentation

- Backend API Docs: `http://localhost:9090/swagger-ui.html`
- Frontend Code: Well-commented components
- Type Safety: Full TypeScript support

---

## License

MIT License - See LICENSE file for details
