import Cookies from "js-cookie";
import { useState } from "react";
import { Link } from "react-router-dom";
import { useAppDispatch } from "../../store/hooks";
import { useNavigate } from "react-router-dom";
import "./login.scss";
import { logInUser } from "../../api/auth/AuthApi";
import { currentUserSetter } from "../../store/CurrentUser/CurrentUserSlice";

const Login = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const payload = {
      email: email,
      password: password,
    };

    try {
      await logInUser(payload).then((response) => {
        dispatch(currentUserSetter(response));
        Cookies.set("JWT_Token", response?.token, {
          expires: 1,
        });
        if (
          response.user.role === "ROLE_MANAGER" ||
          response.user.role === "ROLE_ADMIN"
        ) {
          navigate("/dashboard/home");
        } else {
          navigate("/dashboard/activity");
        }
      });
    } catch (err) {}
  };

  return (
    <div className="main">
      <div className="container center">
        <h1>Already a manager?</h1>
        <h6>Sign in and manage your branch!</h6>
        <form onSubmit={handleSubmit} className="form">
          <input
            type="text"
            id="email"
            name="email"
            placeholder="E-mail"
            value={email}
            className="input"
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="password"
            id="password"
            name="password"
            placeholder="Password"
            value={password}
            className="input"
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button className="button" type="submit">
            authenticate
          </button>
        </form>
        <div className="buttons">
          <Link className="button" to={`/`}>
            Go back
          </Link>
          <Link className="button" to={`/signup`}>
            Sign up as a branch manager
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Login;
