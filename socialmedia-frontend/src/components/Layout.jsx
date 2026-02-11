import { Outlet, Navigate } from "react-router-dom";
import Header from "./Header";
import { isLoggedIn } from "../utils/auth";

function Layout() {
    if (!isLoggedIn()) {
        return null;
    }

    return (
        <>
            <Header />
            <div style={{ padding: "2rem" }}>
                <Outlet />
            </div>
        </>
    );
}

export default Layout;