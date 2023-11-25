import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { logInUser } from "../../api/auth/AuthApi";
import { currentUserSetter } from "../../store/CurrentUser/CurrentUserSlice";
import { useAppDispatch } from "../../store/hooks";
import "./login.scss";

const Login = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [emailError, setEmailError] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!emailRegex.test(email)) {
      return;
    }
    const payload = {
      email: email,
      password: password,
    };
    if (!isLoading) {
      logInUser(payload).then((response) => {
        dispatch(currentUserSetter(response));
        if (response.user.role === "ROLE_EMPLOYEE") {
          navigate("/dashboard/activity");
        } else {
          navigate("/dashboard/home");
        }
      });
      setIsLoading(true);
    }
  };

  return (
    <div className="main">
      <div className="container center">
        <h1>Already a manager?</h1>
        <h6>Sign in and manage your branch!</h6>
        <form onSubmit={handleSubmit} className="form">
          <input
            className={emailError ? "inputError" : "input"}
            type="text"
            id="email"
            name="email"
            placeholder="E-mail"
            value={email}
            maxLength={40}
            onChange={(e) => setEmail(e.target.value)}
            required
            onBlur={() => {
              if (!emailRegex.test(email)) {
                setEmailError(true);
              } else {
                setEmailError(false);
              }
            }}
          />
          <div
            className="helperContainer"
            style={{ display: emailError ? "" : "none" }}
          >
            {emailError && (
              <small className="error-text">Please enter a valid e-mail.</small>
            )}
          </div>
          <input
            className="input"
            type="password"
            id="password"
            name="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button className="button" type="submit">
            Authenticate
          </button>
        </form>
        <div className="buttons">
          <Link className="button" to={`/`}>
            Go back
          </Link>
          <Link className="button" to={`/signup`}>
            Sign up
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Login;
