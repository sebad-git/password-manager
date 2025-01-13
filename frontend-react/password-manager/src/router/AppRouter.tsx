import React from "react";
import { BrowserRouter as Router, Routes, Route, Outlet, Navigate } from "react-router-dom";
import HomePage from "../view/pages/HomePage";
import AboutPage from "../view/pages/AboutPage";
import LoginPage from "../view/pages/LoginPage";
import { useAuth } from "./AuthContext";

const AppRouter: React.FC = () => {
  const { isAuthenticated, loading } = useAuth();

  const PrivateRoute: React.FC = () => {
    if (loading) {
      return <div>Loading...</div>;
    }
    return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
  };

  return (
    <Router>
      <Routes>
        {/* Public Routes */}
        <Route path="/login" element={<LoginPage />} />
        
        {/* Private Routes */}
        <Route element={<PrivateRoute />}>
          <Route path="/" element={<HomePage />} />
          <Route path="/about" element={<AboutPage />} />
        </Route>
      </Routes>
    </Router>
  );
};

export default AppRouter;