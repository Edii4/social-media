import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Feed from "./pages/Feed";
import { isLoggedIn } from "./utils/auth.js";
import Layout from "./components/Layout.jsx";

function App() {
    return (
        <BrowserRouter>
            <Routes>

                {/* Public routes */}
                <Route
                    path="/login"
                    element={isLoggedIn() ? <Navigate to="/feed" /> : <Login />}
                />

                <Route
                    path="/register"
                    element={isLoggedIn() ? <Navigate to="/feed" /> : <Register />}
                />

                {/* Protected routes */}
                <Route element={<Layout />}>
                    <Route path="/feed" element={<Feed />} />
                    {/* Add more protected routes here later */}
                    {/* <Route path="/profile" element={<Profile />} /> */}
                </Route>

                {/* Default route */}
                <Route
                    path="/"
                    element={<Navigate to={isLoggedIn() ? "/feed" : "/login"} />}
                />

            </Routes>
        </BrowserRouter>
    );
}

export default App;