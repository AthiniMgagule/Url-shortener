Ahh I see what happened — when GitHub renders, those “Copy code / bash / arduino” lines you pasted aren’t actual Markdown, they came from a code block preview somewhere.

Let me give you a **clean Markdown-only README.md** that will render properly on GitHub, without those extra artifacts.

---

````markdown
# 🔗 URL Shortener

A simple **URL Shortener** built with **React (frontend)** and **Spring Boot (backend)**.  
It allows users to input long URLs and get back shortened versions that can be shared and visited easily.

---

## ✨ Features

- Shorten long URLs into compact links
- Copy shortened URL to clipboard
- Open shortened URL in a new tab
- Reset and shorten another URL
- Built with a **React frontend** and **Spring Boot backend**

---

## 🛠️ Tech Stack

**Frontend:**
- React + Vite
- Axios (API calls)
- Lucide React (icons)
- CSS for styling

**Backend:**
- Spring Boot
- REST API
- Java
- (Optional) Database (H2/MySQL/PostgreSQL for storing links)

---

## ⚙️ Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/your-username/url-shortener.git
cd url-shortener
````

---

### 2. Backend (Spring Boot)

```bash
cd url-shortener-backend
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw spring-boot:run
```

The backend will run at:

```
http://localhost:8080
```

---

### 3. Frontend (React)

```bash
cd url-shortener-frontend
npm install
```

Create a `.env.local` file in the root of `url-shortener-frontend`:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

Start the development server:

```bash
npm run dev
```

The frontend will run at:

```
http://localhost:5173
```

---

## 📡 API Endpoints

**POST /api/shorten**
Shortens a given URL.

**Request body:**

```json
{
  "url": "https://example.com"
}
```

**Response:**

```json
{
  "shortUrl": "http://localhost:8080/api/abc123"
}
```

**GET /{code}**
Redirects to the original URL.

---

## 🚀 Deployment

* For production, set `VITE_API_BASE_URL` in `.env.production` to your hosted backend URL.
* You can deploy:

  * Frontend: Vercel, Netlify, Render
  * Backend: Heroku, Render, Fly.io, or AWS

---

## 📌 Future Improvements

* User authentication & dashboards
* Custom short URL aliases
* Analytics (click count, referrer, location, etc.)
* Link expiration & QR code generation
* Improved UI/UX with Tailwind or shadcn/ui

---

## 📜 License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
Feel free to use, modify, and share.

