import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { signUpUser } from "../../api/auth/AuthApi";
import "./signup.scss";

export default function SignUp() {
  const navigate = useNavigate();
  const passwordRegex =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [phone, setPhone] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [phoneToolTip, setphoneToolTip] = useState(false);
  const [passwordToolTip, setPasswordToolTip] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [emailError, setEmailError] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!emailRegex.test(email)) {
      return;
    }
    if (!passwordRegex.test(password)) {
      return;
    }
    const payload = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
      phone: phone,
    };
    if (!isLoading) {
      signUpUser(payload).then((response) => {
        if (response.ok) {
          setIsLoading(true);
          navigate("/login");
        }
      });
    }
  };

  return (
    <div className="main">
      <div className="container center">
        <h1>New branch?</h1>
        <h6>Sign up and start managing your branch!</h6>
        <form onSubmit={handleSubmit} className="form">
          <div>
            <label htmlFor="firstName">First Name:</label>
            <input
              className="input"
              type="text"
              id="firstName"
              name="firstName"
              maxLength={10}
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              required
            />
          </div>
          <div>
            <label htmlFor="lastName">Last Name:</label>
            <input
              className="input"
              type="text"
              id="lastName"
              name="lastName"
              maxLength={10}
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              required
            />
          </div>
          <div>
            <label htmlFor="email">E-mail:</label>
            <input
              className={emailError ? "inputError" : "input"}
              type="text"
              id="email"
              name="email"
              maxLength={40}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              onBlur={() => {
                if (!emailRegex.test(email)) {
                  setEmailError(true);
                }
              }}
            />
          </div>
          <div>
            <label htmlFor="phone">Phone:</label>
            <input
              className={phoneToolTip ? "inputError" : "input"}
              type="text"
              value={phone}
              id="phone"
              name="phone"
              maxLength={40}
              onChange={(e) => setPhone(e.target.value)}
              required
              onBlur={() => {
                if (phone.length !== 10) {
                  setphoneToolTip(true);
                }
              }}
            />
            {phoneToolTip && (
              <small className="helper-text">
                The phone number must be 10 characters long.
              </small>
            )}
          </div>
          <div>
            <label htmlFor="password">Password:</label>
            <input
              className={passwordError ? "inputError" : "input"}
              type="password"
              id="password"
              name="password"
              maxLength={40}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              onFocus={() => setPasswordToolTip(true)}
              onBlur={() => {
                setPasswordToolTip(false);
                if (!passwordRegex.test(password)) {
                  setPasswordError(true);
                }
              }}
            />
            {passwordToolTip && (
              <small className="helper-text">
                The password requires a minimum length of 6 characters, at least
                one uppercase or lowercase letter, requires at least one digit
                and a special character: @,$,!,%,*,#,?,&
              </small>
            )}
          </div>
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
}
