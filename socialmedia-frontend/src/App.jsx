import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Feed from "./pages/Feed";
import { isLoggedIn } from "./utils/auth.js";

function App() {
    return (
        <Router>
            <Routes>
                <Route
                    path="/"
                    element={
                        isLoggedIn() ? <Navigate to="/feed" /> : <Navigate to="/login" />
                    }
                />
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />
                <Route path="/feed" element={<Feed />} />
            </Routes>
        </Router>
    );
}

export default App;