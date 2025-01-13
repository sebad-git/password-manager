import React, { useState } from "react";
import Card from "../components/card/Card";
import "./LoginPage.css";
import { UserService } from "../../core/services/UserService";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../router/AuthContext";

const userService = UserService.getInstance();

const LoginPage: React.FC = () => {

const [username, setUsername] = useState("");
const [password, setPassword] = useState("");
const [error, setError] = useState<string | null>(null);
const { login } = useAuth();
const navigate = useNavigate();

const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault();
  const response = await userService.login(username, password);
  console.log(JSON.stringify(response));
  const success = await login(username, password);
  if (success) {
    navigate("/");
  } else {
    setError("Invalid username or password");
  }
};

  return <>
  <div className="login">
      <Card>
        <div className="login-form">
          <form onSubmit={handleSubmit}>
            <div className="row">
              <div className="col-4">
                <h6 className="login-form-label">Username</h6>
              </div>
              <div className="col-8">
                <input
                  type="text"
                  className="form-control login-form-input"
                  id="userName"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </div>
            </div>
            <div className="row login-form-spacer">
              <div className="col-4">
                <h6 className="login-form-label">Password</h6>
              </div>
              <div className="col-8">
                <input
                  type="password"
                  className="form-control login-form-input"
                  id="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
            </div>
            <div className="row login-form-spacer">
              <div className="col-12 text-center">
                <button type="submit" className="btn btn-primary">
                  Submit
                </button>
              </div>
            </div>
          </form>
          {error && <div className="error-message">{error}</div>}
        </div>
      </Card>
    </div>
  </>;
};

export default LoginPage;