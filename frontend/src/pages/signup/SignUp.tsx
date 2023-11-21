import { useState } from "react";
import { Link } from "react-router-dom";
import "./signup.scss";
import { signUpUser } from "../../api/auth/AuthApi";
import { useNavigate } from "react-router-dom";

const SignUp = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [phone, setPhone] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const payload = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
      phone: phone,
    };
    try {
      const res = await signUpUser(payload);
      if (res.ok) {
        navigate("/login");
      }
    } catch (err) {}
  };

  return (
    <div className="main">
      <div className="container center">
        <h1>New branch?</h1>
        <h6>Sign up and start managing your branch!</h6>
        <form onSubmit={handleSubmit} className="form">
          <input
            className="input"
            type="text"
            value={firstName}
            id="firstName"
            name="firstName"
            placeholder="First name"
            onChange={(e) => setFirstName(e.target.value)}
            required
          />
          <input
            className="input"
            type="text"
            id="lastName"
            name="lastName"
            value={lastName}
            placeholder="Last name"
            onChange={(e) => setLastName(e.target.value)}
            required
          />
          <input
            className="input"
            type="text"
            value={email}
            id="email"
            name="email"
            placeholder="E-mail"
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            className="input"
            type="text"
            value={phone}
            id="phone"
            name="phone"
            placeholder="Phone"
            max="10"
            onChange={(e) => setPhone(e.target.value)}
            required
          />
          <input
            className="input"
            type="password"
            value={password}
            id="password"
            name="password"
            placeholder="Password"
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button className="button" type="submit">
            Register
          </button>{" "}
        </form>
        <div>
          <Link className="button" to={`/`}>
            Go back
          </Link>
          <Link className="button" to={`/login`}>
            Sign in instead
          </Link>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
